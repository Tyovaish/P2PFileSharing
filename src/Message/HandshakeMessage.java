package Message;

import Message.Message;
import sun.misc.resources.Messages_es;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by Trevor on 10/23/2017.
 */
public class HandshakeMessage extends Message {
    final private static String handshakeHeader="P2PFILESHARINGPROJ";
    final private static int handShakeSize=32;
    int peerID;
    public static boolean checkHandshakeMessage(Message message){
        byte [] handshake=message.getByteMessage();
        for(int i=0;i<handshakeHeader.getBytes().length;i++){
            if(handshakeHeader.getBytes()[i]!=handshake[i]){
                return false;
            }
        }
        return true;

    }
    public static int getPeerIDFromHandshakeMessage(Message message){
        return ByteBuffer.wrap(message.getByteMessage(),28,4).getInt();
    }
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
