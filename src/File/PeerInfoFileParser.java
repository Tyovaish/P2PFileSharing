package File;

import Peer.PeerInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Trevor on 11/1/2017.
 */
public class PeerInfoFileParser {
    public static final String filePath="C:\\Users\\Trevor\\IdeaProjects\\NetworkingProject\\src\\Test\\FileTest\\PeerInfo.cfg";
    public static  ArrayList<PeerInfo> getPeersToConnect(){
        String line = null;
        ArrayList<PeerInfo> peersToConnect=new ArrayList<PeerInfo>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                String[] peerInfoProperties = line.split(" ");
                int peerID=Integer.parseInt(peerInfoProperties[0]);
                String hostName=peerInfoProperties[1];
                int portNumber=Integer.parseInt(peerInfoProperties[2]);
                boolean hasFile=false;
                if(peerInfoProperties[3].compareTo("1")==0){
                    hasFile=true;
                }
                peersToConnect.add(new PeerInfo(peerID,hostName,portNumber,hasFile));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peersToConnect;
    }
    public static PeerInfo getPeerInfo(int peerID) {
        ArrayList<PeerInfo> peers=getPeersToConnect();
        for(int i=0;i<peers.size();i++){
            if(peers.get(i).getPeerID()==peerID){
                return peers.get(i);
            }
        }
        return null;
    }
}
