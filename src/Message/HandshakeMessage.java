package Message;

import Message.Message;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by Trevor on 10/23/2017.
 */
public class HandshakeMessage extends Message {
    final private String handshakeHeader="P2PFILESHARINGPROJ";
    final private int handShakeSize=32;
    int peerID;
    public HandshakeMessage(int peerID){
        this.peerID=peerID;
    }
    public byte[] getByteMessage(){
        byte [] handShakeMessage=new byte[handShakeSize];
        byte [] handShakeHeaderInBytes= handshakeHeader.getBytes();
        for(int i=0;i<handShakeHeaderInBytes.length;i++){
            handShakeMessage[i]=handShakeHeaderInBytes[i];
        }
        byte[] peerIDInBytes= ByteBuffer.allocate(4).putInt(peerID).array();
        handShakeMessage[28]=peerIDInBytes[0];
        handShakeMessage[29]=peerIDInBytes[1];
        handShakeMessage[30]=peerIDInBytes[2];
        handShakeMessage[31]=peerIDInBytes[3];
        return handShakeMessage;
    }
    public byte getMessageType(){return Message.HANDSHAKE;}
}
