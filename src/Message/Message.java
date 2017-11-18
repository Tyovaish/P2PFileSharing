package Message;

import java.io.Serializable;

/**
 * Created by Trevor on 10/25/2017.
 */
public class Message{
    public static final byte CHOKE=0;
    public static final byte UNCHOKE=1;
    public static final byte INTERESTED=2;
    public static final byte NOTINTERESTED=3;
    public static final byte HAVE=4;
    public static final byte BITFIELD=5;
    public static final byte REQUEST=6;
    public static final byte PIECE=7;
    public static final byte HANDSHAKE=8;
    byte messageType;
    byte [] payload;
    public Message(byte messageType, byte[] payload){
        this.messageType=messageType;
        this.payload=payload;
    }
    public Message(byte messageType){
        this.messageType=messageType;
        this.payload=new byte[0];
    }
    public byte[] getPayload(){
        return payload;
    }
    public byte getMessageType(){
        return messageType;
    }
    public int getPayloadLength(){
        return payload.length;
    }

}
