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
				r.register(client);

			} catch (Exception e){
					e.printStackTrace();
			}

    }

    public int leaveChatRoom(Info_itf client) throws RemoteException {
        return 0;
    }

    public void saySomething(Info_itf client, String message) throws RemoteException {

    }
}
