import java.rmi.registry.*;
import java.rmi.*;

public  class ChatAppImpl implements ChatApp {

	public Hello2Impl(String s) {
		message = s ;
	}

	public String joinChatRoom()  throws RemoteException
	{
	}
	public String leaveChatRoom() throws RemoteException
	{
	}
	public String saySomething(String s) throws RemoteException
	{
	}
}
