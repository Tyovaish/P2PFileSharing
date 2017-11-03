package Message;

import java.io.Serializable;

/**
 * Created by Trevor on 10/25/2017.
 */
public abstract class Message implements Serializable{
    public static final byte CHOKE=0;
    public static final byte UNCHOKE=1;
    public static final byte INTERESTED=2;
    public static final byte NOTINTERESTED=3;
    public static final byte HAVE=4;
    public static final byte BITFIELD=5;
    public static final byte REQUEST=6;
    public static final byte PIECE=7;
    public static final byte HANDSHAKE=8;

    abstract public byte[] getByteMessage();
    public byte [] getSliceOfMessage(int start,int end){
        byte [] sliceOfMessaage=new byte[end-start+1];
        byte[] message=getByteMessage();
        for(int j=0,i=start;i<end;j++,i++){
            sliceOfMessaage[j]=message[i];
        }
        return sliceOfMessaage;
    }
    abstract public byte getMessageType();
}
