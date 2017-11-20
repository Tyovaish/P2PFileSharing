package Test.ClientTest;

import Peer.PeerClient;

/**
 * Created by Trevor on 11/17/2017.
 */
public class MessageTestClient4 {
    public static void main(String [] args){
        PeerClient client1 = new PeerClient(1004);
        client1.run();
    }
}
