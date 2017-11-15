package TCPConnection;
import File.PeerInfoFileParser;
import Message.Message;
import Message.HandshakeMessage;
import Message.Types.*;
import Peer.NeighborState;
import Peer.PeerInfo;
import Peer.PeerClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Trevor on 10/18/2017.
 */
public class TCPConnection implements Runnable{
    PeerClient peerClient;
    NeighborState neighbor;
    PeerInfo currentPeerInfo;
    PeerInfo neighborPeerInfo;
    Socket socket;
    MessageHandler messageHandler;

    public TCPConnection(PeerClient peerClient, Socket socket){
        this.peerClient = peerClient;
        this.socket=socket;
        this.neighbor=new NeighborState();
        this.currentPeerInfo= peerClient.getPeerInfo().copy();
        this.neighborPeerInfo= PeerInfoFileParser.getPeerInfo(HandshakeMessage.getPeerIDFromHandshakeMessage(getMessage()));
        messageHandler=new MessageHandler(this,this.neighbor);
    }
    public TCPConnection(PeerClient peerClient, PeerInfo peerInfo){
            try {
                this.neighbor = new NeighborState();
                this.peerClient = peerClient;
                this.currentPeerInfo= peerClient.getPeerInfo().copy();
                socket = new Socket(peerInfo.getHostName(), peerInfo.getPortNumber());
                System.out.println("Connected to " + peerInfo.getPeerID());
                messageHandler=new MessageHandler(this,this.neighbor);
                sendMessage(new HandshakeMessage(peerClient.getPeerInfo().getPeerID()));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void sendMessage(Message message){
        try {
            ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(message);
            out.flush();
            System.out.println("Sent message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Message getMessage() {
        try {
            System.out.println("Waiting for message");
            ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
            return (Message) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        while(true) {
            sendMessage(new BitfieldMessage());
            Message message = getMessage();
            messageHandler.handleMessage(message);
            System.out.println();
        }
    }
    public PeerInfo getCurrentPeerInfo(){
        return currentPeerInfo;
    }
}

