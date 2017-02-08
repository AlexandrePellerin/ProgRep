import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

// J'ai travaillé avec Romain Duquesne
public class Client {

	public String ip;
	public int port;
	public Socket socket;

	public Client(String ip, int port) throws UnknownHostException, IOException {
		this.ip = ip;
		this.port = port;
		this.socket = new Socket(ip, port);
	}

	public void dayTime() {
		BufferedReader time = null;
		try {
			time = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String res = "";
		String buffer = "";
		try {
			while ((buffer = time.readLine()) != null) {
				res = buffer;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		res.trim();
		System.out.println("Il est " + res + " sur " + ip);
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void echo() {
		BufferedReader echo = null;
		Scanner sc = new Scanner(System.in);
		PrintWriter out = null;
		try {
			echo = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String buff = "";
		String fin = "test";
		try {
			while (!(fin.equalsIgnoreCase("FIN") || fin.equals("."))) {
				fin = sc.nextLine();
				out.println(fin);
				// System.out.println("fin: "+fin);
				if (!(fin.equalsIgnoreCase("FIN") || fin.equals("."))) {
					buff = echo.readLine();
					buff.trim();
					System.out.println(buff);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		sc.close();
	}

	public void getHttp() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(this.socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println("GET / HTTP/1.0");
		out.println("");
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String t;
		boolean entete = false;
		try {
			while ((t = in.readLine()) != null) {
				if (t.equals("")) {
					if (!entete) {
						entete = !entete;
					}
				}
				if (entete) {
					System.out.println(t);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.close();
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		Client cl = new Client("127.0.0.1", 13);
		cl.dayTime();
		Client cl2 = new Client("127.0.0.1", 7);
		cl2.echo();
		Client cl3 = new Client("127.0.0.1", 80);
		cl3.getHttp();
		Client cl4 = new Client("127.0.0.1", 9876);
		cl4.dayTime();
	}
}
