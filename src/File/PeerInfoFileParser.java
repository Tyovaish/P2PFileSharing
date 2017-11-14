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
    ArrayList<PeerInfo> peersToConnect;
    public PeerInfoFileParser(String peerInfoFileName) {
        peersToConnect=new ArrayList<PeerInfo>();
        String line = null;
        try {
            FileReader fileReader = new FileReader(peerInfoFileName);
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
    }
    public ArrayList<PeerInfo> getPeersToConnect(){

        return peersToConnect;
    }
}
