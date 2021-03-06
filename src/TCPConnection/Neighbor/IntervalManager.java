package TCPConnection.Neighbor;

import File.CommonFileParser;
import Peer.PeerClient;
import TCPConnection.TCPConnection;

import java.util.ArrayList;

/**
 * Created by Trevor on 11/14/2017.
 */
public class IntervalManager implements Runnable {
    PeerClient peerClient;
    long optimisticallyUnchokingInterval;
    long unchokingInterval;
    public IntervalManager(PeerClient peerClient){
        this.peerClient=peerClient;
        unchokingInterval= CommonFileParser.getUnchokingInterval()*1000;
        optimisticallyUnchokingInterval=CommonFileParser.getOptimisticUnchokingInterval()*1000;
    }
    @Override
    public void run() {
        long unchokingStartTime=System.currentTimeMillis();
        long optmisticallyUnchokeTime=System.currentTimeMillis();
        while(!peerClient.allFinished()){
            if(System.currentTimeMillis()-unchokingStartTime>unchokingInterval){
                unchokingStartTime=System.currentTimeMillis();
                peerClient.unchokeBestNeighbors();
            }
            if(System.currentTimeMillis()-optmisticallyUnchokeTime>optimisticallyUnchokingInterval){
                optmisticallyUnchokeTime=System.currentTimeMillis();
                peerClient.optimisticallyUnchoke();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            peerClient.getInformationLogger().closeLog();
            peerClient.getFile().bytesToFile();
            System.out.println("Finished");
    }
}
