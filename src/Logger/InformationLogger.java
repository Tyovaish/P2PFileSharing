package Logger;

import Peer.PeerInfo;
import TCPConnection.TCPConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;


/**
 * Created by Trevor on 10/18/2017.
 */
public class InformationLogger {
    Date date;
    int peerIDOfClient;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    boolean completedFile=false;
    public InformationLogger(int peerIDOfClient){
        this.peerIDOfClient=peerIDOfClient;
        date=new Date();
        try {
            fileWriter=new FileWriter("log_peer_"+peerIDOfClient+".log",true);
            bufferedWriter=new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void logTCPConnectionSent(int peerIDOfReciever){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfClient);
        output.append(" makes a connection to Peer ");
        output.append(peerIDOfReciever);
        addToLogFile(output.toString());
    }
    public synchronized void logTCPConnectionRecieved(int peerIDOfSender){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfClient);
        output.append(" is connected from Peer ");
        output.append(peerIDOfSender);
        addToLogFile(output.toString());

    }
    public synchronized void logChangePrefferedNeighbors(ArrayList<TCPConnection> prefferedNeighborsList){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfClient);
        output.append(" has the preferred  ");
        for(int i=0;i<prefferedNeighborsList.size();i++){
            output.append(prefferedNeighborsList.get(i).getNeighborState().getNeighborPeerID() + " ");
        }
        addToLogFile(output.toString());
    }
    public synchronized void logOptimisticallyUnchokeNeighbor(int peerIDOfUnchokeNeighbor){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfClient);
        output.append(" has optimistically unchoked neighbor ");
        output.append(peerIDOfUnchokeNeighbor);
        addToLogFile(output.toString());
    }
    public synchronized void logRecievedOptimisticallyUnchoke(int peerIDOfRecievedUnchokingNeighbor){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfClient);
        output.append(" is unchoked by Peer ");
        output.append(peerIDOfRecievedUnchokingNeighbor);
        addToLogFile(output.toString());
    }
    public synchronized void logChoke(int peerIDOfChokedNeighbor){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfClient);
        output.append(" is choked by Peer ");
        output.append(peerIDOfChokedNeighbor);
        addToLogFile(output.toString());
    }
    public synchronized void logHaveMessage(int peerIDOfHaveSender, int pieceIndex){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfClient);
        output.append(" received the 'have' message from ");
        output.append(peerIDOfHaveSender);
        output.append(" for the piece ");
        output.append(pieceIndex);
        addToLogFile(output.toString());
    }
    public synchronized void logInterestedMessage(int peerIDOfInterested){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfClient);
        output.append(" received the 'interested' message from ");
        output.append(peerIDOfInterested);
        addToLogFile(output.toString());
    }
    public synchronized void logNotInterestedMessage(int peerIDOfNotInterested){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfClient);
        output.append(" received the 'not interested' message from ");
        output.append(peerIDOfNotInterested);
        addToLogFile(output.toString());
    }
    public synchronized void logDownloading(int peerIDOfPieceDownloadedFrom, int pieceIndex,int numberOfPiecesInPossesion){
        StringBuilder output=new StringBuilder();
        output.append(date.toString());
        output.append(": Peer ");
        output.append(peerIDOfClient);
        output.append(" has downloaded the piece ");
        output.append(pieceIndex);
        output.append(" from ");
        output.append(peerIDOfPieceDownloadedFrom);
        output.append(".\nNow the number of pieces it has is ");
        output.append(numberOfPiecesInPossesion);
        addToLogFile(output.toString());
    }
    public synchronized void logCompletition(){
        if(!completedFile) {
            StringBuilder output = new StringBuilder();
            output.append(date.toString());
            output.append(": Peer ");
            output.append(peerIDOfClient);
            output.append(" has downloaded the complete file.");
            addToLogFile(output.toString());
            completedFile=true;
        }
    }
    public synchronized void addToLogFile(String log) {
        try {
            bufferedWriter.write(log);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void closeLog(){
        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
