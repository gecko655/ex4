package pro3.ex4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class TestClient2Java7 {
	static final int PORTNO = 30000;
	volatile boolean submiting = true;

	public void communicate(String host, int port)  {
		// Geranate a socket to conect a server.
		try (Socket socket = new Socket(host, port)) {
			// reading thread
			Thread readT = 	new Thread(new Runnable() {
				public void run() {
					try (BufferedReader sbr = new BufferedReader(
							new InputStreamReader(socket.getInputStream()))) {
						String str = sbr.readLine();
						while (str != null && submiting  ) {
							System.out.println(str);
							str = sbr.readLine();
						}
					} catch (IOException e) {
						if (submiting) {
							System.err.println("Disconnected by server");
							e.printStackTrace(System.err);
						}
					}
				}
			});
			readT.start();

			// writing
			try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					PrintWriter spw = new PrintWriter(socket.getOutputStream(), true)) {
				String str = br.readLine();
				while (str != null) {// C-d
					spw.println(str);
					str = br.readLine();
				}
				System.out.println("End Submission");
				System.out.println("Disconnected by client");
			} catch (IOException e) {
				e.printStackTrace(System.err);
			} finally {
				submiting = false;
				readT.join();
				System.exit(0);
			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace(System.err);
		}
	}

	public static void main(String[] args) {
			TestClient2Java7 tc1 = new TestClient2Java7();
			tc1.communicate("localhost",
					TestClient2Java7.PORTNO);
	}
}
