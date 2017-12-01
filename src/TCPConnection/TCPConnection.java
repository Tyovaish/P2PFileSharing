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
    public final boolean debug=false;
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
        try {
        this.peerClient = peerClient;
        this.socket=socket;
        this.clientPeerInfo= peerClient.getPeerInfo().copy();
        this.neighborPeerInfo=new PeerInfo();
        this.currentNeighborState=new NeighborState(neighborPeerInfo);
        this.messageHandler=new MessageHandler(this,this.currentNeighborState);
        this.out=new DataOutputStream(socket.getOutputStream());
        this.out.flush();
        this.in=new DataInputStream(socket.getInputStream());
        HandshakeMessage.sendHandshake(out,clientPeerInfo.getPeerID());
        HandshakeMessage.readHandshake(in,neighborPeerInfo);
        messageHandler.sendBitfieldMessage();
        messageHandler.handleMessage(getMessage());
        getInformationLogger().logTCPConnectionSent(neighborPeerInfo.getPeerID());
        getInformationLogger().logTCPConnectionRecieved(neighborPeerInfo.getPeerID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public TCPConnection(PeerClient peerClient, PeerInfo peerInfo) {
        try {
            this.socket = new Socket(peerInfo.getHostName(), peerInfo.getPortNumber());
            this.peerClient = peerClient;
            this.clientPeerInfo = peerClient.getPeerInfo().copy();
            this.neighborPeerInfo=peerInfo;
            this.currentNeighborState = new NeighborState(neighborPeerInfo);
            this.messageHandler = new MessageHandler(this, this.currentNeighborState);
            this.out=new DataOutputStream(socket.getOutputStream());
            this.out.flush();
            this.in=new DataInputStream(socket.getInputStream());
            HandshakeMessage.sendHandshake(out,clientPeerInfo.getPeerID());
            HandshakeMessage.readHandshake(in,neighborPeerInfo);
            if(debug)
            System.out.println(neighborPeerInfo.getPeerID());
            messageHandler.sendBitfieldMessage();
            messageHandler.handleMessage(getMessage());
            getInformationLogger().logTCPConnectionSent(neighborPeerInfo.getPeerID());
            getInformationLogger().logTCPConnectionRecieved(neighborPeerInfo.getPeerID());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(Message message){
        try {
            out.writeInt(message.getPayloadLength());
            out.writeByte(message.getMessageType());
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
    public synchronized MessageHandler getMessageHandler(){return messageHandler;}
    public synchronized NeighborState getNeighborState(){return currentNeighborState;}
    public FileParser getFile(){return peerClient.getFile();}
    public PeerInfo getNeighborPeerInfo() {
        return neighborPeerInfo;
    }
    public Socket getSocket(){return socket;}
    public synchronized InformationLogger getInformationLogger(){return peerClient.getInformationLogger();}
    public PeerClient getClient(){return peerClient;}
    public boolean isFinished(){return currentNeighborState.checkIfFinished();}
    @Override
    public void run() {
        while(true) {
            messageHandler.handleSendingMessage();
            messageHandler.handleMessage(getMessage());
        }
    }
}

