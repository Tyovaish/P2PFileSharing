package Message;

import java.io.Serializable;
import java.nio.ByteBuffer;

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
    public Message(byte messageType,int pieceIndex){
        this.messageType=messageType;
        this.payload= ByteBuffer.allocate(4).putInt(pieceIndex).array();
    }
    public Message(byte messageType,int pieceIndex,byte[] payload){
        byte [] pieceIndexInBytes=ByteBuffer.allocate(4).putInt(pieceIndex).array();
        this.payload=new byte[pieceIndexInBytes.length+payload.length];
        for(int i=0;i<pieceIndexInBytes.length;++i){
            this.payload[i]=pieceIndexInBytes[i];
        }
        for(int i=pieceIndexInBytes.length;i<this.payload.length;++i){
            this.payload[i]=payload[i];
        }
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
