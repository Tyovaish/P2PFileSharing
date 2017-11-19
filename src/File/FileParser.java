//package File;

import java.io.*;
import java.lang.*;

public class FileParser {

  byte byteFile[][];
  float fileSize;
  float pieceSize;
  boolean receivedPieces[];
  String fileName;

  public FileParser(String filePath, float fileSize, float pieceSize) {
    this.fileSize = fileSize;
    this.pieceSize = pieceSize;

    fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

    byteFile = new byte[(int)Math.ceil(fileSize / pieceSize)][(int)pieceSize];
    receivedPieces = new boolean[(int)Math.ceil(fileSize / pieceSize)];

    System.out.println("Constructed");
    System.out.println("File name is " + fileName);
    System.out.println("byteFile has " + byteFile.length + " columns and " + byteFile[0].length + " rows.");
  }

  public void setPiece(byte[] bytes, int index) {
    byteFile[index] = bytes;
    receivedPieces[index] = true;

    for(int i = 0; i < byteFile[index].length; i++) {
      System.out.println("Setting piece: " + byteFile[index][i]);
    }

    System.out.println("byteFile has " + byteFile.length + " columns and " + byteFile[index].length + " rows.");
  }

  public byte[] getPiece(int index) {
    System.out.println("Getting Piece: " + byteFile[index]);

    return byteFile[index];
  }

  public byte[] getPiecesWeDontHave() {
    System.out.println("Getting pieces we don't have");

    byte pieces[] = new byte[(int)Math.ceil(fileSize / pieceSize)];
    for(int i = 0; i < receivedPieces.length; i++) {
      if(!receivedPieces[i]) {
        pieces[i] = 1;
      }
      else {
        pieces[0] = 0;
      }
    }

    return pieces;
  }

  public byte[] getPiecesWeHave() {
    System.out.println("Getting pieces we do have");

    byte pieces[] = new byte[(int)Math.ceil(fileSize / pieceSize)];
    for(int i = 0; i < receivedPieces.length; i++) {
      if(receivedPieces[i] == true) {
        pieces[i] = 1;
      }
      else {
        pieces[0] = 0;
      }
    }

    return pieces;
  }

  public void bytesToFile() {
    System.out.println("Outputting file");
    File outputFile = new File("OutputFile.txt");
    try {
      FileOutputStream fos = new FileOutputStream(outputFile);
      
      for(int i = 0; i < Math.ceil(fileSize / pieceSize); i++) {
        fos.write(byteFile[i]);
      }

      fos.flush();
      fos.close();
    } catch(IOException e) {
      System.out.println("IO Excepton");
    }
  }
}
