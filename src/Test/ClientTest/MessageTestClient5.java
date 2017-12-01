package Test.ClientTest;

import Peer.PeerClient;

/**
 * Created by Trevor on 11/17/2017.
 */
public class MessageTestClient5 {
    public static void main(String [] args){
        PeerClient client1 = new PeerClient(1005);
        client1.run();
    }
}
