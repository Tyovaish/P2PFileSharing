package File;

import java.io.*;
import java.lang.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import Peer.PeerInfo;

public class FileParser {

  private long fileSize = CommonFileParser.getFileSize();
  private long pieceSize = CommonFileParser.getPieceSize();;
  private String fileName = CommonFileParser.getFileName().substring(CommonFileParser.getFileName().lastIndexOf("/") + 1);
  private String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

  private byte byteFile[][] = new byte[(int)Math.ceil(fileSize / pieceSize) + 1][(int)pieceSize];
  BitSet bs = new BitSet((int)Math.ceil(fileSize / pieceSize));
  boolean[] received = new boolean[(int)Math.ceil(fileSize / pieceSize)];


  public void setPiece(byte[] bytes, int index) {
    byteFile[index] = bytes;
    received[index] = true;
    bs.set(index);
  }


  public byte[] getPiece(int index) {
    System.out.println("Getting Piece: " + byteFile[index]);
    return byteFile[index];
  }


  public int getPiecesWeHave() {
    return bs.cardinality();
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

    for(int i = 0; i < bs.length(); i++) {
      if(bs.get(i) == true) {
        System.out.print(1);
      }
      else {
        System.out.print(0);
      }
    }

    System.out.println("\n");

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
    if(bs.cardinality() == (int)Math.ceil(fileSize / pieceSize))
      return true;
    else
      return false;
  }
}
