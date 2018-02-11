
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class ChatServer
{

    public static void main(String[] args)
    {
        try
        {
            // Create a Hello remote object
            RegistryImpl r = new RegistryImpl();
            ChatAppImpl c = new ChatAppImpl();

            Registry_itf r_stub = (Registry_itf) UnicastRemoteObject.exportObject(r, 0);
            ChatApp c_stub = (ChatApp) UnicastRemoteObject.exportObject(c, 0);

            // Register the remote object in RMI registry with a given identifier
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("RegistryService", r_stub);
            registry.rebind("ChatingService", c_stub);

            System.out.println("Server ready");

        } catch (Exception e)
        {
            System.err.println("Error on server :" + e);
            e.printStackTrace();
        }
    }
}
