import java.rmi.*;
import java.io.Serializable;
import java.rmi.registry.*;
import java.io.*;

public class ChatClient implements Info_itf, Accounting_itf, Serializable
{
  ChatClient instance = null;
  private static int s_IDcount = 0;

  private String m_sName;


  public ChatClient()
  {

  }
  public static void main(String [] args) {

  	try {
  	  if (args.length < 1) {
  	   System.out.println("Usage: java ChatClient <rmiregistry host> <numberOfCalls>");
  	   return;
      }

    	String host = args[0];
      ChatClient client = ChatClient.getInstance();
      client.m_sName = args[1];

    	// Get remote object reference
    	Registry registry = LocateRegistry.getRegistry();
      Registry_itf r = (Registry_itf) registry.lookup("RegistryService");
      ChatApp chatApp = (ChatApp) registry.lookup("ChatingService");

    	// Remote method invocation

      ///TODO -
      ///call ChatApp - joinChatRoom
      ///if success - echo success message + send farewell

      System.out.println("Trying to join Chat room");
      int iRoomSize = chatApp.joinChatRoom(client);
      System.out.println("Rooms size: " + iRoomSize);
      if (iRoomSize > 0)
      {
        System.out.println("NOTICE: Chat room joined");
        System.out.println("There are " + iRoomSize + " people");
        System.out.println("type : '@-QUIT' to leave the chat room");

        String userInput;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        while ((userInput = stdIn.readLine()) != null)
        {
          if (userInput.contains(ChatApp.s_Command_Quit))
          {
            ///TODO -
            ///send farewell
            ///call ChatApp - leaveChatRoom
            chatApp.leaveChatRoom(client);
            System.out.println("QUIT");
          }
          else
          {
            ///send userInput
            chatApp.saySomething(client, userInput);
            System.out.println("TEXT");
          }
          System.out.println("echo: " + userInput);
        }
      }
    } catch (Exception e)  {
  		System.err.println("Error on client: " + e);
  	}
  }

  public String getName()
  {
    return m_sName;
  }
  public static ChatClient getInstance()
  {
    return new ChatClient();
  }

  public void numberOfCalls(int number)
  {
    System.out.println("Number of calls: " + number);
  }
}
