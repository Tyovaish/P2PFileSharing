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
  public static int numberOfPieces=(int)Math.ceil(fileSize / pieceSize);
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
          this.piecesInPossesion.set(i, true);
        }
        /*for(int i=0;i<fileSize;i++){
          this.byteFile[(int) (i/pieceSize)][(int) (i%pieceSize)]= (byte) i;
        }*/
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
    return bytesOfPiecesInPossesion;
  }

  public File readFile() {
    String filePath = CommonFileParser.getFileName();

    File file = new File(filePath);

    if (file.isFile()) {
      System.out.println("File exists");

      try {
        FileInputStream fis = new FileInputStream(file);

        try {
          fis.close();
        }
        catch (IOException e) {
          System.out.println("IO Exception when closing file");
        }
      }
      catch (FileNotFoundException e) {
        System.out.println("File not found exception");
      }
    }
    else {
      System.out.println("File does not exist");

    }
    return file;
  }

  public void bytesToFile() {
    System.out.println("Outputting file");

    PeerInfo pid = new PeerInfo();

    File directory = new File("/home/keanu/Documents/College/NetworkFundamentals/Project/peer_" + peerID);

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
  public boolean isFinished(){return piecesInPossesion.cardinality()==numberOfPieces;}
  public boolean hasPiece(int pieceIndex){return piecesInPossesion.get(pieceIndex);}
}
