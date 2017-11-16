package Message.Types;

import Message.NormalMessage;

import java.nio.ByteBuffer;

/**
 * Created by Trevor on 10/23/2017.
 */
public class InterestedMessage extends NormalMessage {
    public InterestedMessage(int pieceIndex){
        messageType=INTERESTED;
        super.messagePayload= ByteBuffer.allocate(4).putInt(pieceIndex).array();
    }
}
