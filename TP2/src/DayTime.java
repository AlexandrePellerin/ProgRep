import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DayTime {

	public int port;

	public DayTime(int port) {
		this.port = port;
	}

	public void go() {
		ServerSocket servSocket;
		Socket socket;
		try {
			servSocket = new ServerSocket(9876);
			while(true){
				socket = servSocket.accept();
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(new java.util.Date());
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DayTime serv = new DayTime(9876);
		serv.go();
	}
}
