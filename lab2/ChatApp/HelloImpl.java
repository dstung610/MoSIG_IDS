
import java.rmi.*;

public  class HelloImpl implements Hello {

	private String message;

	public HelloImpl(String s) {
		message = s ;
	}

	public String sayHello() throws RemoteException {
		return message ;
	}

	public String sayHello(String clientName) throws RemoteException {
		return clientName + " - method 1 : " + message ;
	}

	public String sayHello(Info_itf client) throws RemoteException {
		return client.getName() + " - method 2 : " + message ;
	}
}
