package net.steepout.balance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BalanceClient {

	ServerSocket server;

	Thread thread;

	String pwd;

	boolean stopped = false;

	MemoryUtil util = new MemoryUtil();

	ConnectListener listener = null;

	public BalanceClient(int port, String pwd) throws IOException {
		server = new ServerSocket(port);
		this.pwd = pwd;
	}

	public void setListener(ConnectListener l) {
		listener = l;
	}

	public void start() {
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!stopped) {
					try {
						Socket tmp = server.accept();
						BufferedReader reader = new BufferedReader(new InputStreamReader(tmp.getInputStream()));
						String request = reader.readLine();
						if (request == null || !request.equals(pwd)) {
							tmp.close();
							continue;
						}
						PrintWriter writer = new PrintWriter(tmp.getOutputStream(), true);
						if (request.equalsIgnoreCase("ask_free")) {
							writer.write(util.getFree() + "");
							tmp.close();
						}
						if (request.equalsIgnoreCase("ping")) {
							writer.write("alive");
							tmp.close();
						} else if (request.equalsIgnoreCase("ask_total")) {
							writer.write(util.getTotal() + "");
							tmp.close();
						} else if (request.equalsIgnoreCase("full_info")) {
							writer.write(util.getFree() + "/" + util.getTotal());
						} else if (request.equalsIgnoreCase("attemp_connect")) {
							if (listener != null) {
								listener.onConnect(tmp);
							} else {
								tmp.close();
							}
						} else {
							tmp.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
	}

}