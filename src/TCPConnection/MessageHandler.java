package TCPConnection;

import Message.*;
import Message.Types.*;
import TCPConnection.Neighbor.NeighborState;

import static Message.Message.*;

/**
 * Created by Trevor on 11/3/2017.
 */
public class MessageHandler {
    TCPConnection tcpConnection;
    NeighborState currentNeighborState;
    MessageHandler(TCPConnection tcpConnection,  NeighborState neighbor){
        this.tcpConnection=tcpConnection;
        this.currentNeighborState=neighbor;
    }
    public void handleMessage(Message message){
        if(message.getMessageType()!=HANDSHAKE && !currentNeighborState.hasRecievedHandshake()){
            return;
        }
        switch(message.getMessageType()){
            case BITFIELD:
                handleBitfieldMessage(message);
                break;
            case CHOKE:
                handleChokeMessage(message);
                break;
            case HAVE:
                handleHaveMessage(message);
                break;
            case INTERESTED:
                handleInterestedMessage(message);
                break;
            case NOTINTERESTED:
                handleNotInterestedMessage(message);
                break;
            case REQUEST:
                handleRequestMessage(message);
                break;
            case UNCHOKE:
                handleUnchokeMessage(message);
                break;
            case PIECE:
                handlePieceMessage(message);
                break;
            case HANDSHAKE:
                handleHandShakeMessage(message);
                currentNeighborState.hasRecievedHandshake();
                break;
            default:
                System.out.println("ERROR: Message Type Invalid");
        }

    }

    private void handlePieceMessage(Message message) {
        /*
        byte[] payload = message.getByteMessage();
        int index = payload[0:3].toInt();
        file.setPiece(payload, index);
        */
    }

    private void handleUnchokeMessage(Message message) {
        currentNeighborState.unchokeClient();
    }

    private void handleRequestMessage(Message message) {
        /*
        byte[] payload = message.getByteMessage();
        int index = payload.toInt();
        byte[] piece = file.getPiece(index);
        */
    }

    private void handleNotInterestedMessage(Message message) {
        currentNeighborState.setNotInterestedInClient();
    }

    private void handleInterestedMessage(Message message) {
        currentNeighborState.setInterestedInClient();
    }

    private void handleHaveMessage(Message message) {

    }


    private void handleChokeMessage(Message message) {
        currentNeighborState.isChokingClient();
    }

    private void handleBitfieldMessage(Message message) {

    }
    public void handleHandShakeMessage(Message message){

    }
    public void handleSendingMessage(){
        if(!currentNeighborState.hasSentHandshake()){
            sendHandShakeMessage();
            currentNeighborState.sentHandshake();
        }else if(!currentNeighborState.isChokingClient()&&currentNeighborState.isInterestedInNeighbor()){
            sendRequestMessage(3);
        } else if(currentNeighborState.isInterestedInNeighbor()){
            sendInterestedMessage();
        } else {
            sendNotInterestedMessage();
        }

    }
    synchronized public void sendHaveMessage(int pieceIndex){
        tcpConnection.sendMessage(new HaveMessage(pieceIndex));
    }
    synchronized public void sendBitfieldMessage(){
        tcpConnection.sendMessage(new BitfieldMessage(new byte[20]));
    }
    synchronized public void sendInterestedMessage(){
        tcpConnection.sendMessage(new InterestedMessage());
    }
    synchronized public void sendNotInterestedMessage(){
        tcpConnection.sendMessage(new NotInterestedMessage());
    }
    synchronized public void sendPieceMessage(int pieceIndex){
        tcpConnection.sendMessage(new PieceMessage(pieceIndex,new byte[10]));
    }
    synchronized public void sendUnchokeMessage(){
        tcpConnection.sendMessage(new UnChokeMessage());
    }
    synchronized public void sendChokeMessage(){
        tcpConnection.sendMessage(new ChokeMessage());
    }
    synchronized public void sendRequestMessage(int pieceIndex){
        tcpConnection.sendMessage(new RequestMessage(pieceIndex));
    }
    synchronized public void sendHandShakeMessage(){
        tcpConnection.sendMessage(new HandshakeMessage(tcpConnection.getClientPeerInfo().getPeerID()));
    }
}
