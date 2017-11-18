package File;

import java.io.*;
import java.lang.*;
import java.nio.file.*;


public class FileTest {

    public static void main(String args[]) {

        String filePath = "bitch.txt";

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

                    CommonFileParser cfp = new File.CommonFileParser(filePath);

                    FileParser fp = new FileParser(filePath, byteFile.length, byteFile.length);

                    System.out.println("Common file parser piece size: " + cfp.getPieceSize());

                    for(int i = 0; i < byteFile.length; i++) {
                        byte[] byteSegment = new byte[cfp.getPieceSize()];
                        fp.setPiece(b, byteIndex);
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
