import java.rmi.registry.*;
import java.rmi.*;
import java.util.*;
import java.io.*;

public class ChatAppImpl implements ChatApp
{
	private static final String[] s_asErrorMessages = {
            "",
            "ERROR: Client ID is already registered",
			"ERROR: Client ID is not found",
			"ERROR: Client ID is invalided",
    };
	private static final String s_sChatLogFileName = "./ServerChat.log";
	private static final String s_sServerName = "_SERVER_";
	private LinkedList<String> m_qsChatLog;
	private Registry m_registry = null;
	private ClientBase m_clientBase = null;
	
    public ChatAppImpl()
    {
		m_qsChatLog = new LinkedList<String> ();
		            
    }
	
	private Registry getRegistry()
	{
		try
        {
			if (m_registry == null)
				m_registry = LocateRegistry.getRegistry();
		} 
		catch (Exception e)
        {
            e.printStackTrace();
        }
		return m_registry;
	}
	private ClientBase getClientBase()
	{
		try
		{
			if (m_clientBase == null)
				m_clientBase = (ClientBase) getRegistry().lookup("RegistryService");
		} 
		catch (Exception e)
        {
            e.printStackTrace();
        }
		return m_clientBase;
	}

    public int joinChatRoom(Info_itf client) throws RemoteException
    {
        int res = ClientBase.s_iError_NoError;
        try
		{
			if (client.getName().equals(s_sServerName))
			{
				return ClientBase.s_iErrorInvalideID;
			}
            res = getClientBase().register(client.getName());
            if (res == ClientBase.s_iError_NoError)
			{
				loadChatLog();
				client.PushMessageList(m_qsChatLog);
				sendBroadcast(client.getName() + " has joined the room!");
			}
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }

    public int leaveChatRoom(Info_itf client) throws RemoteException
    {
		int res = ClientBase.s_iError_NoError;
        try
        {
            res = getClientBase().unregister(client.getName());
			if (res == ClientBase.s_iError_NoError)
			{
				sendBroadcast(client.getName() + " has left the room!");
				saveChatLog();
			}
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }
	
	public int disConnectChatRoom(String clientName) throws RemoteException
    {
		int res = ClientBase.s_iError_NoError;
        try
        {
            res = getClientBase().unregister(clientName);
			if (res == ClientBase.s_iError_NoError)
			{
				sendBroadcast(clientName + " has been disconnected!");
				saveChatLog();
			}
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }
	
	public String getErrorMessage(int iErrorCode)
    {
        return s_asErrorMessages[iErrorCode];
    }

    public void saySomething(String scrName, String message) throws RemoteException
    {
        try
        {
			message = scrName + ": " + message;
			addToChatLog(message);
            
            List<String> clientList = getClientBase().getListOfClients();
            for (Iterator<String> i = clientList.iterator(); i.hasNext();) 
			{
				String clientName = i.next();
				if (!clientName.equals(scrName))
				{
					Info_itf client = null;
					try 
					{
						client = (Info_itf) getRegistry().lookup(ChatClient.GenerateServiceName(clientName));
						if (client != null)
							client.PushMessage(message);
					}
					catch (Exception e)
					{
						disConnectChatRoom(clientName);
					}
				}
			}

        }	
		catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void sendBroadcast(String message)
    {
		try
		{
			saySomething(s_sServerName, message);
		} 
		catch (Exception e)
        {
            e.printStackTrace();
        }
    }
	
	private void saveChatLog()
	{
		try  
		{
			PrintStream out = new PrintStream(new FileOutputStream(s_sChatLogFileName));
			for (String line : m_qsChatLog)
				out.println(line);
			out.close();
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		
	}
	
	private void addToChatLog(String message)
	{
		m_qsChatLog.addLast(message);
	}
	
	private void loadChatLog()
	{
		try  
		{			
			BufferedReader reader = new BufferedReader(new FileReader(s_sChatLogFileName));
			String line = null;
			LinkedList<String> slChatLog = new LinkedList<String>();
			while ((line = reader.readLine()) != null) {
				slChatLog.add(line);
			}
			
			if (slChatLog.size() > m_qsChatLog.size())
				m_qsChatLog = (LinkedList<String>) slChatLog.clone();
			
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}
}
