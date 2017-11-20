package TCPConnection.Neighbor;

import TCPConnection.TCPConnection;

import java.util.Comparator;

/**
 * Created by Logan on 11/19/2017.
 */
public class NeighborComparator implements Comparator<TCPConnection> {

    @Override
    public int compare(TCPConnection n1, TCPConnection n2){
        if(n1.getNeighborState().getPiecesRecieved() < n2.getNeighborState().getPiecesRecieved()){
            return -1;
        }
        if(n1.getNeighborState().getPiecesRecieved() > n2.getNeighborState().getPiecesRecieved()){
            return 1;
        }
        return 0;
    }

}
