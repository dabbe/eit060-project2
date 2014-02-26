package server;

import java.io.*;
import java.security.KeyStore;

import javax.net.ssl.*;

public class Server {
	
	
	public static void main(String args[]) {
		System.out.println("\nServer Started\n");
		int port = -1;
		if (args.length >= 1) {
			port = Integer.parseInt(args[0]);
		} else{
			port = 9999;
		}
		try {
			SSLServerSocketFactory ssf = getServerSocketFactory("TLS");
			SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(port);
			ss.setNeedClientAuth(true);
			listen(ss);
		} catch (IOException e) {
			System.out.println("Unable to start Server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void listen(SSLServerSocket server) {
		Monitor monitor = new Monitor();
		while (true) {
			try {
				System.out.println("Listening...");
				SSLSocket socket = (SSLSocket) server.accept();
				System.out.println("Processing connection");
				new ConnectionThread(socket, monitor).start();
			} catch (IOException e) {
				System.out.println("Failed to accept or verify to connection");
				e.printStackTrace();
			}
		}
	}
	

	private static SSLServerSocketFactory getServerSocketFactory(String type) {
		SSLServerSocketFactory ssf = null;
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore ts = KeyStore.getInstance("JKS");
			char[] password = "password".toCharArray();
			ks.load(new FileInputStream(new File("bin/serverkeystore").getAbsolutePath()), password);
			ts.load(new FileInputStream(new File("bin/servertruststore").getAbsolutePath()), password);
			kmf.init(ks, password);
			tmf.init(ts);
			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			ssf = ctx.getServerSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ssf;
	}
}
