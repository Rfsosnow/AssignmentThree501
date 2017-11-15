import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class client {

	public static void main(String[] args) {
		Socket client = null;
		String ipAddress = "127.0.0.1";
		
		try {
			client = new Socket(ipAddress, 4444);
		}catch(IOException e) {
			e.printStackTrace();
		}
		DataInputStream fileIn = null;
		try {
			 fileIn = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		int size = 0;
		try {
			size = fileIn.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(size>0) {
			byte[] xmlReceived = new byte[size];
			try {
				fileIn.readFully(xmlReceived, 0, size);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}
