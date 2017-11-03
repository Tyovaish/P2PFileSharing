package Test.MessageTest;

import Peer.PeerProcess;

/**
 * Created by Trevor on 10/24/2017.
 */
public class MessageTestClient2 {
    public static void main(String args []) {
        PeerProcess client1 = new PeerProcess(1002,8000);
        client1.run();
    }
}
