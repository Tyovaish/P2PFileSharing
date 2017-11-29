package Peer;
import File.CommonFileParser;
import File.FileParser;
import File.PeerInfoFileParser;
import Message.Message;
import TCPConnection.Neighbor.IntervalManager;
import TCPConnection.Neighbor.NeighborComparator;
import TCPConnection.TCPConnection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;

import Logger.InformationLogger;

import static Message.Message.HAVE;

public class PeerClient {
    PeerInfo peerInfo;
    IntervalManager intervalManager;
    int numberOfPeersToConnect=PeerInfoFileParser.numberOfPeersToConnect();
    ArrayList<TCPConnection> neighbors;
    Comparator<TCPConnection> comp;
    ArrayList<TCPConnection> preferred;
    FileParser file;
    InformationLogger log;
    int NumberOfPreferredNeighbors;
    public PeerClient(int peerID)
    {
        neighbors=new ArrayList<TCPConnection>();
        file=new FileParser(peerID);
        log=new InformationLogger(peerID);
        NumberOfPreferredNeighbors = CommonFileParser.getNumberOfPreferredNeighbors();
        comp = new NeighborComparator();
        preferred = new ArrayList<TCPConnection>();
        this.intervalManager=new IntervalManager(this);
        this.peerInfo=new PeerInfo(peerID,PeerInfoFileParser.getPeerInfo(peerID).getPortNumber());
    }
    private void connectToPreviousPeers(){
        ArrayList<PeerInfo> allPeerInfo=PeerInfoFileParser.getPeersToConnect();
        int positionInitialized=-1;
        for(int i=0;i<allPeerInfo.size();i++){
            if(peerInfo.getPeerID()==allPeerInfo.get(i).getPeerID()){
                positionInitialized=i;
            }
        }
        for(int i=positionInitialized-1;i>=0;--i){
            TCPConnection peer=new TCPConnection(this,allPeerInfo.get(i));
            neighbors.add(peer);
            new Thread(peer).start();
        }
    }
    public synchronized void sendHaveMessageToNeighbors(int pieceIndex){
        if(file.isFinished()){
            log.logCompletition();
        }
        for(int i=0;i<neighbors.size();i++){
            neighbors.get(i).getMessageHandler().sendHaveMessage(pieceIndex);
        }
    }
    public void unchokeBestNeighbors(){
        //reorder list by data rate
        Collections.sort(neighbors, comp);
        //choke all neighbors
        for(int i = 0; i < neighbors.size(); i++){
            if(!neighbors.get(i).getNeighborState().isChokingNeighbor()) {
                neighbors.get(i).getMessageHandler().sendChokeMessage();
            }
        }
        //clear previous preferred list
        preferred.clear();
        //create new preferred list
        for(int i = 0; i < neighbors.size(); i++){
            if(preferred.size() <= NumberOfPreferredNeighbors && neighbors.get(i).getNeighborState().isInterestedInClient()){
                preferred.add(neighbors.get(i));
                neighbors.get(i).getMessageHandler().sendUnchokeMessage();
            }
        }
        if(preferred.size()!=0) {
            log.logChangePrefferedNeighbors(preferred);
        }
    }
    public void optimisticallyUnchoke(){
        Random random = new Random();
        int i;
        int remaining = neighbors.size();
        while(remaining > 0){
            i = random.nextInt(neighbors.size());
            if(!neighbors.get(i).getNeighborState().isChokingNeighbor() || !neighbors.get(i).getNeighborState().isInterestedInClient()){
                remaining--;
                continue;
            }
            neighbors.get(i).getMessageHandler().sendUnchokeMessage();
            log.logOptimisticallyUnchokeNeighbor(neighbors.get(i).getNeighborState().getNeighborPeerID());
            break;
        }
    }
    public boolean allFinished(){
        if(neighbors.size()!=numberOfPeersToConnect){
            return false;
        }
        for(int i=0;i<neighbors.size();i++){
            if(neighbors.get(i).isFinished()==false){
                System.out.println("Peer "+neighbors.get(i).getNeighborPeerInfo().getPeerID());
                return false;
            }
        }
        return file.isFinished();
    }
    public void shutdown(){
        for(int i=0;i<neighbors.size();i++){
            try {
                neighbors.get(i).getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public PeerInfo getPeerInfo(){
        return peerInfo;
    }
    public FileParser getFile(){return file;}
    public InformationLogger getInformationLogger(){return log;}
    public void run() {
        connectToPreviousPeers();
        new Thread(intervalManager).start();
        try {
            ServerSocket serverSocket = new ServerSocket(peerInfo.getPortNumber());
            System.out.println("Running on portNumber "+peerInfo.getPortNumber());
            while (true) {
                Socket neighbor=serverSocket.accept();
                System.out.println("Accepted");
                TCPConnection currentTCPConnection = new TCPConnection(this, neighbor);
                neighbors.add(currentTCPConnection);
                System.out.println("About to start thread");
                new Thread(currentTCPConnection).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


}