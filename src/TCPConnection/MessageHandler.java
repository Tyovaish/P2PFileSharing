package TCPConnection;

import Message.Message;
import Peer.NeighborState;
import Peer.PeerProcess;

import static Message.Message.*;

/**
 * Created by Trevor on 11/3/2017.
 */
public class MessageHandler {
    PeerProcess peerProcess;
    TCPConnection tcpConnection;
    MessageHandler(PeerProcess peerProcess,TCPConnection tcpConnection){
        this.peerProcess=peerProcess;
        this.tcpConnection=tcpConnection;
    }
    public void handleMessage(Message message, NeighborState neighbor){
        switch(message.getMessageType()){
            case BITFIELD:
                handleBitfieldMessage(message);
                break;
            case CHOKE:
                handleChokeMessage(message);
                break;
            case HANDSHAKE:
                handleHandshakeMessage(message);
                break;
            case HAVE:
                handleHaveMessage(message, neighbor);
                break;
            case INTERESTED:
                handleInterestedMessage(message, neighbor);
                break;
            case NOTINTERESTED:
                handleNotInterestedMessage(message, neighbor);
                break;
            case REQUEST:
                handleRequestMessage(message);
                break;
            case UNCHOKE:
                handleUnchokeMessage(message, neighbor);
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

    private void handleUnchokeMessage(Message message, NeighborState neighbor) {
        neighbor.unchokeClient();
    }

    private void handleRequestMessage(Message message) {
        /*
        byte[] payload = message.getByteMessage();
        int index = payload.toInt();
        byte[] piece = file.getPiece(index);
        */
    }

    private void handleNotInterestedMessage(Message message, NeighborState neighbor) {
        neighbor.setNotInterested();
    }

    private void handleInterestedMessage(Message message, NeighborState neighbor) {
        neighbor.setInterested();
    }

    private void handleHaveMessage(Message message, NeighborState neighbor) {

    }

    private void handleHandshakeMessage(Message message) {

    }

    private void handleChokeMessage(Message message) {

    }

    private void handleBitfieldMessage(Message message) {

    }
}
