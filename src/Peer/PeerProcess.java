package Peer;
import File.PeerInfoFileParser;
import TCPConnection.TCPConnection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PeerProcess{
    PeerInfo peerInfo;
    ArrayList<TCPConnection> tcpConnections;
    byte [][] currentFile;
    public PeerProcess(int peerID,int portNumber) {
        this.peerInfo=new PeerInfo(peerID,portNumber);
    }
    public void run() {
        connectToPreviousPeers();
        try {
            ServerSocket serverSocket = new ServerSocket(peerInfo.getPortNumber());
            System.out.println("Running on portNumber "+peerInfo.getPortNumber());
            while (true) {
                Socket potentialNeighbor=serverSocket.accept();
                System.out.println("Accepted");
                TCPConnection currentTCPConnection = new TCPConnection(this, potentialNeighbor);
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
        PeerInfoFileParser peerInfoFileParser=new PeerInfoFileParser("C:\\Users\\Trevor\\IdeaProjects\\P2PFileSharingProject\\src\\Test\\FileTest\\PeerInfo.cfg");
        ArrayList<PeerInfo> allPeerInfo=peerInfoFileParser.getPeersToConnect();
        int positionInitialized=-1;
        for(int i=0;i<allPeerInfo.size();i++){
            if(peerInfo.getPeerID()==allPeerInfo.get(i).getPeerID()){
                System.out.println("Yes");
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