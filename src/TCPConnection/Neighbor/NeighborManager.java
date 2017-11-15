package TCPConnection.Neighbor;

import TCPConnection.TCPConnection;

import java.util.ArrayList;

/**
 * Created by Trevor on 11/14/2017.
 */
public class NeighborManager implements Runnable {
    ArrayList<TCPConnection> neighborConnections;
    public NeighborManager(){
        neighborConnections=new ArrayList<TCPConnection>();
    }
    @Override
    public void run() {
        long startTime=System.currentTimeMillis();
        while(true){
            if(System.currentTimeMillis()-startTime>1000){
                System.out.println("Been a Seconds");
                startTime=System.currentTimeMillis();
            }
        }
    }

}
