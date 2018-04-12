import java.util.LinkedList;

class ConnectorServerClient {
    SenderBroadcast out;
    Sender outPrivate;
    ReceiverOpened in;
    LinkedList<String> sBuffer;

    public ConnectorServerClient() {
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
        if (outPrivate != null)
            outPrivate.close();
    }

    public void openPrivateChanel(String sChanelName) {
        outPrivate = new Sender(GameUtils.host, sChanelName);
    }

    public void sendPrivateMsg(String msg) {
        outPrivate.send(msg);
    }

    public void closePrivateChanel() {
        if (outPrivate != null)
            outPrivate.close();
    }
}
