import java.rmi.*;

public interface ChatApp extends Remote {
	public String joinChatRoom()  throws RemoteException;
	public String leaveChatRoom() throws RemoteException;
	public String saySomething(String) throws RemoteException;
}
