package TCPConnection;
import Message.Message;
import Message.HandshakeMessage;
import Message.Types.*;
import Peer.NeighborState;
import Peer.PeerInfo;
import Peer.PeerProcess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Trevor on 10/18/2017.
 */
public class TCPConnection implements Runnable{
    PeerProcess peerProcess;
    NeighborState neighbor;
    PeerInfo currentPeerInfo;
    Socket socket;
    MessageHandler messageHandler;

    public TCPConnection(PeerProcess peerProcess,Socket socket){
        this.peerProcess=peerProcess;
        this.socket=socket;
        this.currentPeerInfo=peerProcess.getPeerInfo().copy();
        messageHandler=new MessageHandler(peerProcess,this);
    }
    public TCPConnection(PeerProcess peerProcess,PeerInfo peerInfo){
            try {
                neighbor = new NeighborState();
                this.peerProcess = peerProcess;
                socket = new Socket(peerInfo.getHostName(), peerInfo.getPortNumber());
                this.currentPeerInfo=peerProcess.getPeerInfo().copy();
                System.out.println("Connected to " + peerInfo.getPeerID());
                messageHandler=new MessageHandler(peerProcess,this);
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
    public void sendHandShakeMessage(){
        sendMessage(new HandshakeMessage(peerProcess.getPeerInfo().getPeerID()));
    }
    public void sendChokeMessage(){
        sendMessage(new ChokeMessage());
    }
    public void sendHaveMessage(){
        sendMessage(new HaveMessage());
    }
    public void sendBitfieldMessage(){
        sendMessage(new BitfieldMessage());
    }
    public void sendInterestedMessage(){
        sendMessage(new InterestedMessage());
    }
    public void sendNotInterestedMessage(){

    }
    public void sendPieceMessage(){
    }
    public void sendRequestMessage(){
        sendMessage(new RequestMessage());
    }
    public void sendUnChokeMessage(){
        sendMessage(new UnChokeMessage());
    }




    @Override
    public void run() {
        while(true) {
            sendMessage(new HandshakeMessage(1000));
            Message message = getMessage();
            messageHandler.handleMessage(message, neighbor);
            for(int i=0;i<message.getByteMessage().length;i++){
                System.out.print((char) message.getByteMessage()[i]);
            }
            System.out.println();
        }
    }
}

