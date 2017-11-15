import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

	public static void main(String[] args) {
		ServerSocket server= null;
		Socket socket = null;
		File xmlFile = null;
		DataOutputStream out = null;
		
		
		try {
			server = new ServerSocket(4444);
			socket = server.accept();
			xmlFile = new File("XML-output.txt");
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		
		byte[] xmlFileSize = new byte[(int) xmlFile.length()];
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(xmlFile);
			fileIn.read(xmlFileSize);
			fileIn.close();
			out.writeInt(xmlFileSize.length);
			out.write(xmlFileSize );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			out.close();
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
