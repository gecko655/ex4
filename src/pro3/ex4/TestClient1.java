package pro3.ex4;

import java.io.*;
import java.net.*;

class TestClient1 {
	final BufferedReader sbr;
	final PrintWriter spw;
	final BufferedReader br;
	private Socket socket;

	public TestClient1(String host, int port) throws IOException {
		socket = new Socket(host, port);
		sbr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		spw = new PrintWriter(socket.getOutputStream(), true);
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	public void communicate() throws IOException {
		String str = br.readLine();
		while (str != null) {// repeat until C-d
			spw.println(str);
			System.out.println(sbr.readLine());
			str = br.readLine();
		}
	}

	public void close() throws IOException {
		socket.close();
	}

	public static void main(String[] args) {
		try {
			TestClient1 tc1 = new TestClient1("localhost", TestServer1.PORTNO);
			tc1.communicate();
			tc1.close();

		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
