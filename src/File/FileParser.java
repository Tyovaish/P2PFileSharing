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

  long fileSize = CommonFileParser.getFileSize();
  long pieceSize = CommonFileParser.getPieceSize();
  int numberOfPieces=(int)Math.ceil(fileSize / pieceSize);
  String fileName = CommonFileParser.getFileName().substring(CommonFileParser.getFileName().lastIndexOf("/") + 1);
  String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
  byte byteFile[][] = new byte[numberOfPieces][(int)pieceSize];
  BitSet piecesInPossesion = new BitSet(numberOfPieces);

  public FileParser(int peerID){
    PeerInfo peerInfo=PeerInfoFileParser.getPeerInfo(peerID);
    if(peerInfo.getHasFile()){
      for(int i=0;i<numberOfPieces;i++){
        piecesInPossesion.set(i,true);
      }
    }
  }
  public synchronized void setPiece(byte[] bytes, int index) {
    byteFile[index] = bytes;
    piecesInPossesion.set(index,true);

    System.out.println("byteFile has " + byteFile.length + " columns and " + byteFile[index].length + " rows.");
  }

  public byte[] getPiece(int index) {
    System.out.println("Getting Piece: " + byteFile[index]);
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

  public void bytesToFile() {
    System.out.println("Outputting file");

    PeerInfo pid = new PeerInfo();

    File directory = new File("/home/keanu/Documents/College/NetworkFundamentals/Project/peer_" + pid.getPeerID());

    if(!directory.exists()) {
      directory.mkdir();
    }

    File outputFile = new File(directory + "/" + fileName);

    System.out.println("File name is " + fileName);

    try {
      FileOutputStream fos = new FileOutputStream(outputFile);
      
      for(int i = 0; i < Math.ceil(fileSize / pieceSize); i++) {
        fos.write(byteFile[i]);
      }

      fos.flush();
      fos.close();
    }
    catch(IOException e) {
      System.out.println("IO Exception");
    }
  }
  public boolean isFinished(){
    return piecesInPossesion.cardinality()==numberOfPieces;
  }
  public boolean hasPiece(int pieceIndex){return piecesInPossesion.get(pieceIndex);}
}
