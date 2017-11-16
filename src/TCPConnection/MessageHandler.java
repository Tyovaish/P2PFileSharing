package TCPConnection;

import Message.Message;
import TCPConnection.Neighbor.NeighborState;

import static Message.Message.*;

/**
 * Created by Trevor on 11/3/2017.
 */
public class MessageHandler {
    TCPConnection tcpConnection;
    NeighborState neighbor;
    MessageHandler(TCPConnection tcpConnection,  NeighborState neighbor){
        this.tcpConnection=tcpConnection;
        this.neighbor=neighbor;
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
        neighbor.unchokeClient();
    }

    private void handleRequestMessage(Message message) {
        /*
        byte[] payload = message.getByteMessage();
        int index = payload.toInt();
        byte[] piece = file.getPiece(index);
        */
    }

    private void handleNotInterestedMessage(Message message) {
        neighbor.setNotInterested();
    }

    private void handleInterestedMessage(Message message) {
        neighbor.setInterested();
    }

    private void handleHaveMessage(Message message) {

    }


    private void handleChokeMessage(Message message) {
        neighbor.isChoking();
    }

    private void handleBitfieldMessage(Message message) {

    }
}
