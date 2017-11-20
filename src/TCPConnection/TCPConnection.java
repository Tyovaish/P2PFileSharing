package TCPConnection;
import Logger.InformationLogger;
import Message.Message;
import TCPConnection.Neighbor.NeighborState;
import Peer.PeerInfo;
import Peer.PeerClient;
import Message.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import File.*;

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
    DataOutputStream out;
    DataInputStream in;
    InformationLogger informationLogger;
    public TCPConnection(PeerClient peerClient, Socket socket){
        this.peerClient = peerClient;
        this.socket=socket;
        this.clientPeerInfo= peerClient.getPeerInfo().copy();
        this.neighborPeerInfo=new PeerInfo();
        try {
            this.out=new DataOutputStream(socket.getOutputStream());
            out.flush();
            this.in=new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        HandshakeMessage.sendHandshake(out,clientPeerInfo.getPeerID());
        HandshakeMessage.readHandshake(in,neighborPeerInfo);
        this.currentNeighborState=new NeighborState(neighborPeerInfo);
        messageHandler=new MessageHandler(this,this.currentNeighborState);
    }
    public TCPConnection(PeerClient peerClient, PeerInfo peerInfo) {
        try {
            this.peerClient = peerClient;
            this.clientPeerInfo = peerClient.getPeerInfo().copy();
            this.neighborPeerInfo=peerInfo;
            socket = new Socket(peerInfo.getHostName(), peerInfo.getPortNumber());

            this.out=new DataOutputStream(socket.getOutputStream());
            out.flush();
            this.in=new DataInputStream(socket.getInputStream());

            HandshakeMessage.sendHandshake(out,clientPeerInfo.getPeerID());
            HandshakeMessage.readHandshake(in,neighborPeerInfo);
            this.currentNeighborState = new NeighborState(neighborPeerInfo);
            messageHandler = new MessageHandler(this, this.currentNeighborState);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(Message message){
        try {
            out.writeInt(message.getPayloadLength());
            out.write(message.getMessageType());
            out.write(message.getPayload());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Message getMessage() {
        try {
           int payloadLength=in.readInt();
           byte messageType=in.readByte();
           byte [] payload=new byte[payloadLength];
           in.readFully(payload);
           return new Message(messageType,payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public MessageHandler getMessageHandler(){return messageHandler;}
    public PeerInfo getNeighborPeerInfo(){return neighborPeerInfo;}
    public PeerInfo getClientPeerInfo(){return clientPeerInfo;}
    public NeighborState getNeighborState(){return getNeighborState();}
    public FileParser getFile(){return peerClient.getFile();}
    public Socket getSocket(){return socket;}
    public InformationLogger getInformationLogger(){return peerClient.getInformationLogger();}
    public boolean isFinished(){return currentNeighborState.checkIfFinished();}
    @Override
    public void run() {
        while(true) {
            messageHandler.handleSendingMessage();
            messageHandler.handleMessage(getMessage());
        }
    }
}

