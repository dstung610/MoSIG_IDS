import java.rmi.*;

public interface ChatApp extends Remote {
	static public final s_Command_Quit = "@-QUIT";
	public String joinChatRoom()  throws RemoteException;
	public String leaveChatRoom() throws RemoteException;
	public String saySomething(String s) throws RemoteException;
}
