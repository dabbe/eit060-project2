package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.security.cert.X509Certificate;

import resources.Identity;
import resources.Request;

import com.google.gson.Gson;

public class ConnectionThread extends Thread {
	private SSLSocket socket;
	private Monitor monitor;
	private Identity identity;
	
	private Gson gson;

	public ConnectionThread(SSLSocket socket, Monitor monitor) throws IOException {
		this.socket = socket;
		this.monitor = monitor;
		this.gson = new Gson();
		
		SSLSession session = socket.getSession();
		X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
		splitDN(cert.getSubjectDN().getName());
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

	public void run() {
		try {
			PrintWriter out = null;
			BufferedReader in = null;
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String clientMsg = null;
			while ((clientMsg = in.readLine()) != null) {
				Scanner scan = new Scanner(clientMsg);
//				String op = scan.next();
//				System.out.println("Received " + op);
				
				Request request = gson.fromJson(clientMsg, Request.class);
				int type = request.getType();
				
				switch(type){
				case Request.GET_RECORDS:
					out.println(gson.toJson(monitor.getRecords(identity)));
					break;
				case Request.CREATE_RECORD:
					Record createRecord = gson.fromJson(request.getData(), Record.class);
					out.println(monitor.createRecord(identity, createRecord));
					break;
				case Request.UPDATE_RECORD:
					Record updateRecord = gson.fromJson(request.getData(), Record.class);
			//		monitor.updateRecord(record);
					break;
				}
				
				
				/*
				if (op.equals("GET")) {
					out.println(gson.toJson(monitor.getRecords(CN, OU)));
				} else if (op.equals("PUT")) {
					out.println("TUP");
				} else if (op.equals("ADD")) {
//					monitor.createPatient();
					out.println("DDA");
				} else if (op.equals("DELETE")) {
//					monitor.delete();
					out.println("ETELED");
				} else {
					System.out.println("Unrecognized operation " + op);
					out.println("IDUNNOLOL");
				}*/
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
