package Message.Types;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by Trevor on 10/23/2017.
 */
public class HandshakeMessage implements Serializable {
    final private String handshakeHeader="P2PFILESHARINGPROJ";
    final private int handShakeHeaderSize=18;
    final private int handShakeSize=32;
    public byte[] getMessage(int peerID){
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
    public void print(){
        byte [] message=getMessage(5000);
        for(int i=0;i<handShakeSize;i++){
            System.out.println(i+" "+(int) message[i]);
        }
        int number=ByteBuffer.wrap(message,28,4).getInt();
        System.out.println(number);
    }

}
