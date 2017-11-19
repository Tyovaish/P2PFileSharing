package Test.MessageTest;

import Peer.PeerClient;

/**
 * Created by Trevor on 11/17/2017.
 */
public class MessageTestClient3 {
    public static void main(String args []) {
        PeerClient client1 = new PeerClient(1003);
        client1.run();
    }
}
