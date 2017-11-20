package Test.ClientTest;

import Peer.PeerClient;

/**
 * Created by Trevor on 10/24/2017.
 */
public class MessageTestClient1 {
    //First Client
    public static void main(String args []) {
        PeerClient client1 = new PeerClient(1001);
        client1.run();
    }
}
