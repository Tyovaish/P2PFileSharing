package Test.MessageTest;

import Peer.PeerProcess;

/**
 * Created by Trevor on 10/24/2017.
 */
public class MessageTestClient {
    //First Client
    public static void main(String args []) {
        PeerProcess client1 = new PeerProcess(1001,7000);
        client1.run();
    }
}
