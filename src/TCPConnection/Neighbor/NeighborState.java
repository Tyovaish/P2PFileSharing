package TCPConnection.Neighbor;

import File.FileParser;
import Peer.PeerInfo;
import TCPConnection.TCPConnection;

import java.util.BitSet;

/**
 * Created by Logan on 11/4/2017.
 */
public class NeighborState {
    PeerInfo neighborInfo;
    boolean hasRecievedHandshake=false;
    boolean hasSentHandshake=false;
    boolean chokingNeighbor = true;
    boolean chokingClient = true;
    boolean interestedInClient=false;
    boolean sentBitfield=false;
    BitSet bitfield;
    int piecesRecieved;
    public NeighborState(PeerInfo neighborInfo){
        this.neighborInfo=neighborInfo;
    }
    public boolean isChokingNeighbor() { return chokingNeighbor; }
    public boolean isChokingClient() { return chokingClient; }
    public boolean isInterestedInClient(){return interestedInClient;}
    public boolean hasRecievedHandshake(){return hasRecievedHandshake;}
    public boolean hasSentHandshake(){return hasSentHandshake;}
    public boolean hasSentBitfield(){return sentBitfield;}
    public int getPiecesRecieved() {return piecesRecieved;}
    public void chokeNeighbor() { chokingNeighbor = true; }
    public void unchokeNeighbor() { chokingNeighbor = false; }
    public void chokeClient() { chokingClient= true; }
    public void unchokeClient() { chokingClient = false; }
    public void setNotInterestedInNeighbor() { interestedInClient = false; }
    public void setInterestedInClient(){}{interestedInClient=true;}
    public void setNotInterestedInClient(){interestedInClient=false;}
    public void updateBitField(int pieceIndex) { bitfield.set(pieceIndex,true); }
    public void updateBitfield(byte [] bitfield){this.bitfield=BitSet.valueOf(bitfield);}
    public void recievedHandshake(){hasRecievedHandshake=true;}
    public void sentHandshake(){hasSentHandshake=true;}
    public void sentBitfield(){sentBitfield=true;}
    public boolean checkIfFinished(){return bitfield.cardinality()==bitfield.size();}
    public int getRandomPiece(FileParser file){
        return 0;
    }
    public boolean isInterestedInNeighbor(FileParser file){
        return false;
    }
    public int getNeighborPeerID(){return neighborInfo.getPeerID();}
    //public int getNumberOfPiecesInPossesion(){return }



}
