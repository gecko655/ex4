package pro3.ex4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class TestClient2 {
	static final int PORTNO = 30000;
	final BufferedReader sbr;
	final PrintWriter spw;
	final BufferedReader br;
	private Socket socket;

	public TestClient2(String host, int port) throws IOException {
		socket = new Socket(host, port);
		sbr = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		spw = new PrintWriter(socket.getOutputStream(), true);
		br = new BufferedReader(
				new InputStreamReader(System.in));
	}

	public void communicate() throws IOException {

		new Thread(new Runnable() {
			public void run() {
				try {
					String str = sbr.readLine();
					while (str != null) {
						System.out.println(str);
						str = sbr.readLine();
					}
					System.err.println("Disconnected by server");
				} catch (IOException e) {
					System.err.println(e);
				} finally {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		}).start();

	
		String str = br.readLine();
		while (str != null) {// C-d
			spw.println(str);
			str = br.readLine();
		}
		System.out.println("Disconnected by client");
		socket.close();
		System.exit(0);
	}

	public static void main(String[] args) {
		try {
			TestClient2 tc1 = new TestClient2("localhost",
					TestClient2.PORTNO);
			tc1.communicate();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
