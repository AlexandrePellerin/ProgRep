import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientUDP {

	private DatagramPacket out = null;
	private DatagramSocket sc = null;

	public ClientUDP(String[] args) throws UnknownHostException {
		InetAddress DEFAULT_ADDRESS = InetAddress.getByName("localhost");
		final int DEFAULT_PORT = 9876;

		try {
			sc = new DatagramSocket();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		try {
			this.envoie("2Bonjour le monde", args.length == 0 ? DEFAULT_ADDRESS : InetAddress.getByName(args[1]),
					args.length < 2 ? DEFAULT_PORT : Integer.parseInt(args[0]));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void envoie(String message, InetAddress adresse, int port) {
		this.envoie(message.getBytes(), adresse, port);
	}

	public void envoie(byte[] buf, InetAddress adresse, int port) {
		out = new DatagramPacket(buf, buf.length, adresse, port);
		try {
			sc.send(out);
			sc.receive(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Adresse : " + out.getAddress());
		System.out.println("Port : " + out.getPort());
		System.out.println("Data : " + new String(out.getData()));
	}

	public static void main(String[] args) throws UnknownHostException {
		new ClientUDP(args);
	}
}
