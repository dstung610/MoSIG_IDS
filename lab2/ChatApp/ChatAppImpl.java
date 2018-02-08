import java.rmi.registry.*;
import java.rmi.*;

public class ChatAppImpl implements ChatApp {

    public ChatAppImpl() {
    }

    public int joinChatRoom(Info_itf client) throws RemoteException {
		int iRoomSize = 0;
		try
		{
			Registry registry = LocateRegistry.getRegistry();
			Registry_itf r = (Registry_itf) registry.lookup("RegistryService");
			int res = r.register(client);
			if (res == Registry_itf.s_iError_NoError)
				iRoomSize = r.getNumberOfClients();

		} catch (Exception e){
				e.printStackTrace();
		}
		return iRoomSize;
    }

    public int leaveChatRoom(Info_itf client) throws RemoteException {
        return 0;
    }

    public void saySomething(String scrName, String desName, String message) throws RemoteException {
		try
		{
			Registry registry = LocateRegistry.getRegistry();
			Info_itf r = (Info_itf) registry.lookup(ChatClient.GenerateServiceName(desName));
			if (r != null)
				r.PushMessage(scrName + ": " + message);

		} catch (Exception e){
				e.printStackTrace();
		}
    }

    private void sendBroadcast(String message)
    {
      
    }
}
