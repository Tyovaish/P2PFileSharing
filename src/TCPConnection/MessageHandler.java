package TCPConnection;

import Message.*;
import TCPConnection.Neighbor.NeighborState;

import java.nio.ByteBuffer;

import static Message.Message.*;

/**
 * Created by Trevor on 11/3/2017.
 */
public class MessageHandler {
    final boolean debug=false;
    TCPConnection tcpConnection;
    NeighborState currentNeighborState;
    MessageHandler(TCPConnection tcpConnection,  NeighborState neighbor){
        this.tcpConnection=tcpConnection;
        this.currentNeighborState=neighbor;
    }
    public void handleMessage(Message message){
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
            default:
                System.out.println("ERROR: Message Type Invalid");
        }

    }
    public void handleSendingMessage(){
        boolean isInterested=currentNeighborState.checkIfInterested(tcpConnection.getFile());
        if(!currentNeighborState.hasSentBitfield()){
            sendBitfieldMessage();
            currentNeighborState.sentBitfield();
        } else if(!currentNeighborState.isChokingClient()&&isInterested){
            sendRequestMessage(currentNeighborState.getRandomPiece(tcpConnection.getFile()));
        } else if(isInterested && !currentNeighborState.hasSentInterested()){
            sendInterestedMessage();
        } else if(!currentNeighborState.hasSentNotInterested()){
            sendNotInterestedMessage();
        }
    }

    private void handlePieceMessage(Message message) {
        if(debug){
            System.out.println("Recieved "+ByteBuffer.wrap(message.getPayload(),0,4).getInt());
        }
       int pieceIndex=ByteBuffer.wrap(message.getPayload(),0,4).getInt();
       byte [] payLoad=ByteBuffer.wrap(message.getPayload(),4,message.getPayloadLength()-4).array();
       if(!tcpConnection.getFile().hasPiece(pieceIndex)){
            tcpConnection.getInformationLogger().logDownloading(currentNeighborState.getNeighborPeerID(),pieceIndex,tcpConnection.getFile().getNumberOfPiecesInPossession()+1);
        }
       tcpConnection.getFile().setPiece(payLoad,pieceIndex);
       tcpConnection.getClient().sendHaveMessageToNeighbors(pieceIndex);

    }

    private void handleUnchokeMessage(Message message) {
        if(debug){
            System.out.println("Recieved Unchoke");
        }
        tcpConnection.getInformationLogger().logChoke(currentNeighborState.getNeighborPeerID());
        currentNeighborState.unchokeClient();
    }

    private void handleRequestMessage(Message message) {
        if(debug){
            System.out.println("Recieved Request");
        }
        int pieceIndex=ByteBuffer.wrap(message.getPayload()).getInt();
        sendPieceMessage(pieceIndex);
    }

    private void handleNotInterestedMessage(Message message) {
        if(debug){
            System.out.println("Recieved Not Interested");
        }
        currentNeighborState.setNotInterestedInClient();
        tcpConnection.getInformationLogger().logNotInterestedMessage(currentNeighborState.getNeighborPeerID());
    }

    private void handleInterestedMessage(Message message) {
        if (debug) {
            System.out.println("Recieved Intereseted");
        }
        currentNeighborState.setInterestedInClient();
        tcpConnection.getInformationLogger().logInterestedMessage(currentNeighborState.getNeighborPeerID());
    }

    private void handleHaveMessage(Message message) {
        if(debug){
            System.out.println("Recieved Have Message");
        }
        int pieceIndex=ByteBuffer.wrap(message.getPayload()).getInt();
        if(!currentNeighborState.knowHasPieceAlready(pieceIndex)) {
            tcpConnection.getInformationLogger().logHaveMessage(currentNeighborState.getNeighborPeerID(), pieceIndex);
        }
        currentNeighborState.updateBitField(pieceIndex);

    }
    private void handleChokeMessage(Message message) {
        if(debug){
            System.out.println("Recieved Choke");
        }
        currentNeighborState.chokeClient();
        tcpConnection.getInformationLogger().logChoke(currentNeighborState.getNeighborPeerID());
    }

    private void handleBitfieldMessage(Message message) {
        if(debug){
            System.out.println("Recieved Bitfield");
        }
        currentNeighborState.recievedBitfield(message.getPayload());
    }

     public void sendHaveMessage(int pieceIndex){
        if(debug){
            System.out.println("Sent Have");
        }
        tcpConnection.sendMessage(new Message(HAVE,pieceIndex));
    }
    public synchronized void sendBitfieldMessage(){
        if(debug){
            System.out.println("Sent Bitfield");
        }
        tcpConnection.sendMessage(new Message(BITFIELD,tcpConnection.getFile().getBitFieldMessage()));
    }
     public synchronized void sendInterestedMessage(){
        if(debug){
            System.out.println("Sent Interested");
        }
        currentNeighborState.setInterestedInNeighbor();
        tcpConnection.sendMessage(new Message(INTERESTED));
    }
     public synchronized void sendNotInterestedMessage(){
        if(debug){
            System.out.println("Sent Not Interested");
        }
        currentNeighborState.setNotInterestedInNeighbor();
        tcpConnection.sendMessage(new Message(NOTINTERESTED));
    }
     public synchronized void sendPieceMessage(int pieceIndex){
        if(debug){
            System.out.println("Sent Piece Message");
            System.out.println("Piece Looking For"+pieceIndex);
        }
        tcpConnection.sendMessage(new Message(PIECE,pieceIndex,tcpConnection.getFile().getPiece(pieceIndex)));
    }
    public synchronized void sendUnchokeMessage(){
        if(debug){
            System.out.println("Sent Unchoke Message");
        }
        tcpConnection.sendMessage(new Message(UNCHOKE));
    }
    public synchronized void sendChokeMessage(){
        if(debug){
            System.out.println("Sent Choke");
        }
        tcpConnection.sendMessage(new Message(CHOKE));
    }
    public synchronized void sendRequestMessage(int pieceIndex){
        if(debug){
            System.out.println("Sent Request");
            System.out.println("Piece Requested "+pieceIndex);
        }
        tcpConnection.sendMessage(new Message(REQUEST,pieceIndex));
    }

}
