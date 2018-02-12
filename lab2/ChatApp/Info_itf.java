import java.rmi.*;
import java.util.LinkedList;

public interface Info_itf extends Remote
{
    public String getName() throws RemoteException;

    public void PushMessage(String message) throws RemoteException;
	public void PushMessageList(LinkedList<String> message)throws RemoteException;
}
