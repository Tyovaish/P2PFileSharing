package Peer;
import File.PeerInfoFileParser;
import TCPConnection.Neighbor.NeighborManager;
import TCPConnection.TCPConnection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PeerClient {
    PeerInfo peerInfo;
    NeighborManager neighborManager;

    public PeerClient(int peerID)
    {
        this.neighborManager=new NeighborManager();
        this.peerInfo=new PeerInfo(peerID,PeerInfoFileParser.getPeerInfo(peerID).getPortNumber());
    }
    public void run() {
        connectToPreviousPeers();
        new Thread(neighborManager).start();
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
    public PeerInfo getPeerInfo(){
        return peerInfo;
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
            new Thread(peer).start();
        }
    }
    


}