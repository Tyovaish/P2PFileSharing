package Test.MessageTest;

import Peer.PeerClient;

/**
 * Created by Trevor on 10/24/2017.
 */
public class MessageTestClient2 {
    public static void main(String args []) {
        PeerClient client1 = new PeerClient(1002);
        client1.run();
    }
}
