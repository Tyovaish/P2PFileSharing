package Factory;

import Message.Types.*;

/**
 * Created by Trevor on 10/23/2017.
 */
public class MessageFactory {
        public static BitfieldMessage createBitfieldMessage(){
            return new BitfieldMessage();
        }
    public static ChokeMessage createChokeMessage(){
            return new ChokeMessage();
    }
    public static HaveMessage createHaveMessage(){
            return new HaveMessage();
    }
    public static InterestedMessage createInterestedMessage(){
            return new InterestedMessage();
    }
    public static NotInterestedMessage createNotInterestedMessage(){
            return new NotInterestedMessage();
    }
    public static PieceMessage createPieceMessage(){
            return new PieceMessage();
    }
    public static RequestMessage createRequestMessage(){
            return new RequestMessage();
    }
    public static UnChokeMessage createUnchokeMessage(){
            return new UnChokeMessage();
    }

}
