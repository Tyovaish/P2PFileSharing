package File;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Trevor on 11/1/2017.
 */
public class CommonFileParser {
    static final String filePath="C:\\Users\\Logan\\Documents\\P2PFileSharing\\src\\Test\\FileTest\\Common.cfg";

    private static String getValueOfProperty(String indentifier) {
        String line=null;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                String [] commonProperties=line.split(" ");
                if(commonProperties[0].compareTo(indentifier)==0){
                    return commonProperties[1];
                }
            }
            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int getNumberOfPreferredNeighbors(){return Integer.parseInt(getValueOfProperty("NumberOfPreferredNeighbors"));}

    public static int getOptimisticUnchokingInterval() {return Integer.parseInt(getValueOfProperty("OptimisticUnchokingInterval"));}

    public static int getUnchokingInterval() {return Integer.parseInt(getValueOfProperty("UnchokingInterval"));    }

    public static long getFileSize() {
        return Long.parseLong(getValueOfProperty("FileSize"));
    }

    public static long getPieceSize() {
        return Long.parseLong(getValueOfProperty("PieceSize"));
    }

    public static String getFileName() {
        return getValueOfProperty("FileName");
    }

}
