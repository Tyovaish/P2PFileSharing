package TCPConnection;

import Message.Message;
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
    public void handleMessage(Message message){
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

    }

    private void handleUnchokeMessage(Message message) {

    }

    private void handleRequestMessage(Message message) {

    }

    private void handleNotInterestedMessage(Message message) {

    }

    private void handleInterestedMessage(Message message) {

    }

    private void handleHaveMessage(Message message) {

    }

    private void handleHandshakeMessage(Message message) {

    }

    private void handleChokeMessage(Message message) {

    }

    private void handleBitfieldMessage(Message message) {

    }
}
