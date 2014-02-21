package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.security.cert.X509Certificate;

public class ConnectionThread extends Thread {
	private SSLSocket socket;
	private String peerName;

	public ConnectionThread(SSLSocket socket) throws IOException {
		this.socket = socket;

		SSLSession session = socket.getSession();
		X509Certificate cert = (X509Certificate) session
				.getPeerCertificateChain()[0];
		this.peerName = cert.getSubjectDN().getName();
	}

	public void run() {
		try {
			PrintWriter out = null;
			BufferedReader in = null;
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			String clientMsg = null;
			while ((clientMsg = in.readLine()) != null) {
				Scanner scan = new Scanner(clientMsg);
				String op = scan.next();
				System.out.println("Received " + op);
				if (op.equals("GET")) {
					out.println("TEG");
				} else if (op.equals("PUT")) {
					out.println("TUP");
				} else if (op.equals("ADD")) {
					out.println("DDA");
				} else if (op.equals("DELETE")) {
					out.println("ETELED");
				} else {
					System.out.println("Unrecognized operation " + op);
					out.println("IDUNNOLOL");
				}
				scan.close();
				out.flush();
			}
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
