package pro3.ex4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class TestServer2 {
	static final int PORTNO = 30000;
	ServerSocket ss = null;
	ArrayList<TestSession> sessions =
			new ArrayList<TestSession>();

	public TestServer2() throws IOException {
		ss = new ServerSocket(PORTNO);
	}

	public void welcome() {
		while (true) {
			Socket s = null;
			try {
				s = ss.accept();
				System.out.println("Port:" + s.getPort());
				System.out.println("Address:" + s.getInetAddress());
				System.out.println("LocalPort:" + s.getLocalPort());
				System.out.println("LocalAddress:" +
						s.getLocalAddress());
				TestSession session =
						new TestSession(s, this);
				synchronized (this) {
					sessions.add(session);
				}
				session.start();
			} catch (IOException e) {
				System.out.print(e);
				try {
					if (s != null) {
						s.close();
					}
				} catch (IOException ee) {
				}
			}
		}
	}

	public void welcomeJava7() {
		while (true) {
			// try-with-resource 文を使用するとfinallyでのclose()
			// 保証されるため記述不要
			try (Socket s = ss.accept()) {
				System.out.println("Port:" + s.getPort());
				System.out.println("Address:" + s.getInetAddress());
				System.out.println("LocalPort:" + s.getLocalPort());
				System.out.println("LocalAddress:" +
						s.getLocalAddress());
				TestSession session =
						new TestSession(s, this);
				synchronized (this) {
					sessions.add(session);
				}
				session.start();
			} catch (IOException e) {
				System.out.print(e);
			}
		}
	}

	synchronized void out(String str) {
		System.out.println(str);
		for (TestSession session : sessions) {
			session.out(str);
		}
	}

	synchronized void delete(TestSession mst) {
		sessions.remove(mst);
	}

	public static void main(String[] args) {
		try {
			TestServer2 ts2 = new TestServer2();
			ts2.welcome();
		} catch (IOException e) {
			System.out.print(e);
		}
	}
}

class TestSession extends Thread {
	Socket socket;
	TestServer2 srv;
	PrintWriter pw = null;
	//...
    BufferedReader br;

	public TestSession(Socket s, TestServer2 ts2)
			throws IOException {
		this.socket = s;
		this.srv = ts2;
		pw = new PrintWriter(s.getOutputStream(), true);
                //...
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}

	public void run() {
	    try {
	        String message;
	        while((message = br.readLine())!=null)
            srv.out(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		// クライアントからのメッセージを
		// Ctrl-D が来るまで一行づつ読み込み
		// 内容を全クライアントに送信
	}

	public void out(String s) {
		// 自分のクライアントにStringを送信
		pw.println(s);
	}
}
