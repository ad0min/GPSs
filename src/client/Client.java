package client;


import java.io.*; 
import java.text.*;
import java.util.Date;
import java.net.*;
public class Client {
//	 
//	   public static void main(String[] args) {
//	 
//	       // Địa chỉ máy chủ.
//	       final String serverHost = "localhost";
//	 
//	       Socket socketOfClient = null;
//	       BufferedWriter os = null;
//	       BufferedReader is = null;
//	 
//	       try {
//	           // Gửi yêu cầu kết nối tới Server đang lắng nghe
//	           // trên máy 'localhost' cổng 9999.
//	           socketOfClient = new Socket(serverHost, 9999);
//	 
//	           // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
//	           os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
//	 
//	           // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
//	           is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
//	 
//	       } catch (UnknownHostException e) {
//	           System.err.println("Don't know about host " + serverHost);
//	           return;
//	       } catch (IOException e) {
//	           System.err.println("Couldn't get I/O for the connection to " + serverHost);
//	           return;
//	       }
//	 
//	       try {
//	           // Ghi dữ liệu vào luồng đầu ra của Socket tại Client.
//	           os.write("HELO");
//	           os.newLine(); // kết thúc dòng
//	           os.flush();  // đẩy dữ liệu đi.
//	           os.write("I am Tom Cat");
//	           os.newLine();
//	           os.flush();
//	           os.write("QUIT");
//	           os.newLine();
//	           os.flush();
//	 
//	           // Đọc dữ liệu trả lời từ phía server
//	           // Bằng cách đọc luồng đầu vào của Socket tại Client.
//	           String responseLine;
//	           while ((responseLine = is.readLine()) != null) {
//	               System.out.println("Server: " + responseLine);
//	               if (responseLine.indexOf("OK") != -1) {
//	                   break;
//	               }
//	           }
//	 
//	           os.close();
//	           is.close();
//	           socketOfClient.close();
//	       } catch (UnknownHostException e) {
//	           System.err.println("Trying to connect to unknown host: " + e);
//	       } catch (IOException e) {
//	           System.err.println("IOException:  " + e);
//	       }
//	   }
//	 
//	}
	FileInputStream in = null;
	
	final String serverHost="localhost";
	Socket socket =null;
	public static void main(String []argv){
		Client cl= new Client();
		cl.init(2000);
	}
	
	public void init(int t) {
	       final String serverHost = "localhost";
	       
	       Socket socketOfClient = null;
	       BufferedWriter os = null;
	       BufferedReader is = null;
	 
	       try {

	           socketOfClient = new Socket(serverHost, 5556);

	           os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));

				FileInputStream in = new FileInputStream("gps.txt");
				is = new BufferedReader(new InputStreamReader(new DataInputStream(in)));
	       } catch (UnknownHostException e) {
	           System.err.println("Don't know about host " + serverHost);
	           return;
	       } catch (IOException e) {
	           System.err.println("Couldn't get I/O for the connection to " + serverHost);
	           return;
	       }
	 
	       try {

	           String responseLine;
	           while ((responseLine = is.readLine()) != null) {
					DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
					Date dateobj = new Date();
					String timeStamp = df.format(dateobj);
	 
					responseLine = responseLine+ ", "+timeStamp;
	               System.out.println("Server: " + responseLine);
	               os.write(responseLine+"\n");
	               os.flush();
	               try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						e.printStackTrace();
					}
	           		}
				
	           os.close();
	           is.close();
	           socketOfClient.close();
	       } catch (UnknownHostException e) {
	           System.err.println("Trying to connect to unknown host: " + e);
	       } catch (IOException e) {
	           System.err.println("IOException:  " + e);
	       }
	   }
}