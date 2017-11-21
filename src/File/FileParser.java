package File;

import java.io.*;
import java.lang.*;
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

  public void setPiece(byte[] bytes, int index) {
    byteFile[index] = bytes;
    piecesInPossesion.set(index,true);

    for(int i = 0; i < byteFile[index].length; i++) {
      System.out.println("Setting piece: " + byteFile[index][i]);
    }

    System.out.println("byteFile has " + byteFile.length + " columns and " + byteFile[index].length + " rows.");
  }

  public byte[] getPiece(int index) {
    System.out.println("Getting Piece: " + byteFile[index]);

    return byteFile[index];
  }

  public BitSet getCurrentFileState() {
    return piecesInPossesion;
  }
  public int getNumberOfPieces(){
    return numberOfPieces;
  }
  public int getNumberOfPiecesInPossession(){
    int pieceCount=0;
    for(int i=0;i<numberOfPieces;i++){
      if (piecesInPossesion.get(i)==true){
          ++pieceCount;
      }
    }
      return pieceCount;
  }
  public ArrayList<Integer> getInterestedPieces(BitSet peerBitfield){
      ArrayList<Integer> interestedPieces=new ArrayList<Integer>();
      for(int i=0;i<numberOfPieces;++i){
        if(piecesInPossesion.get(i)==false && peerBitfield.get(i)==true){
          interestedPieces.add(i);
        }
      }
      return interestedPieces;
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
}
