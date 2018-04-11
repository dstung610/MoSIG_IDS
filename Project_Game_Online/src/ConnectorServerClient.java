import java.util.LinkedList;

class ConnectorServerClient {
    SenderBroadcast out;
    ReceiverOpened in;
    LinkedList<String> sBuffer;

    public ConnectorServerClient(String sExchanceName)
    {       
        sBuffer = new LinkedList<String>();
        out = new SenderBroadcast(GameSettings.host, sExchangeName);
        in = new ReceiverOpened(GameSettings.host, sExchangeName, sBuffer); 
    }

    public void send(String msg) {
        out.send(msg);
    }

    public String getMessage() {
        return sBuffer.pollFirst();
    }

    public void close() {
        out.close();
        in.close();
    }
}
