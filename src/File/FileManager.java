package File;

import Peer.PeerInfo;

import java.io.*;

public class FileManager {

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

    public void bytesToFile(int peerID) {
        System.out.println("Outputting file");

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
}