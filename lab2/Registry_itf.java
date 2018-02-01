import java.rmi.*;

public interface Registry_itf extends Remote
{
  public void register(Info_itf client) throws RemoteException;
  public int getNumberOfCalls(Info_itf client)throws RemoteException;
}
