package Peer;
import File.CommonFileParser;
import File.PeerInfoFileParser;
import Message.Message;
import TCPConnection.Neighbor.IntervalManager;
import TCPConnection.TCPConnection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import static Message.Message.HAVE;

public class PeerClient {
    PeerInfo peerInfo;
    IntervalManager intervalManager;
    ArrayList<TCPConnection> neighbors;
    Comparator<TCPConnection> comp;
    PriorityQueue<TCPConnection> preferred;
    int NumberOfPreferredNeighbors;
    public PeerClient(int peerID)
    {
        neighbors=new ArrayList<TCPConnection>();
        NumberOfPreferredNeighbors = CommonFileParser.getNumberOfPreferredNeighbors();
        comp = new NeighborComparator();
        preferred = new PriorityQueue<TCPConnection>(NumberOfPreferredNeighbors, comp);
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
        System.out.println(positionInitialized);
        for(int i=positionInitialized-1;i>=0;--i){
            TCPConnection peer=new TCPConnection(this,allPeerInfo.get(i));
            neighbors.add(peer);
            new Thread(peer).start();
        }
    }
    public void sendHaveMessageToNeighbors(int pieceIndex){
        for(int i=0;i<neighbors.size();i++){
            neighbors.get(i).sendMessage(new Message(HAVE,pieceIndex));
        }
    }
    public void unchokeBestNeighbors(){
        int NumberOfPreferredNeighbors = CommonFileParser.getNumberOfPreferredNeighbors();
        for(int i = 0; i < neighbors.size(); i++){
            neighbors.get(i).getNeighborState().chokeNeighbor();
            if( preferred.size() < NumberOfPreferredNeighbors || comp.compare(neighbors.get(i), preferred.peek()) == 1){
                if(preferred.size() == NumberOfPreferredNeighbors){
                    preferred.remove(preferred.peek());
                }
                preferred.offer(neighbors.get(i));
            }
        }
        for(int j = 0; j < preferred.size(); j++){
            preferred.peek().getNeighborState().unchokeNeighbor();
        }
    }
    public void optimisticallyUnchoke(){

    }
    public PeerInfo getPeerInfo(){
        return peerInfo;
    }
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
                System.out.println("About to start thread");
                new Thread(currentTCPConnection).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


}