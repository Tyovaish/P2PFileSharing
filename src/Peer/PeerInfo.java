package Peer;

/**
 * Created by Trevor on 10/23/2017.
 */
public class PeerInfo {
    int peerID;
    int portNumber;
    String hostName;
    boolean isChoked=true;
    boolean hasfile=false;

    public PeerInfo(int peerID, String hostName,int portNumber, boolean hasFile){
        this.peerID=peerID;
        this.portNumber=portNumber;
        this.hostName=hostName;
        this.hasfile=hasFile;
    }
    PeerInfo(int peerID,int portNumber){
        this.peerID=peerID;
        this.portNumber=portNumber;
    }
    public int getPeerID(){
        return peerID;
    }
    public int getPortNumber(){
        return portNumber;
    }
    public String getHostName(){return hostName;}
    public boolean getHasFile(){return hasfile;}
    public boolean getIsChoked(){return isChoked;}
    public void setIsChoked(boolean isChoked){this.isChoked=isChoked;}
    public PeerInfo copy(){
        return new PeerInfo(peerID,hostName,portNumber,hasfile);
    }
}
