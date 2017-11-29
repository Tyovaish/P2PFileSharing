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

  public static long fileSize = CommonFileParser.getFileSize();
  public  static long pieceSize = CommonFileParser.getPieceSize();
  public static int numberOfPieces= (int) Math.ceil(fileSize /(float)(pieceSize));
  private String fileName = CommonFileParser.getFileName().substring(CommonFileParser.getFileName().lastIndexOf("/") + 1);
  private byte byteFile[][] = new byte[numberOfPieces][(int) pieceSize];
  private BitSet piecesInPossesion = new BitSet(numberOfPieces);
  private PeerInfo peerInfo;

  public FileParser(int peerID){
    this.peerInfo = PeerInfoFileParser.getPeerInfo(peerID);
    try {
      if(peerInfo.getHasFile()) {
        for(int i=0; i<numberOfPieces; i++) {
          this.piecesInPossesion.set(i, true);
        }
        readFile();
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
  public boolean isFinished(){return piecesInPossesion.cardinality()==numberOfPieces;}

  public boolean hasPiece(int pieceIndex){return piecesInPossesion.get(pieceIndex);}
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
    return bytesOfPiecesInPossesion;
  }

  public void bytesToFile() {
    if(peerInfo.getHasFile()){
      return;
    }
    System.out.println("Outputting file");

    File directory = new File("./peer_" + peerInfo.getPeerID());

    if(!directory.exists()) {
      directory.mkdir();
    }
    File outputFile = new File(directory + "/" + fileName);

    System.out.println("File name is " + fileName);

    try {
      FileOutputStream fos = new FileOutputStream(outputFile);
      for(int i = 0; i < numberOfPieces; i++) {
        fos.write(byteFile[i]);
      }
      fos.flush();
      fos.close();
    }
    catch(IOException e) {
      System.out.println("IO Exception");
    }
  }
  public void readFile() {
    String filePath ="peer_"+peerInfo.getPeerID()+"/"+CommonFileParser.getFileName();
    File file = new File(filePath);
    System.out.println(file.length());
    if (file.isFile()) {
      try {
        InputStream is=new FileInputStream(filePath);
        byte [] fileInBytes=new byte[(int) fileSize];
        is.read(fileInBytes);
        for(int i=0;i<fileInBytes.length;i++){
          byteFile[(int) (i/pieceSize)][(i%(int)pieceSize)]=fileInBytes[i];
        }

      }
      catch (FileNotFoundException e) {
        System.out.println("File not found exception");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    else {
      System.out.println("File does not exist");

    }
  }
  public void printFileInBytes(){
    for(int i=0;i<numberOfPieces;i++){
      for(int j=0;j<pieceSize;j++){
        System.out.print(byteFile[i][j]+" ");
      }
      System.out.println();
    }
  }
}
