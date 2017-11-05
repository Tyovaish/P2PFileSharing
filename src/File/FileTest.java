import java.io.*;
import java.lang.*;

public class FileTest {
  public static void main(String args[]) {
    String filePath = "~/Documents/bitch.txt";

    File file = new File(filePath);
    FileInputStream fis = new FileInputStream(file);

    fis.close();

    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    byte[] bf = new byte[1024];

    try {
      for (int readNum; (readNum = fis.read(bf)) != -1;) {
        bos.write(bf, 0, readNum);
        System.out.println("read " + readNum + " bytes,");
      }
    }
    catch (IOException ex){
      throw(ex);
    }

    byte[] byteFile = bos.toByteArray();

    FileParser fp = new FileParser(filePath, byteFile.length, byteFile.length);

    fp.setPiece(byteFile, 0);

    System.out.println(fp.getPiece(0));

    fp.bytesToFile();
  }
}
