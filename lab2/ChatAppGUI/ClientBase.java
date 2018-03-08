import java.rmi.*;
import java.util.List;

public interface ClientBase extends Remote
{

    public static final int s_iError_NoError = 0;
    public static final int s_iErrorSameID = 1;
	public static final int s_iErrorIDNotFound = 2;
	public static final int s_iErrorInvalideID = 3;

    public int register(String clientName) throws RemoteException;//return error code
	public int unregister(String clientName) throws RemoteException;//return error code

    public int getNumberOfClients() throws RemoteException;

    public List<String> getListOfClients() throws RemoteException;
}
