package Message.Types;

import Message.NormalMessage;

/**
 * Created by Trevor on 10/23/2017.
 */
public class BitfieldMessage extends NormalMessage {
    public BitfieldMessage(byte [] bitField){
        messageType=BITFIELD;
        super.messagePayload=bitField;
    }
}
