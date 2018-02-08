import java.rmi.*;
import java.io.Serializable;
import java.rmi.registry.*;
import java.io.*;

public class HelloClient implements Info_itf, Accounting_itf, Serializable
{
  HelloClient instance = null;
  private static int s_IDcount = 0;

  private String m_sName;


  public HelloClient()
  {

  }
  public static void main(String [] args) {

  	try {
  	  if (args.length < 1) {
  	   System.out.println("Usage: java HelloClient <rmiregistry host> <numberOfCalls>");
  	   return;
      }

    	String host = args[0];
      HelloClient client = HelloClient.getInstance();
      client.m_sName = args[1];
      int loop = Integer.parseInt(args[2]);

    	// Get remote object reference
    	Registry registry = LocateRegistry.getRegistry();
    	Hello h = (Hello) registry.lookup("HelloService");
      Hello2 h2 = (Hello2) registry.lookup("Hello2Service");
      Registry_itf r = (Registry_itf) registry.lookup("RegistryService");
      ChatApp c = (ChatApp) registry.lookup("ChatingService");

    	// Remote method invocation

      //Exercise 5
      //Method 1

      String res = h.sayHello();
      System.out.println(res);

      //Method 2
      res = h.sayHello(client);
    	System.out.println(res);

      res = h2.sayHello(client);
    	System.out.println(res);

      //Exercise 6
      for (int i = 0; i < loop; i++)
  	  {
        res = h2.sayHello(client);
  	    System.out.println(res);
  	  }
      client.numberOfCalls(r.getNumberOfCalls(client));

      //Exercise 7

      ///TODO -
      ///call ChatApp - joinChatRoom
      ///if success - echo success message + send farewell
      String userInput;
      BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

      while ((userInput = stdIn.readLine()) != null)
      {
        if (userInput.contains(ChatApp.s_Command_Quit))
        {
          ///TODO -
          ///send farewell
          ///call ChatApp - leaveChatRoom
          System.out.println("QUIT");
        }
        else
        {
          System.out.println("TEXT");
          ///send userInput
        }
        System.out.println("echo: " + userInput);
      }

    } catch (Exception e)  {
  		System.err.println("Error on client: " + e);
  	}
  }

  public String getName()
  {
    return m_sName;
  }
  public static HelloClient getInstance()
  {
    return new HelloClient();
  }

  public void numberOfCalls(int number)
  {
    System.out.println("Number of calls: " + number);
  }
}
