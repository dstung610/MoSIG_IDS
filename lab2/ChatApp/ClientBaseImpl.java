
import java.rmi.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ClientBaseImpl implements ClientBase
{
    public HashMap<String, Integer> m_listClients;

    public ClientBaseImpl()
    {
        m_listClients = new HashMap<String, Integer>();
    }

    public int register(String clientName) throws RemoteException
    {
        int resCode = s_iError_NoError;

        boolean isFound = (m_listClients.containsKey(clientName));
        if (!isFound)
        {
            m_listClients.put(clientName, 0);
            System.out.println(clientName + " registered");
        } else
        {
            resCode = s_iErrorSameID;
        }

        return resCode;
    }
	
	public int unregister(String clientName) throws RemoteException
    {
        int resCode = s_iError_NoError;

        boolean isFound = (m_listClients.containsKey(clientName));
        if (isFound)
        {
            m_listClients.remove(clientName, 0);
            System.out.println(clientName + "unregistered");
        } else
        {
            resCode = s_iErrorSameID;
        }

        return resCode;
    }

    public int getNumberOfClients() throws RemoteException
    {
        return m_listClients.size();
    }

    public List<String> getListOfClients() throws RemoteException
    {
        return new ArrayList<String>(m_listClients.keySet());
    }
}
