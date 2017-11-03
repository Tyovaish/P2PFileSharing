package Test.FileTest;

import File.CommonFileParser;

/**
 * Created by Trevor on 11/2/2017.
 */
public class CommonFileTest {
    public static void main(String args[]){
        CommonFileParser commonFileParser=new CommonFileParser("C:\\Users\\Trevor\\IdeaProjects\\P2PFileSharing\\src\\Test\\FileTest\\Common.cfg");
        System.out.println("NumberOfPrefferedNeighbors "+commonFileParser.getNumberOfPreferredNeighbors());
        System.out.println("UnchokingInterval "+commonFileParser.getUnchokingInterval());
        System.out.println("OptimisticUnchokingInterval "+commonFileParser.getOptimisticUnchokingInterval());
        System.out.println("FileName "+commonFileParser.getFileName());
        System.out.println("FileSize "+commonFileParser.getFileSize());
        System.out.println("PieceSize "+commonFileParser.getPieceSize());
    }
}
