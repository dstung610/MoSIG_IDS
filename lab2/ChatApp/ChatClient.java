import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.io.*;
import java.util.LinkedList;
import java.lang.Thread;

public class ChatClient implements Info_itf, Runnable
{
  	ChatClient instance = null;
  	private static int s_IDcount = 0;

  	private String m_sName;
  	private LinkedList<String> m_qsMessageBuffer;
	
	private int m_iMessageCount;

  	public ChatClient(String name)
  	{
	m_sName = name;
	
	m_qsMessageBuffer = new LinkedList<String>();
	m_iMessageCount = 0;
  	}
  	
  	public String getName()
  	{
	  	return m_sName;
  	}
  	 
  	static public String GenerateServiceName(String name)
  	{
	  	return "Client_"+ name + "_service";
  	}
  	
  	public String getServiceName()
  	{
	  	return GenerateServiceName(m_sName);
  	}

  	public void numberOfCalls(int number)
  	{
  	  	System.out.println("Number of calls: " + number);
  	}
  	
  	public void PushMessage(String message)throws RemoteException
  	{
	  	// System.out.println("INFO: PushMessage" + message);
	  	m_qsMessageBuffer.addLast(message);
	  	m_iMessageCount++;
  	}
  	
	public void PushMessageList(LinkedList<String> message)throws RemoteException
  	{
	  	// System.out.println("INFO: PushMessage" + message);
	  	m_qsMessageBuffer.addAll(message);
	  	m_iMessageCount += message.size();
  	}
	
  	public String PullMessage()
  	{
	  	// System.out.println("INFO: PullMessage");
	  	m_iMessageCount = m_iMessageCount > 0 ? m_iMessageCount - 1 : 0;
	  	return m_qsMessageBuffer.pollFirst();
  	}
  	
  	public int GetMessageCount()
  	{
	  	// System.out.println("INFO: GetMessageCount " + m_iMessageCount);
	  	return m_iMessageCount;
  	}
  	
  	public void run()
  	{
		while (true)
		{		
			if (GetMessageCount() > 0)
				System.out.println(PullMessage());
			
			try 
			{		
				Thread.sleep(300);
			} 
			catch (Exception e) 
			{
				System.out.println(e);
			}
		}
  	}
  	
  	public static void main(String [] args) 
  	{

  		try 
		{
			if (args.length != 2) {
				System.out.println("Usage: java ChatClient <rmiregistry host> <client name>");
				System.exit(1);
			}

			String host = args[0];
			ChatClient client = new ChatClient(args[1]);
			
			Info_itf client_stub = (Info_itf) UnicastRemoteObject.exportObject(client, 0);
			
			// Get remote object reference
			Registry registry = LocateRegistry.getRegistry();
					
			registry.rebind(client.getServiceName(), client_stub);
			
			Registry_itf r = (Registry_itf) registry.lookup("RegistryService");
			ChatApp chatApp = (ChatApp) registry.lookup("ChatingService");

			// Remote method invocation

			///TODO -
			///call ChatApp - joinChatRoom
			///if success - echo success message + send farewell

			System.out.println("Trying to join Chat room");
			int err = chatApp.joinChatRoom(client);
			if (err == Registry_itf.s_iError_NoError)
			{
				System.out.println("NOTICE: Chat room joined");
				System.out.println("type : '@-QUIT' to leave the chat room");

				String userInput = "";
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
				Thread threadPullServerMessage = new Thread(client);
				threadPullServerMessage.start();
			
				while ((userInput = stdIn.readLine()) != null )
				{
					if (userInput.contains(ChatApp.s_Command_Quit))
					{
						chatApp.leaveChatRoom(client);
						System.out.println("QUIT");
						System.exit(0);
					}
					else
					{
						///send userInput
						chatApp.saySomething(client.getName(), userInput);						
					}
				} 			
			}
			else
			{
				System.out.println(chatApp.getErrorMessage(err));
				System.exit(0);
			}
		}
		catch (Exception e)
		{
			System.err.println("Error on client: " + e);

		}
	
	}
}
