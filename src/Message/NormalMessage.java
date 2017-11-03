package Message;

import java.io.Serializable;

/**
 * Created by Trevor on 10/23/2017.
 */
public abstract class NormalMessage extends Message{
    protected byte messageType;
    protected byte[] messageHeader;
    protected byte [] messagePayload;
    public byte[] getByteMessage(){
        messageHeader=new byte[5];
        messageHeader[4]=messageType;
        return messageHeader;
    }
    public void setMessagePayload(byte [] payload){
        messagePayload=payload;
    }
    public byte getMessageType(){return messageType;}



}
