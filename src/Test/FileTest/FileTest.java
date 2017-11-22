package Test.FileTest;

import java.io.*;
import java.lang.*;
import java.nio.file.*;
import File.CommonFileParser;
import File.FileParser;



public class FileTest {

    public static void main(String args[]) {
        FileParser fp = new FileParser();

        File file = fp.readFile();

        try {
            Path pth = Paths.get(CommonFileParser.getFileName());
            byte[] byteFile = Files.readAllBytes(pth);

            int byteIndex = 0;

            for(int i = 0; i < byteFile.length; i += CommonFileParser.getPieceSize()) {
                byte[] byteSegment = new byte[(int) CommonFileParser.getPieceSize()];
                for(int j = 0; j < (int) CommonFileParser.getPieceSize(); j++) {
                    if(i + j < byteFile.length) {
                        byteSegment[j] = byteFile[i + j];
                    }
                }
                fp.setPiece(byteSegment, byteIndex);
                byteIndex++;
            }
        }
        catch (IOException i) {
            System.out.println("IO Exception with byte array");
        }

        if(fp.isFinished())
            fp.bytesToFile();
        else
            System.out.println("Not finished");

    }


}
