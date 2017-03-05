

import java.io.*;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.net.*;
public class Client {

	public static void main(String[] args) {
		FileInputStream in = null;
		
		final String serverHost="localhost";
		BufferedWriter os=null;
		Socket socket =null;
		try {
			in = new FileInputStream("gps.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(in)));
			String str;
			while ((str = br.readLine()) != null) {
				DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				Date dateobj = new Date();
				String timeStamp = df.format(dateobj);
 
				str= str+ ","+timeStamp;
				System.out.println(str);
				try{
					socket=new Socket(serverHost, 4444);
					os =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					os.write(str);
					os.flush();
				
				}catch(UnknownHostException e){
					System.err.println(e);
					System.exit(1);
				}catch(IOException e){
					System.err.println(e);
					System.exit(1);
				}
				
				os.close();
				socket.close();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
			br.close();

		} catch (IOException e) {
			System.err.println(e);
		}
		
	}
}