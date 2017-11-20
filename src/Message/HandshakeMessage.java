package Message;

import Message.Message;
import Peer.PeerInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by Trevor on 10/23/2017.
 */
public class HandshakeMessage{
    final private static String handshakeHeader="P2PFILESHARINGPROJ";
    public static void sendHandshake(DataOutputStream out,int peerID){
        try {
            out.write(handshakeHeader.getBytes());
            out.writeInt(peerID);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readHandshake(DataInputStream in, PeerInfo neighborInfo){
        byte [] handshakeHeaderFromNeighbor=new byte[handshakeHeader.getBytes().length];
        try {
            in.readFully(handshakeHeaderFromNeighbor);
            int peerID=in.readInt();
            neighborInfo.setPeerID(peerID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
