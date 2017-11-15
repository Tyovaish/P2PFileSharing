package TCPConnection.Neighbor;

import File.CommonFileParser;
import TCPConnection.TCPConnection;

import java.util.ArrayList;

/**
 * Created by Trevor on 11/14/2017.
 */
public class NeighborManager implements Runnable {
    ArrayList<TCPConnection> neighborConnections;
    long unchokingInterval;
    long optimisticallyUnchokingInterval;
    public NeighborManager(){
        unchokingInterval= CommonFileParser.getUnchokingInterval()*1000;
        optimisticallyUnchokingInterval=CommonFileParser.getOptimisticUnchokingInterval()*1000;
        neighborConnections=new ArrayList<TCPConnection>();
    }
    @Override
    public void run() {
        long unchokingStartTime=System.currentTimeMillis();
        long optmisticallyUnchokeTime=System.currentTimeMillis();
        while(true){
            if(System.currentTimeMillis()-unchokingStartTime>unchokingInterval){
                System.out.println("Unchoke");
                if(neighborConnections.size()!=0){
                    System.out.println("Hello");
                }
                unchokingStartTime=System.currentTimeMillis();
            }
            if(System.currentTimeMillis()-optmisticallyUnchokeTime>optimisticallyUnchokingInterval){
                System.out.println("OptimisticallyUnchoke");
                optmisticallyUnchokeTime=System.currentTimeMillis();
            }
        }
    }
    public synchronized void addNeighbor(TCPConnection tcpConnection){

        neighborConnections.add(tcpConnection);
    }
}
