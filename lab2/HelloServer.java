
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class HelloServer {

  public static void  main(String [] args) {
	  try {
		  // Create a Hello remote object
	    HelloImpl h = new HelloImpl ("Hello world !");
      Hello2Impl h2 = new Hello2Impl ("Hello world 2!");
      RegistryImpl r = new RegistryImpl();
	    Hello h_stub = (Hello) UnicastRemoteObject.exportObject(h, 0);
      Hello2 h2_stub = (Hello2) UnicastRemoteObject.exportObject(h2, 0);
      Registry_itf r_stub = (Registry_itf) UnicastRemoteObject.exportObject(r, 0);

	    // Register the remote object in RMI registry with a given identifier
	    Registry registry= LocateRegistry.getRegistry();
		System.setProperty("java.rmi.server.hostname","127.0.0.1");
      registry.rebind("HelloService", h_stub);
      registry.rebind("Hello2Service", h2_stub);
      registry.rebind("RegistryService", r_stub);

      System.out.println ("Server ready");

	  } catch (Exception e) {
		  System.err.println("Error on server :" + e) ;
		  e.printStackTrace();
	  }
  }
}
