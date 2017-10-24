
import Message.Types.HandshakeMessage;
import TCPConnection.TCPConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerProcess implements Runnable {
    Socket requestSocket;           //socket connect to the server
    ObjectOutputStream out;         //stream write to the socket
    ObjectInputStream in;          //stream read from the socket
    private static final int portNumber = 8000;
    public void Peer() {}

    public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                TCPConnection tcpConnection = new TCPConnection(5000, "localHost", 8000);
                Socket connection = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                tcpConnection.sendHandShakeMessage(new HandshakeMessage());
                HandshakeMessage message = (HandshakeMessage) in.readObject();
                message.print();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


    }
    //send a message to the output stream

    //main method
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        PeerProcess client = new PeerProcess();
        client.run();
    }

}