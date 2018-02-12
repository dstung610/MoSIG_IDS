import java.rmi.registry.*;
import java.rmi.*;
import java.util.*;
import java.io.*;

public class ChatAppImpl implements ChatApp
{
	private static final String[] s_asErrorMessages = {
            "",
            "ERROR: Client ID is already registered",
			"ERROR: Client ID is not found"
    };
	private static final String s_sChatLogFileName = "./ServerChat.log";
	private LinkedList<String> m_qsChatLog;
	
    public ChatAppImpl()
    {
    }

    public int joinChatRoom(Info_itf client) throws RemoteException
    {
        int res = Registry_itf.s_iError_NoError;
        try
        {
            Registry registry = LocateRegistry.getRegistry();
            Registry_itf r = (Registry_itf) registry.lookup("RegistryService");
            res = r.register(client);
            if (res == Registry_itf.s_iError_NoError)
			{
				sendBroadcast(client.getName() + " has joined the room!");
				loadChatLog();
				client.PushMessageList(m_qsChatLog);
			}
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }

    public int leaveChatRoom(Info_itf client) throws RemoteException
    {
		int res = Registry_itf.s_iError_NoError;
        try
        {
            Registry registry = LocateRegistry.getRegistry();
            Registry_itf r = (Registry_itf) registry.lookup("RegistryService");
            res = r.unregister(client);
			if (res == Registry_itf.s_iError_NoError)
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
	
	public String getErrorMessage(int iErrorCode)
    {
        return s_asErrorMessages[iErrorCode];
    }

    public void saySomething(String scrName, String message) throws RemoteException
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry();
			Registry_itf r = (Registry_itf) registry.lookup("RegistryService");
            List<String> clientList = r.getListOfClients();
            for (Iterator<String> i = clientList.iterator(); i.hasNext();) 
			{
				String clientName = i.next();
				if (!clientName.equals(scrName))
				{
					Info_itf client = (Info_itf) registry.lookup(ChatClient.GenerateServiceName(clientName));
					if (client != null)
						client.PushMessage(scrName + ": " + message);
				}
			}

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void sendBroadcast(String message)
    {
		try
		{
			saySomething("", message);
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
			while ((line = reader.readLine()) != null) {
				m_qsChatLog.add(line);
			}
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}
}
