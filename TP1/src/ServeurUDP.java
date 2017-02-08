import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServeurUDP {
	private DatagramSocket dgSocket;

	ServeurUDP(int pSrv) throws IOException {
		dgSocket = new DatagramSocket(pSrv);
	}

	void go() throws IOException {
		DatagramPacket dgPacket = new DatagramPacket(new byte[1500], 1500);
		String str;

		while (true) {
			dgSocket.receive(dgPacket);
			System.out.println("Datagram␣received␣from␣" + dgPacket.getSocketAddress());
			System.out.println(new String(dgPacket.getData()));
			int nbRepet = Integer.parseInt(new String(dgPacket.getData()).charAt(0)+"");
			System.out.println("Nb : " + nbRepet);
			
			String[] chaine = (new String(dgPacket.getData()).substring(1)).split(" ");
			System.out.println(chaine[0]);
			dgPacket.setSocketAddress(dgPacket.getSocketAddress());
			str = new java.util.Date().toString() + "\n";
			byte[] bufDate = str.getBytes();
			dgPacket.setData(bufDate, 0, bufDate.length);
			dgSocket.send(dgPacket);
		}
	}

	public static void main(String[] args) throws IOException {
		final int DEFAULT_PORT = 9876;
		new ServeurUDP(args.length == 0 ? DEFAULT_PORT : Integer.parseInt(args[0])).go();
	}
}