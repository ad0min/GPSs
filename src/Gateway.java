import java.net.*;
import java.io.*;
public class Gateway {
	public static void main(String args[]){
		ServerSocket listener =null;
		System.out.println("waiting...");
		int clientNo=0;
		try{
			listener= new ServerSocket(4444);
		}
		catch(IOException e){
			System.err.println(e);
			System.exit(1);
		}
		try{
			while(true){
				Socket socket = listener.accept();
				new ServiceThread(socket, clientNo++).start();
			}
		}catch(IOException e){
			System.err.println(e);
			System.exit(1);
		}
//		}finally{
//			listener.close();
//		}
		
	}
	public static class ServiceThread extends Thread{
		private int clientNo;
		private Socket socket;
		public ServiceThread(Socket socket, int clientNo){
			this.clientNo=clientNo;
			this.socket=socket;
		}
		public void run(){
			try{
				BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line;
				while((line= is.readLine())!=null){
					
					System.out.println(line);
				}
			}
			catch(IOException e){
				System.err.println(e);
				e.printStackTrace();//?
			}
		}
	}
	
}
