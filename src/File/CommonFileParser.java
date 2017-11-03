package File;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Trevor on 11/1/2017.
 */
public class CommonFileParser {
    int numberOfPreferredNeighbors;
    int unchokingInterval;
    int optimisticUnchokingInterval;
    String fileName;
    long fileSize;
    long pieceSize;

    public CommonFileParser(String commonFileName){
                String line=null;
                try {
                    FileReader fileReader = new FileReader(commonFileName);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
        while((line = bufferedReader.readLine()) != null) {
            String [] commonProperties=line.split(" ");
            switch(commonProperties[0]){
                case "NumberOfPreferredNeighbors":
                    numberOfPreferredNeighbors=Integer.parseInt(commonProperties[1]);
                    break;
                case "UnchokingInterval":
                    unchokingInterval=Integer.parseInt(commonProperties[1]);
                    break;
                case "OptimisticUnchokingInterval":
                    optimisticUnchokingInterval=Integer.parseInt(commonProperties[1]);
                    break;
                case "FileName":
                   fileName=commonProperties[1];
                    break;
                case "FileSize":
                    fileSize=Integer.parseInt(commonProperties[1]);
                    break;
                case "PieceSize":
                    pieceSize=Integer.parseInt(commonProperties[1]);
                    break;
            }
        }

        // Always close files.
        bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfPreferredNeighbors() {
        return numberOfPreferredNeighbors;
    }

    public int getOptimisticUnchokingInterval() {
        return optimisticUnchokingInterval;
    }

    public int getUnchokingInterval() {
        return unchokingInterval;
    }

    public long getFileSize() {
        return fileSize;
    }

    public long getPieceSize() {
        return pieceSize;
    }

    public String getFileName() {
        return fileName;
    }

}
