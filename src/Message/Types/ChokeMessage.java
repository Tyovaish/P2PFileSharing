package Message.Types;

import Message.Message;
import Message.NormalMessage;

/**
 * Created by Trevor on 10/23/2017.
 */
public class ChokeMessage extends NormalMessage {
    public ChokeMessage(){
        messageType=CHOKE;
    }
}
