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

    byteFile = new byte[Math.ceil(fileSize / pieceSize)][pieceSize];
    receivedPieces = new boolean[Math.ceil(fileSize / pieceSize)];

    // File file = new File(filePath);
    // FileInputStream fis = new FileInputStream(file);
    // fis.read(byteFile);
    // fis.close();
  }

  public void setPiece(byte[] bytes, int index) {
    System.out.println("Setting piece");

    byteFile[index] = bytes;
    receivedPieces[index] = true;
  }

  public byte getPiece(int index) {
    System.out.println("Getting Piece: " + byteFile[index]);

    return byteFile[index];
  }

  public byte[] getPiecesWeDontHave() {
    System.out.println("Getting pieces we don't have");

    byte pieces[] = new byte[Math.ceil(fileSize / pieceSize)];
    for(int i = 0; i < receivedPieces.length; i++) {
      if(receivedPieces[i] == false) {
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

    byte pieces[] = new byte[Math.ceil(fileSize / pieceSize)];
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
    File outputFile = new File(fileName);
    FileOutputStream fos = new FileOutputStream(outputFile);
    for(int i = 0; i < Math.ceil(fileSize / pieceSize); i++) {
      fos.write(byteFile[i]);
    }
    fos.flush();
    fos.close();
  }
}
