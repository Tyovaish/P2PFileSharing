package Test.FileTest;

import java.io.*;
import java.lang.*;
import java.nio.file.*;
import File.CommonFileParser;
import File.FileParser;



public class FileTest {

  public static void main(String args[]) {

    String filePath = CommonFileParser.getFileName();

    System.out.println("File Name: " + CommonFileParser.getFileName());

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

        try {
          Path pth = Paths.get(filePath);
          byte[] byteFile = Files.readAllBytes(pth);

          FileParser fp = new FileParser();

          int byteIndex = 0;
          for(int i = 0; i < byteFile.length; i += CommonFileParser.getPieceSize()) {
            System.out.println("Getting new segment");
            byte[] byteSegment = new byte[(int) CommonFileParser.getPieceSize()];
            for(int j = 0; j < (int) CommonFileParser.getPieceSize(); j++) {
              if(i + j < byteFile.length) {
                System.out.println("Getting new byte");
                byteSegment[j] = byteFile[i + j];
              }
            }

            fp.setPiece(byteSegment, byteIndex);

            byteIndex++;
          }

          System.out.println(fp.getPiece(0));

          fp.bytesToFile();
        }
        catch (IOException i) {
          System.out.println("IO Exception with byte array");
        }
      }
      catch (FileNotFoundException e) {
        System.out.println("File not found exception");
      }

    }

    else {
      System.out.println("File does not exist");
    }

  }

}
