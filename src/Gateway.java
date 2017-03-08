package New;

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
				ServiceThread t= new ServiceThread(socket, clientNo++);
				t.start();
//				try{
//				t.join();
//				}catch(Exception e ){
//					System.out.println(e);
//				}
			}
		}catch(IOException e){
			System.err.println(e);
			System.exit(1);
		}
//		}finally{
//			listener.close();
//		}
		
	}
	
	
	
//	public static void main(String []args){
//		InputStreamReader cin =null;
//		try {
//		cin = new InputStreamReader(System.in);
//		String s = null;
//			do {
//				BufferedReader br = new BufferedReader(cin);
//				s= br.readLine();
//				System.out.println(s);
//				
//				Mqtt temp = new Mqtt();
//				temp.New(s);
//				
//			} while (s != "q");
//
//			cin.close();
//		}catch(IOException e){
//			System.out.println(e);
//		}
//	}
	
	
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
					
					System.out.println("Gateway receive: "+line);
					
					Mqtt _info = new Mqtt();
					_info.New(line);
				}
			}
			catch(IOException e){
				System.err.println(e);
				e.printStackTrace();//?
			}
		}
	}
	
}