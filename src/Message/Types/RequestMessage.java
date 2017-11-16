package Message.Types;

import Message.NormalMessage;

import java.nio.ByteBuffer;

/**
 * Created by Trevor on 10/23/2017.
 */
public class RequestMessage extends NormalMessage {
    public RequestMessage(int pieceIndex){
        messageType=REQUEST;
        super.messagePayload= ByteBuffer.allocate(4).putInt(pieceIndex).array();
    }
}
