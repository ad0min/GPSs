package client;
import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.text.DefaultCaret;

public class ClientGUI extends JFrame{
	private JTextField setTime;
	private JLabel timeLabel;
	private JTextField serverIp;
	private JLabel serverLabel;
	private JTextField serverPort;
	private JLabel portLabel;
	private JButton connect;
	private JButton disconnect;
	private JTextArea displayTA;
	private BufferedReader reader;
	private BufferedWriter writer;
	private FileInputStream in;
	private Socket socket;
	private String txt= "gps.txt";
	private boolean flat_conn=true;// co ket noi 
	private DefaultCaret caret ;
	
	public static void main(String []argv){
		try {
			ClientGUI frame = new ClientGUI();
			frame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ClientGUI(){
		super();
		setTitle("GPSs Client");
		setBounds(750,350,549, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(50,500);
		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		serverLabel = new JLabel("IP address server: ");
		
		serverIp = new JTextField(12);
		serverIp.setText("localhost");
		portLabel = new JLabel("Port :");
		serverPort= new JTextField(6);
		serverPort.setText("4444");
		timeLabel =new JLabel("Time: ");
		setTime = new JTextField(5);
		setTime.setText("1000");
		
		
		panel.add(serverLabel);
		panel.add(serverIp);
		panel.add(portLabel);
		panel.add(serverPort);		
		panel.add(timeLabel);
		panel.add(setTime);
		panel.add(new JLabel("(ms)"));

		final JPanel panel1 = new JPanel();
		getContentPane().add(panel1, BorderLayout.AFTER_LAST_LINE);
		
		
		connect = new JButton("Connect");
		disconnect = new JButton("Disconnect");
		panel1.add(connect);
		panel1.add(disconnect);
		
		getGPStext(txt);
		
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent arg){
				flat_conn = true;				

				sendGPS();
			}
		});
		disconnect.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent arg){	
				flat_conn = false;
			}
		});
		
		final JScrollPane scrollPane_1 = new JScrollPane();
		getContentPane().add(scrollPane_1, BorderLayout.CENTER);

		displayTA = new JTextArea();

		scrollPane_1.setViewportView(displayTA);
		
		
		
	}
	
	public void getGPStext(String filename){
		try{
			in = new FileInputStream(filename);
			reader= new BufferedReader(new InputStreamReader(new DataInputStream(in)));
		}catch(IOException e){
			e.printStackTrace();
			display("Exception: "+e);
		}		
	}
	public void sendGPS(){
		String str;
		
		try{
			socket=new Socket(serverIp.getText(), Integer.parseInt(serverPort.getText()));
			writer =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while((str= reader.readLine())!=null && flat_conn==true){
				DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				Date dateobj = new Date();
				String timeStamp = df.format(dateobj); 
				str= str+ ", "+timeStamp;
				try{
					writer.write(str+"\n");
					writer.flush();
					display("Send: "+str);			
				}catch(IOException e){
					e.printStackTrace();
					display("Exception: "+e);
				}
				try {
					Thread.sleep(Integer.parseInt(setTime.getText()));
				}catch (NumberFormatException e) {
					e.printStackTrace();
					display("Exception: " + e.getMessage());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
					display("Exception: "+e);
				}
			}
			writer.close();
			socket.close();
		}catch(UnknownHostException e){
			e.printStackTrace();
			display("Exception: "+e);
		}catch(IOException e){
			e.printStackTrace();
			display("Exception: "+e);
		}
				
	}
	public void display(String e){
		displayTA.append(e+"\n");
		displayTA.setCaretPosition(displayTA.getDocument().getLength());
		displayTA.update(displayTA.getGraphics());
	}
	
}
