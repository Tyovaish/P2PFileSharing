package TCPConnection;
import File.PeerInfoFileParser;
import Message.Message;
import Message.HandshakeMessage;
import Message.Types.*;
import TCPConnection.Neighbor.NeighborState;
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
    NeighborState currentNeighborState;
    PeerInfo clientPeerInfo;
    PeerInfo neighborPeerInfo;
    Socket socket;
    MessageHandler messageHandler;

    public TCPConnection(PeerClient peerClient, Socket socket){
        this.peerClient = peerClient;
        this.socket=socket;
        this.currentNeighborState=new NeighborState();
        this.clientPeerInfo= peerClient.getPeerInfo().copy();
        this.neighborPeerInfo=new PeerInfo();
        messageHandler=new MessageHandler(this,this.currentNeighborState);
    }
    public TCPConnection(PeerClient peerClient, PeerInfo peerInfo){
            try {
                this.currentNeighborState = new NeighborState();
                this.peerClient = peerClient;
                this.clientPeerInfo= peerClient.getPeerInfo().copy();
                socket = new Socket(peerInfo.getHostName(), peerInfo.getPortNumber());
                System.out.println("Connected to " + peerInfo.getPeerID());
                messageHandler=new MessageHandler(this,this.currentNeighborState);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public synchronized void sendMessage(Message message){
        try {
            ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Message getMessage() {
        try {
            ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
            return (Message) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public MessageHandler getMessageHandler(){return messageHandler;}
    public PeerInfo getNeighborPeerInfo(){return neighborPeerInfo;}
    public PeerInfo getClientPeerInfo(){return clientPeerInfo;}
    @Override
    public void run() {
        while(true) {
            System.out.println("Sent Message");
            messageHandler.handleSendingMessage();
            messageHandler.handleMessage(getMessage());
            System.out.println("Got Problem");
        }
    }
}

