import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

public class client {

	public static void main(String[] args) {
		Socket client = null;
		String ipAddress = "127.0.0.1";
		File xmlFile = null;
		DataOutputStream out = null;
		FileInputStream fileIn = null;

		try {
			client = new Socket(ipAddress, 4444);
			
			//CHANGE FILE LOCATION EVERY TIME RERUN
			xmlFile = new File("U:\\Eclipse\\eclipseWorkSpace\\client\\src\\client\\XML-output.txt");
			out = new DataOutputStream(client.getOutputStream());
			fileIn = new FileInputStream(xmlFile);
		}catch(IOException e) {
			e.printStackTrace();
		}
		long length = xmlFile.length();
		if(length == 0) {
			System.out.println("File contains no data");
		} else if(length > Integer.MAX_VALUE) {
			System.out.println("File too large");
		}
		
		byte[] xmlFileSize = new byte[(int) xmlFile.length()];
		try {
			out.writeInt(xmlFileSize.length);
			int count;
			while((count = fileIn.read(xmlFileSize)) > 0) {
				out.write(xmlFileSize, 0, count);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		try {
			out.close();
			fileIn.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	
	
	
}
