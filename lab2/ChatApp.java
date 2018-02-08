import java.rmi.*;

public interface ChatApp extends Remote {
	static public final String s_Command_Quit = "@-QUIT";
	public String joinChatRoom()  throws RemoteException;
	public String leaveChatRoom() throws RemoteException;
	public String saySomething(String s) throws RemoteException;
}
