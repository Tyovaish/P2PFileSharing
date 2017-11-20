package TCPConnection.Neighbor;

import TCPConnection.TCPConnection;

import java.util.BitSet;

/**
 * Created by Logan on 11/4/2017.
 */
public class NeighborState {
    boolean hasRecievedHandshake=false;
    boolean hasSentHandshake=false;
    boolean chokingNeighbor = true;
    boolean chokingClient = true;
    boolean interestedInClient=false;
    boolean sentBitfield=false;
    BitSet bitfield;
    int piecesRecieved;


    public boolean isChokingNeighbor() { return chokingNeighbor; }
    public boolean isChokingClient() { return chokingClient; }
    /*public boolean isInterestedInNeighbor(File clientFile){
        //BitSet piecesNeighborHas=currentNeighborState.getBitfield();
        //BitSet piecesClientHas=clientFile.getBitfield();
        //for(int i=0;i<piecesNeighborHas.size();i++){
        //  if(piecesNeighborHas.get(i)==true && piecesClientHas.get(i)==false){
        //
        //  return true;
        //}
        //return false;
        return false;
    }*/
    public boolean isInterestedInClient(){return interestedInClient;}
    public boolean hasRecievedHandshake(){return hasRecievedHandshake;}
    public boolean hasSentHandshake(){return hasSentHandshake;}
    public boolean hasSentBitfield(){return sentBitfield;}
    public void chokeNeighbor() { chokingNeighbor = true;}
    public void unchokeNeighbor() { chokingNeighbor = false;}
    public void chokeClient() { chokingClient= true; }
    public void unchokeClient() { chokingClient = false; }
    public void setInterestedInClient(){}{interestedInClient=true;}
    public void setNotInterestedInClient(){interestedInClient=false;}
    public void updateBitField(int pieceIndex) {++piecesRecieved;bitfield.set(pieceIndex,true); }
    public void updateBitfield(byte [] bitfield){this.bitfield=BitSet.valueOf(bitfield);}
    public BitSet getBitfield(){return bitfield;}
    public void recievedHandshake(){hasRecievedHandshake=true;}
    public void sentHandshake(){hasSentHandshake=true;}
    public void sentBitfield(){sentBitfield=true;}
    public int getPiecesRecieved(){return piecesRecieved;}
    public int getRandomPiece(){
        //BitSet piecesNeighborHas=currentNeighborState.getBitfield();
        //BitSet piecesClientHas=tcpConnection.getFile().getBitfield();
        //for(int i=0;i<piecesNeighborHas.size();i++){
        //  if(piecesNeighborHas.get(i)==true && piecesClientHas.get(i)==false{
        //
        //  }
        //}
        return 0;
    }
    public void setPieceIndex(int pieceIndex, byte [] payLoad){

    }
    public boolean checkIfFinished(){return bitfield.cardinality()==bitfield.size();}



}
