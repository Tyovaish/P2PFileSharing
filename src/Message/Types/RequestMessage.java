package Message.Types;

import Message.NormalMessage;

/**
 * Created by Trevor on 10/23/2017.
 */
public class RequestMessage extends NormalMessage {
    public RequestMessage(){
        messageType=REQUEST;
    }
}
