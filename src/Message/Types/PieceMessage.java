package Message.Types;

import Message.NormalMessage;

import java.nio.ByteBuffer;

/**
 * Created by Trevor on 10/23/2017.
 */
public class PieceMessage extends NormalMessage {
    public PieceMessage(int pieceIndex, byte [] pieceDataInBytes){
        messageType=PIECE;
        byte [] pieceIndexInBytes= ByteBuffer.allocate(4).putInt(pieceIndex).array();
        super.messagePayload=new byte[pieceIndexInBytes.length+pieceDataInBytes.length];
        for(int i=0;i<pieceDataInBytes.length;i++){
            messagePayload[i]=pieceIndexInBytes[i];
        }
        for(int i=4;i<messagePayload.length;i++){
            messagePayload[i]=pieceDataInBytes[i];
        }
    }
}
