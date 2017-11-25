package File;

import java.io.*;
import java.lang.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;

import Peer.PeerInfo;

public class FileParser {

  private long fileSize = CommonFileParser.getFileSize();
  private long pieceSize = CommonFileParser.getPieceSize();
  private int numberOfPieces=(int)Math.ceil(fileSize / pieceSize);
  private String fileName = CommonFileParser.getFileName().substring(CommonFileParser.getFileName().lastIndexOf("/") + 1);
  private byte byteFile[][] = new byte[numberOfPieces][(int)pieceSize];
  private BitSet piecesInPossesion = new BitSet(numberOfPieces);
  private int peerID;

  public FileParser(int peerID){
    PeerInfo peerInfo = PeerInfoFileParser.getPeerInfo(peerID);
    this.peerID = peerID;
    try {
      if(peerInfo.getHasFile()) {
        for(int i=0; i<numberOfPieces; i++) {
          piecesInPossesion.set(i, true);
        }
      }
    }
    catch (NullPointerException e) {
      System.out.println("Exception in constructor");
    }
  }
  
  public synchronized void setPiece(byte[] bytes, int index) {
    byteFile[index] = bytes;
    piecesInPossesion.set(index,true);
  }

  public byte[] getPiece(int index) {
    return byteFile[index];
  }

  public synchronized BitSet getCurrentFileState() {
    return piecesInPossesion;
  }

  public int getNumberOfPieces(){
    return numberOfPieces;
  }

  public int getNumberOfPiecesInPossession(){
      return piecesInPossesion.cardinality();
  }

  public synchronized ArrayList<Integer> getInterestedPieces(BitSet peerBitfield){
      ArrayList<Integer> interestedPieces=new ArrayList<Integer>();
      for(int i=0;i<numberOfPieces;++i){
        if(piecesInPossesion.get(i)==false && peerBitfield.get(i)==true){
          interestedPieces.add(i);
        }
      }
   
    return interestedPieces;
  }

  public byte[] getBitFieldMessage(){
    byte [] bytesOfPiecesInPossesion=piecesInPossesion.toByteArray();
    byte [] bitfield=new byte[numberOfPieces];
    for(int i=0;i<bytesOfPiecesInPossesion.length;i++){
     bitfield[i]=bytesOfPiecesInPossesion[i];
    }
    return bitfield;
  }

  public byte[][] getFileFromPeer() {
    for(int i = 0; i < piecesInPossesion.length(); i++) {
      if(piecesInPossesion.get(i)) {
        break;
      }
      else if (!piecesInPossesion.get(i) && i == piecesInPossesion.length() - 1) {
        return new byte[numberOfPieces][(int)pieceSize];
      }
    }
    return byteFile;
  }


  public boolean isFinished(){
    return piecesInPossesion.cardinality()==numberOfPieces;
  }
  public boolean hasPiece(int pieceIndex){return piecesInPossesion.get(pieceIndex);}
}
