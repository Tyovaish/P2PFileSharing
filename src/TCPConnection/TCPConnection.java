package TCPConnection;

import Message.Types.HandshakeMessage;
import Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Trevor on 10/18/2017.
 */
public class TCPConnection {
    private int peerID;
    private String hostName;
    private int portNumber;
    Socket socket;
    boolean isChoked=true;
    ObjectInputStream in;
    ObjectOutputStream out;

    public TCPConnection(int peerID, String hostName, int portNumber) throws IOException {
        this.peerID=peerID;
        this.hostName=hostName;
        this.portNumber=portNumber;
        socket=new Socket(hostName,portNumber);
        out=new ObjectOutputStream(socket.getOutputStream());
        out.flush();
    }
    public void sendNormalMessage(Message message) throws IOException {
        out.writeObject(message);
        out.flush();
    }
    public void sendHandShakeMessage(HandshakeMessage handshakeMessage) throws IOException {
        out.writeObject(handshakeMessage);
        out.flush();
    }
    public byte[] getMessage() throws IOException, ClassNotFoundException {
        in=new ObjectInputStream(socket.getInputStream());
        return (byte []) in.readObject();
    }
    public void unchoke(){
        isChoked=false;
    }
    public void choke(){
        isChoked=true;
    }
}
