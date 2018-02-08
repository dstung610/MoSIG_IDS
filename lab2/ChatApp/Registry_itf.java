import java.rmi.*;

public interface Registry_itf extends Remote
{

  public static final int s_iErrorUnkown = 0;
  public static final int s_iErrorSameID = 1;

  public int register(Info_itf client) throws RemoteException;//return error code
  public int getNumberOfCalls(Info_itf client)throws RemoteException;
  public int getNumberOfClients()throws RemoteException;

  public String getErrorMessage(int iErrorCode);
}