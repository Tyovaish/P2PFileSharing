package TCPConnection;

import Message.*;
import TCPConnection.Neighbor.NeighborState;

import java.io.OutputStream;
import java.nio.ByteBuffer;

import static Message.Message.*;

/**
 * Created by Trevor on 11/3/2017.
 */
public class MessageHandler {
    final boolean debug=true;
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

    private void handlePieceMessage(Message message) {
        /*
        byte[] payload = message.getByteMessage();
        int index = payload[0:3].toInt();
        file.setPiece(payload, index);
        */
    }

    private void handleUnchokeMessage(Message message) {
        if(debug){
            System.out.println("Recieved Unchoke");
        }

        currentNeighborState.unchokeClient();
    }

    private void handleRequestMessage(Message message) {
        if(debug){
            System.out.println("Recieved Request");
        }
        /*
        byte[] payload = message.getByteMessage();
        int index = payload.toInt();
        byte[] piece = file.getPiece(index);
        */
    }

    private void handleNotInterestedMessage(Message message) {
        if(debug){
            System.out.println("Recieved Not Interested");
        }
        currentNeighborState.setNotInterestedInClient();
    }

    private void handleInterestedMessage(Message message) {
        if (debug) {
            System.out.println("Recieved Intereseted");
        }
        currentNeighborState.setInterestedInClient();
    }

    private void handleHaveMessage(Message message) {
        if(debug){
            System.out.println("Recieved Have Message");
        }

    }


    private void handleChokeMessage(Message message) {
        if(debug){
            System.out.println("Recieved Choke");
        }
        currentNeighborState.isChokingClient();
    }

    private void handleBitfieldMessage(Message message) {
        if(debug){
            System.out.println("Recieved Bitfield");
        }
        currentNeighborState.updateBitfield(message.getPayload());
    }

    public void handleSendingMessage(){
        if(!currentNeighborState.hasSentBitfield()){
            sendBitfieldMessage();
            currentNeighborState.sentBitfield();
        } else if(!currentNeighborState.isChokingClient()&&currentNeighborState.isInterestedInNeighbor()){
            sendRequestMessage(3);
        } else if(currentNeighborState.isInterestedInNeighbor()){
            sendInterestedMessage();
        } else {
            sendNotInterestedMessage();
        }
    }
     public void sendHaveMessage(int pieceIndex){
        if(debug){
            System.out.println("Sent Have");
        }
        tcpConnection.sendMessage(new Message(HAVE));
    }
    public void sendBitfieldMessage(){
        if(debug){
            System.out.println("Sent Bitfield");
        }
        tcpConnection.sendMessage(new Message(BITFIELD));
    }
     public void sendInterestedMessage(){
        if(debug){
            System.out.println("Sent Interested");
        }
        tcpConnection.sendMessage(new Message(INTERESTED));
    }
     public void sendNotInterestedMessage(){
        if(debug){
            System.out.println("Sent Not Interested");
        }
        tcpConnection.sendMessage(new Message(NOTINTERESTED));
    }
     public void sendPieceMessage(int pieceIndex){
        if(debug){
            System.out.println("Sent Piece Message");
        }
        tcpConnection.sendMessage(new Message(PIECE));
    }
             public void sendUnchokeMessage(){
        if(debug){
            System.out.println("Sent Unchoke Message");
        }
        tcpConnection.sendMessage(new Message(UNCHOKE));
    }
    synchronized public void sendChokeMessage(){
        if(debug){
            System.out.println("Sent Choke");
        }
        tcpConnection.sendMessage(new Message(CHOKE));
    }
    synchronized public void sendRequestMessage(int pieceIndex){
        if(debug){
            System.out.println("Sent Request");
        }
        tcpConnection.sendMessage(new Message(REQUEST));
    }

}
