import java.rmi.registry.*;
import java.rmi.*;

public  class Hello2Impl implements Hello2 {

	private String message;

	public Hello2Impl(String s) {
		message = s ;
	}

	public String sayHello(Info_itf client) throws RemoteException {
		try {
			Registry registry = LocateRegistry.getRegistry();
	  	Registry_itf r = (Registry_itf) registry.lookup("RegistryService");
			r.register(client);
		} catch (Exception e){
				e.printStackTrace();
		}

		return client.getName() + " - method 2 : " + message ;
	}
}
