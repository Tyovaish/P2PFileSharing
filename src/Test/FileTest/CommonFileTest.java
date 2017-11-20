package Test.FileTest;

import File.CommonFileParser;

/**
 * Created by Trevor on 11/2/2017.
 */
public class CommonFileTest {
    public static void main(String args[]){
        System.out.println("NumberOfPrefferedNeighbors "+CommonFileParser.getNumberOfPreferredNeighbors());
        System.out.println("UnchokingInterval "+CommonFileParser.getUnchokingInterval());
        System.out.println("OptimisticUnchokingInterval "+CommonFileParser.getOptimisticUnchokingInterval());
        System.out.println("FileName "+CommonFileParser.getFileName());
        System.out.println("FileSize "+CommonFileParser.getFileSize());
        System.out.println("PieceSize "+CommonFileParser.getPieceSize());
    }
}
