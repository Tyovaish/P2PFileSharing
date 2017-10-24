package Message;

import java.io.Serializable;

/**
 * Created by Trevor on 10/23/2017.
 */
public abstract class Message implements Serializable{
    protected byte messageType;
    protected byte[] messageHeader;
    protected byte [] messagePayload;
    public byte[] getMessage(){
        messageHeader=new byte[5];
        messageHeader[4]=messageType;
        return messageHeader;
    }
    public void setMessagePayload(){

    }
    public void print(){
            byte [] message=getMessage();
            for(int i=0;i<5;i++){
                System.out.println(message[i]);
            }

    }


}
