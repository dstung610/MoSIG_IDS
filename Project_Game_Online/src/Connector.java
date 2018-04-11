import java.util.LinkedList;

class Connector {
    protected Sender out;
    protected Receiver in;
    protected LinkedList<String> sBuffer;

    public Connector(String sSrcName, String sDstName)
    {
        sBuffer = new LinkedList<String>();
        out = new Sender(GameSettings.host, GameSettings.GenerateChanelName(sSrcName, sDstName));
        in = new Receiver(GameSettings.host, GameSettings.GenerateChanelName(sDstName, sSrcName), sBuffer);
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
