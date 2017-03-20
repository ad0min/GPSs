package gateway;

import java.net.*;


import java.io.*;
public class Gateway {
	public static void main(String args[]){
		ServerSocket listener =null;
		try{
			listener= new ServerSocket(4444);
		}
		catch(IOException e){
			System.err.println(e);
			System.exit(1);
		}
		try{
			System.out.println("waiting...");
			
			while(true){
				Socket socket = listener.accept();

				ServiceThread t= new ServiceThread(socket);
				t.start();
			}
		}catch(IOException e){
			System.err.println(e);
			System.out.println("err1");
			System.exit(1);
		}	
	}
	
	public static class ServiceThread extends Thread{
		private Socket socket;
		public ServiceThread(Socket socket){
			this.socket=socket;
		}
		public void run(){
			try{
				System.out.println("COnnect success...");
				BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		           
				String line;
				while((line= is.readLine())!=null){
					System.out.println("Gateway receive: "+line);

				}
			}catch(IOException e){
				System.err.println(e);
				e.printStackTrace();//?
				System.exit(1);	
			}
		}
	}
	
}