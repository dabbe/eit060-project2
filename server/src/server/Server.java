package server;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Server {

	public static void main(String args[]) {
		new Server().createFrame();
	}

	private void createFrame() {
		JFrame frame = new JFrame();
		JButton button = new JButton("Start");
		button.addActionListener(new Listener(frame, button));
		frame.add(button);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
	}

	private class Listener implements ActionListener {

		private boolean started = false;
		private final JButton button;
		private final JFrame frame;

		public Listener(JFrame frame, JButton button) {
			this.button = button;
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			if (!started) {
				final int port = 9999;
				new Thread() {
					@Override
					public void run() {
						try {
							button.setText("Listening on port " + port + " | Press to shut down");
							frame.pack();
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
							SSLServerSocketFactory ssf = getServerSocketFactory("TLS");
							SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(port);
							ss.setNeedClientAuth(true);
							listen(ss);
						} catch (IOException e) {
							System.exit(1);
							System.out.println("Unable to start Server: " + e.getMessage());
						}
					}
				}.start();
			} else {
				System.exit(1);
			}
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

			File currentDirectory = new File(new File(".").getAbsolutePath());
			ks.load(new FileInputStream(currentDirectory.getCanonicalPath() + "/Desktop/EIT060/serverkeystore"), password);
			ts.load(new FileInputStream(currentDirectory.getCanonicalPath() + "/Desktop/EIT060/servertruststore"), password);
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
