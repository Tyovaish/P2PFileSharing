package Message.Types;

import Message.NormalMessage;

import java.nio.ByteBuffer;

/**
 * Created by Trevor on 10/23/2017.
 */
public class HaveMessage extends NormalMessage {
    public HaveMessage(int pieceIndex){
        messageType=HAVE;
        super.messagePayload= ByteBuffer.allocate(4).putInt(pieceIndex).array();
    }
}
