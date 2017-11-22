package Test.LoggerTest;
import Logger.InformationLogger;
/**
 * Created by Trevor on 10/24/2017.
 */
public class LoggerTest {
    public static void main(String args[]){
        InformationLogger logger=new InformationLogger(1001);
        logger.logChoke(1002);
        logger.logOptimisticallyUnchokeNeighbor(1301);
        logger.logNotInterestedMessage(131);
        logger.logInterestedMessage(1313);
        logger.logCompletition();
        logger.closeLog();
    }
}
