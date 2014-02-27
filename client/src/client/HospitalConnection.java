package client;

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
import javax.swing.JOptionPane;

import resources.Identity;
import resources.Record;
import resources.Request;

import com.google.gson.Gson;

public class HospitalConnection {
	private SSLSocket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Gson gson;
	private Identity identity;

	public HospitalConnection(String host, int port, char[] pw) throws IOException {
		SSLSocketFactory factory = null;
		try {
			factory = getSocketFactory(pw);
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

		splitDN(socket.getSession().getLocalPrincipal().getName());
	}

	public String getRecordsOfPatient(String patientName) {
		try {
			Request request = new Request(Request.GET_RECORDS, patientName);
			out.println(gson.toJson(request));
			out.flush();
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	private void splitDN(String dn) {
		identity = new Identity();
		String[] params = dn.split(",");
		for (String s : params) {
			String temp = s.trim();
			String[] splitParams = temp.split("=");
			if (splitParams[0].equals(Identity.CN)) {
				identity.setCN(splitParams[1]);
			} else if (splitParams[0].equals(Identity.OU)) {
				identity.setOU(splitParams[1]);
			}
		}
	}

	public String getCN() {
		return identity.getCN();
	}

	public String getOU() {
		return identity.getOU();
	}

	public String createRecord(Record record) throws IOException {
		Request request = new Request(Request.CREATE_RECORD, gson.toJson(record));
		out.println(gson.toJson(request));
		out.flush();
		return in.readLine();
	}

	public String updateRecord(Record record) throws IOException {
		Request request = new Request(Request.UPDATE_RECORD, gson.toJson(record));
		out.println(gson.toJson(request));
		out.flush();
		return in.readLine();
	}

	public String deleteRecord(Record record) throws IOException {
		Request request = new Request(Request.DELETE_RECORD, gson.toJson(record));
		out.println(gson.toJson(request));
		out.flush();
		return in.readLine();
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

	private static SSLSocketFactory getSocketFactory(char[] pw) throws Exception {
		KeyStore ks = KeyStore.getInstance("JKS");
		KeyStore ts = KeyStore.getInstance("JKS");
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		SSLContext ctx = SSLContext.getInstance("TLS");

		File currentDirectory = new File(new File(".").getAbsolutePath());
		try {

			ks.load(new FileInputStream(currentDirectory.getCanonicalPath() + "/Desktop/EIT060/clientkeystore"), pw); // keystore
			// password
			// (storepass)
			ts.load(new FileInputStream(currentDirectory.getCanonicalPath() + "/Desktop/EIT060/clienttruststore"), pw); // truststore
			// password
			// (storepass);
			kmf.init(ks, pw); // user password (keypass)
			tmf.init(ts); // keystore can be used as truststore here
			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, currentDirectory.getCanonicalPath());
			JOptionPane.showMessageDialog(null, currentDirectory.getAbsolutePath());
		}
		return ctx.getSocketFactory();
	}

}
