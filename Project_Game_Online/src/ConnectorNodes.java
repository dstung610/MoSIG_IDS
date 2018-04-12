import java.util.LinkedList;

class ConnectorNodes {
    protected Sender out;
    protected Receiver in;
    protected LinkedList<String> sBuffer;

    public ConnectorNodes (String sSrcName, String sDstName)
    {
        sBuffer = new LinkedList<String>();
        out = new Sender(GameUtils.host, GameUtils.GenerateChanelName(sSrcName, sDstName));
        in = new Receiver(GameUtils.host, GameUtils.GenerateChanelName(sDstName, sSrcName), sBuffer);
    }

    public void send(String msg) {
        out.send(msg);
    }

    public String getMessage() {
        return sBuffer.pollFirst();
    }

    public void stopReciver()
    {
        in.close();
    }


    public void close() {
        out.close();
        if (in != null)
            in.close();
    }
}
