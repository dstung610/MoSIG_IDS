import java.util.LinkedList;

class ConnectorServerClient {
    SenderBroadcast out;
    ReceiverOpened in;
    LinkedList<String> sBuffer;

    public ConnectorServerClient()
    {       
        sBuffer = new LinkedList<String>();
        out = new SenderBroadcast(GameUtils.host, GameUtils.s_sExchangeNameServerClient);
        in = new ReceiverOpened(GameUtils.host, GameUtils.s_sExchangeNameClientServer, sBuffer); 
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
