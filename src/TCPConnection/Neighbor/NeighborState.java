package TCPConnection.Neighbor;

import File.FileParser;
import Peer.PeerInfo;
import TCPConnection.TCPConnection;

import java.io.File;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 * Created by Logan on 11/4/2017.
 */
public class NeighborState {
    PeerInfo neighborInfo;
    boolean hasRecievedHandshake=false;
    boolean hasSentHandshake=false;
    boolean chokingNeighbor = false; //DEBUGGING
    boolean chokingClient = false; //DEBUGGING
    boolean interestedInClient=false;
    boolean sentBitfield=false;
    boolean hasRecievedBitfield=false;
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
    public void recievedBitfield(byte [] bitfield){hasRecievedBitfield=true;this.bitfield=BitSet.valueOf(bitfield);}
    public void recievedHandshake(){hasRecievedHandshake=true;}
    public void sentHandshake(){hasSentHandshake=true;}
    public void sentBitfield(){sentBitfield=true;}
    public int getRandomPiece(FileParser file){
       ArrayList<Integer> interestedFilePieces = file.getInterestedPieces(bitfield);
       int pieceIndexInterested=new Random().nextInt(interestedFilePieces.size());
       return  pieceIndexInterested;
    }
    public boolean checkIfFinished(FileParser file){
        if(!hasRecievedBitfield){
            return false;
        }
        return bitfield.cardinality()==file.getNumberOfPieces();
    }
    public boolean isInterestedInNeighbor(FileParser file){
        if(!hasRecievedBitfield){
            return false;
        }
        BitSet numberOfPiecesInClientPossesion=file.getCurrentFileState();
        for(int i=0;i<file.getNumberOfPieces();++i){
            if(bitfield.get(i)==true && numberOfPiecesInClientPossesion.get(i)==false){
                return true;
            }
        }
        return false;
    }
    public int getNeighborPeerID(){return neighborInfo.getPeerID();}



}
