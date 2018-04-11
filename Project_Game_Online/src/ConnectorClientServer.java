import java.util.LinkedList;

class ConnectorClientServer {
    Sender out;
    Receiver in;
    LinkedList<String> sBuffer;

    public ConnectorClientServer(String sSrcName, String sDstName)
    {        
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
