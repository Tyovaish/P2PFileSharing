//package File;

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

                    CommonFileParser cfp = new CommonFileParser();

                    FileParser fp = new FileParser(filePath, file.length(), 1);

//                    System.out.println("Common file parser piece size: " + (int) cfp.getPieceSize());

                    int byteIndex = 0;
                    for(int i = 0; i < byteFile.length; i += 1) {
                        System.out.println("Getting new segment");
                        byte[] byteSegment = new byte[1];
                        for(int j = 0; j < 1; j++) {
                            System.out.println("Getting new byte");
                            byteSegment[j] = byteFile[i + j];
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
