package TCPConnection.Neighbor;
import File.CommonFileParser;
import File.FileParser;
import Peer.PeerInfo;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 * Created by Logan on 11/4/2017.
 */
public class NeighborState {
    PeerInfo neighborInfo;
    boolean chokingNeighbor = true; //DEBUGGING
    boolean chokingClient = true; //DEBUGGING
    boolean interestedInClient=false;
    boolean interestedInNeighbor=false;
    boolean hasSentNotInterested=false;
    boolean hasSentInterested=false;

    BitSet bitfield=new BitSet(FileParser.numberOfPieces);
    int piecesRecieved;

    public NeighborState(PeerInfo neighborInfo){
        this.neighborInfo=neighborInfo;
    }

    public boolean isChokingNeighbor() {
        return chokingNeighbor;
    }
    public boolean isChokingClient() {
        return chokingClient;
    }
    public boolean isInterestedInClient(){
        return interestedInClient;
    }


    public int getPiecesRecieved() {
        return piecesRecieved;
    }

    public void chokeNeighbor() {
        chokingNeighbor = true;
    }

    public void unchokeNeighbor() {
        piecesRecieved=0;
        chokingNeighbor = false;
    }

    public void chokeClient() {
        chokingClient= true;
    }

    public void unchokeClient() {
        chokingClient = false;
    }

    public void setInterestedInClient(){}{
        interestedInClient=true;
    }

    public void setNotInterestedInClient(){
        interestedInClient=false;
    }

    public void setInterestedInNeighbor(){}{
        interestedInNeighbor=true;
        hasSentInterested=true;
        hasSentNotInterested=false;
    }
    public void setNotInterestedInNeighbor(){
        interestedInNeighbor=false;
        hasSentInterested=false;
        hasSentNotInterested=true;
    }

    public synchronized void updateBitField(int pieceIndex) {
        piecesRecieved++;
        bitfield.set(pieceIndex,true);
    }

    public void recievedBitfield(byte [] bitfield){
        this.bitfield=BitSet.valueOf(bitfield);
    }

    public boolean hasSentNotInterested(){

        return hasSentNotInterested;
    }
    public boolean hasSentInterested(){

        return hasSentInterested;
    }

    public int getRandomPiece(FileParser file){
       ArrayList<Integer> interestedFilePieces = file.getInterestedPieces(bitfield);
       int pieceIndexInterested=new Random().nextInt(interestedFilePieces.size());
       return  interestedFilePieces.get(pieceIndexInterested);
    }

    public boolean checkIfFinished(){
        return bitfield.cardinality()==FileParser.numberOfPieces;
    }

    public boolean checkIfInterested(FileParser file){
        BitSet numberOfPiecesInClientPossesion=file.getCurrentFileState();
        for(int i=0;i<file.getNumberOfPieces();++i){
            if(bitfield.get(i)==true && numberOfPiecesInClientPossesion.get(i)==false){
                return true;
            }
        }
        return false;
    }
    public boolean knowHasPieceAlready(int pieceIndex){
        if(bitfield==null){
            return false;
        }
        return bitfield.get(pieceIndex);
    }
    public int getNeighborPeerID(){
        return neighborInfo.getPeerID();
    }


    public void resetInterested() {
        hasSentInterested=false;
        hasSentNotInterested=false;
    }
}
