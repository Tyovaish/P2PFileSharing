package TCPConnection.Neighbor;

/**
 * Created by Logan on 11/4/2017.
 */
public class NeighborState {
    boolean choked = true;
    boolean choking = true;
    boolean interested = false;
    byte[] bitfield = {};

    public boolean isChoked() { return choked; }
    public boolean isChoking() { return choking; }
    public boolean isInterested() { return interested; }
    public void chokeNeighbor() { choked = true; }
    public void chokeClient() { choking = true; }
    public void unchokeNeighbor() { choked = false; }
    public void unchokeClient() { choking = false; }
    public void setInterested() { interested = true; }
    public void setNotInterested() { interested = false; }
    public void updatePieces(byte[] newBitfield) { bitfield = newBitfield; }

}
