package pro3.ex4;

import java.net.*;
import java.io.*;

class TestServer0 {
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(30000);
			Socket s = ss.accept();
			System.out.println("Port:" + s.getPort());
			System.out.println("Address:" + s.getInetAddress());
			System.out.println("LocalPort:" + s.getLocalPort());
			System.out.println("LocalAddress:" + s.getLocalAddress());
			s.close();
			ss.close();
		} catch (IOException e) {
			System.out.print(e);
		}
	}
}
