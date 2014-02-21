import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import resources.Request;

import com.google.gson.Gson;

public class HospitalConnection {
	private SSLSocket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Gson gson;

	public HospitalConnection(String host, int port) throws IOException {
		SSLSocketFactory factory = null;
		try {
			factory = getSocketFactory();
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
		this.gson = new Gson();
		this.socket = (SSLSocket) factory.createSocket(host, port);
		System.out.println("Starting handshake");
		socket.startHandshake();
		System.out.println("Handshake complete");
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream());
	}

	public String getGetResponse() {
		try {
			Request request = new Request(Request.GET_RECORDS, null);
			out.println(gson.toJson(request));
			out.flush();
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getResponse(String operation) {
		System.out.println("Sending " + operation);
		try {
			out.println(operation);
			out.flush();
			return in.readLine();
		} catch (IOException e) {
			System.out.println("Error when getting response from server " + e.getMessage());
			return null;
		}
	}

	public void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Error when closing stream " + e.getMessage());
		}
	}

	private static SSLSocketFactory getSocketFactory() throws Exception {
		char[] password = "password".toCharArray();
		KeyStore ks = KeyStore.getInstance("JKS");
		KeyStore ts = KeyStore.getInstance("JKS");
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		SSLContext ctx = SSLContext.getInstance("TLS");
		ks.load(new FileInputStream(new File("bin/clientkeystore").getAbsolutePath()), password); // keystore
																	// password
																	// (storepass)
		ts.load(new FileInputStream(new File("bin/clienttruststore").getAbsolutePath()), password); // truststore
																	// password
																	// (storepass);
		kmf.init(ks, password); // user password (keypass)
		tmf.init(ts); // keystore can be used as truststore here
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		return ctx.getSocketFactory();
	}
}
