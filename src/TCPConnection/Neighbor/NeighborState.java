package TCPConnection.Neighbor;

/**
 * Created by Logan on 11/4/2017.
 */
public class NeighborState {
    boolean hasRecievedHandshake=false;
    boolean hasSentHandshake=false;
    boolean chokingNeighbor = true;
    boolean chokingClient = true;
    boolean interestedInNeighbor = false;
    boolean interestedInClient=false;
    byte[] bitfield = {};

    public boolean isChokingNeighbor() { return chokingNeighbor; }
    public boolean isChokingClient() { return chokingClient; }
    public boolean isInterestedInNeighbor() { return interestedInNeighbor; }
    public boolean isInterestedInClient(){return interestedInClient;}
    public boolean hasRecievedHandshake(){return hasRecievedHandshake;}
    public boolean hasSentHandshake(){return hasSentHandshake;}
    public void chokeNeighbor() { chokingNeighbor = true; }
    public void unchokeNeighbor() { chokingNeighbor = false; }
    public void chokeClient() { chokingClient= true; }
    public void unchokeClient() { chokingClient = false; }
    public void setInterestedInNeighbor() {interestedInNeighbor  = true; }
    public void setNotInterestedInNeighbor() { interestedInClient = false; }
    public void setInterestedInClient(){}{interestedInClient=true;}
    public void setNotInterestedInClient(){interestedInClient=false;}
    public void updatePieces(byte[] newBitfield) { bitfield = newBitfield; }
    public void recievedHandshake(){hasRecievedHandshake=true;}
    public void sentHandshake(){hasSentHandshake=true;}



}
