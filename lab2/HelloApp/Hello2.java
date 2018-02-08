import java.rmi.*;

public interface Hello2 extends Remote {
	public String sayHello(Info_itf client) throws RemoteException;
}
