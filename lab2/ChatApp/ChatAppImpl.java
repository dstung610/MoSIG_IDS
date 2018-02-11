import java.rmi.registry.*;
import java.rmi.*;
import java.util.*;

public class ChatAppImpl implements ChatApp
{
	 private static final String[] s_asErrorMessages = {
            "",
            "ERROR: Client ID is already registered",
			"ERROR: Client ID is not found"
    };
	
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
				sendBroadcast(client.getName() + " has joined the room!");
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
				sendBroadcast(client.getName() + " has left the room!");
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
		saySomething("", message);
    }
	
	
}
