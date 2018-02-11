
import java.rmi.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class RegistryImpl implements Registry_itf
{

    private static final String[] s_asErrorMessages = {
            "",
            "ERROR: Client ID is already registered"
    };

    public HashMap<String, Integer> m_listClients;

    public RegistryImpl()
    {
        m_listClients = new HashMap<String, Integer>();
    }

    public int register(Info_itf client) throws RemoteException
    {
        int resCode = s_iError_NoError;

        boolean isFound = (m_listClients.containsKey(client.getName()));
        if (!isFound)
        {
            m_listClients.put(client.getName(), 0);
            System.out.println(client.getName() + " registered");
        } else
        {
            resCode = s_iErrorSameID;
        }

        return resCode;
    }

    public int getNumberOfCalls(Info_itf client) throws RemoteException
    {
        boolean found = (m_listClients.containsKey(client.getName()));
        int count = 0;
        if (found)
        {
            count = m_listClients.get(client.getName());
        }

        return count;
    }

    public int getNumberOfClients() throws RemoteException
    {
        return m_listClients.size();
    }

    public String getErrorMessage(int iErrorCode)
    {
        return s_asErrorMessages[iErrorCode];
    }

    public List<String> getListOfClients() throws RemoteException
    {
        return new ArrayList<String>(m_listClients.keySet());
    }
}
