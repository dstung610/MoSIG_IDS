import java.rmi.*;

public interface ChatApp extends Remote {
	static public final String s_Command_Quit = "@-QUIT";
	public int joinChatRoom(Info_itf client)  throws RemoteException;
	public int leaveChatRoom(Info_itf client) throws RemoteException;
	public void saySomething(String scrName, String desName, String message) throws RemoteException;
}
