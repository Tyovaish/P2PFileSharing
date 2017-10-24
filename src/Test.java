/**
 * Created by Trevor on 10/24/2017.
 */
public class Test {
    public static void main(String args[]){
        InformationLogger logger=new InformationLogger(1001);
        logger.logChoke(1002);
        logger.logCompletition();
        logger.closeLog();
    }
}
