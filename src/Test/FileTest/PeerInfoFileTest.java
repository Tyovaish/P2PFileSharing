package Test.FileTest;

import File.PeerInfoFileParser;
import Peer.PeerInfo;

import java.util.ArrayList;

/**
 * Created by Trevor on 11/2/2017.
 */
public class PeerInfoFileTest {
    public static void main(String args[]){
        ArrayList<PeerInfo> peerInfos=PeerInfoFileParser.getPeersToConnect();
        for(int i=0;i<peerInfos.size();i++){
            System.out.println(peerInfos.get(i).getPeerID()+"  "+peerInfos.get(i).getHostName()+" "+peerInfos.get(i).getPortNumber()+" "+peerInfos.get(i).getHasFile());
        }
    }
}
