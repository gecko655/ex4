package pro3.ex4;

import java.net.*;
import java.io.*;

class TestServer1 {
	ServerSocket ss = null;
	public static final int PORTNO = 30000;

	public TestServer1() {
		try {
			ss = new ServerSocket(PORTNO);
		} catch (IOException e) {
			System.out.print(e);
		}
	}

	public void welcome() {
		Socket s = null;
		try {
			s = ss.accept();
			System.out.println("Port:" + s.getPort());
			System.out.println("Address:" + s.getInetAddress());
			System.out.println("LocalPort:" + s.getLocalPort());
			System.out.println("LocalAddress:"
					+ s.getLocalAddress());
			communicate(s);
		} catch (IOException e) {
			System.out.print(e);
		} finally {
			try {
				if (s != null) {
					s.close();
				}
			} catch (IOException ee) {
			}
		}
	}

	public void welcomeJava7() {
		try (Socket s = ss.accept()) {
			System.out.println("Port:" + s.getPort());
			System.out.println("Address:" + s.getInetAddress());
			System.out.println("LocalPort:" + s.getLocalPort());
			System.out.println("LocalAddress:"
					+ s.getLocalAddress());
			communicate(s);
		} catch (IOException e) {
			System.out.print(e);
		}
	}
  
	public void goodby() {
		try {
			if (ss != null) {
				ss.close();
			}
		} catch (IOException e) {
		}
	}

	private void communicate(Socket socket)
			throws IOException {
		final BufferedReader br =
				new BufferedReader(new
						InputStreamReader(
								socket.getInputStream()));
		final PrintWriter pw = new PrintWriter(
				socket.getOutputStream(), true);
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			pw.println(line);
		}
		socket.close();
	}

	public static void main(String[] args) {
		TestServer1 ts2 = new TestServer1();
		ts2.welcome();
		ts2.goodby();
	}
}
