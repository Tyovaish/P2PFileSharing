package Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;


/**
 * Created by Trevor on 10/18/2017.
 */
public class InformationLogger {
    Date date;
    int peerIDOfProccess;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    public InformationLogger(int peerIDOfProccess){
        this.peerIDOfProccess=peerIDOfProccess;
        date=new Date();
        try {
            fileWriter=new FileWriter("log_peer_"+peerIDOfProccess+".log",true);
            bufferedWriter=new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logTCPConnectionSent(int peerIDOfReciever){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append("makes a connection to Peer ");
        output.append(peerIDOfReciever);
        addToLogFile(output.toString());
    }
    public void logTCPConnectionRecieved(int peerIDOfSender){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append("is connected from Peer ");
        output.append(peerIDOfSender);
        addToLogFile(output.toString());

    }
    public void logChangePrefferedNeighbors(ArrayList<Integer> prefferedNeighborsList){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append("has the preffered  ");
        for(int i=0;i<prefferedNeighborsList.size();i++){
            output.append(prefferedNeighborsList.get(i));
        }
        addToLogFile(output.toString());
    }
    public void logOptimisticallyUnchokeNeighbor(int peerIDOfUnchokeNeighbor){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append(" has optimistically unchoked neighbor ");
        output.append(peerIDOfUnchokeNeighbor);
        addToLogFile(output.toString());
    }
    public void logRecievedOptimisticallyUnchoke(int peerIDOfRecievedUnchokingNeighbor){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append(" is unchoked by Peer ");
        output.append(peerIDOfRecievedUnchokingNeighbor);
        addToLogFile(output.toString());
    }
    public void logChoke(int peerIDOfChokedNeighbor){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append(" is choked by Peer ");
        output.append(peerIDOfChokedNeighbor);
        addToLogFile(output.toString());
    }
    public void logHaveMessage(int peerIDOfHaveSender, int pieceIndex){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append(" received the 'have' message from ");
        output.append(peerIDOfHaveSender);
        output.append(" for the piece ");
        output.append(pieceIndex);
        addToLogFile(output.toString());
    }
    public void logInterestedMessage(int peerIDOfInterested){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append(" received the 'interested' message from ");
        output.append(peerIDOfInterested);
        addToLogFile(output.toString());
    }
    public void logNotInterestedMessage(int peerIDOfNotInterested){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append(" received the 'not interested' message from ");
        output.append(peerIDOfNotInterested);
        addToLogFile(output.toString());
    }
    public void logDownloading(int peerIDOfPieceDownloadedFrom, int pieceIndex,int numberOfPiecesInPossesion){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append(" has downloaded the piece ");
        output.append(pieceIndex);
        output.append(" from ");
        output.append(peerIDOfPieceDownloadedFrom);
        output.append(".\nNow the number of pieces it has is ");
        output.append(numberOfPiecesInPossesion);
        addToLogFile(output.toString());
    }
    public void logCompletition(){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfProccess);
        output.append(" has downloaded the complete file.");
        addToLogFile(output.toString());
    }
    public void addToLogFile(String log){
        try {
            bufferedWriter.write(log);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeLog(){
        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
