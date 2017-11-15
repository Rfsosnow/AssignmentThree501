import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

	public static void main(String[] args) {
		ServerSocket server= null;
		Socket socket = null;
		DataInputStream fileIn = null;
		
		
		try {
			server = new ServerSocket(4444);
			socket = server.accept();
			fileIn = new DataInputStream(socket.getInputStream());
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		int size = 0;
		try {
			size = fileIn.readInt();
			System.out.println("client sent file size");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Got size of file: "+size);
		
		if(size>0) {
			byte[] xmlReceived = new byte[size];
			try {
				fileIn.readFully(xmlReceived, 0, size);
				System.out.println(xmlReceived);
				FileOutputStream outFile = null;
				try {
					
					//CHANGE FILE LOCATION EVERY TIME
					outFile = new FileOutputStream("U:\\Eclipse\\eclipseWorkSpace\\server\\src\\server\\XML-received.txt");
				
					outFile.write(xmlReceived);
				}finally {
					outFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
