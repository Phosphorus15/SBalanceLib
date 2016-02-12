package net.steepout.balance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class BalanceServer {
	List<InetSocketAddress> subserver;

	String pwd;

	public BalanceServer(List<InetSocketAddress> sub, String pwd) {
		if (sub != null) {
			for (InetSocketAddress x : sub) {
				if (!addSubserver(x)) {
					System.out.println("invalid subserver " + x.getAddress().getHostAddress());
				}
			}
		}
		this.pwd = pwd;
	}

	public List<Client> listClient() {
		List<Client> pre = new LinkedList<Client>();
		for (InetSocketAddress x : subserver) {
			Client c = particleInformation(x);
			if (c != null) {
				pre.add(c);
			}
		}
		return pre;
	}
	
	public Socket createSocketFor(Client client){
		return createSocketByAddress(client.getAddress());
	}
	
	private Socket createSocketByAddress(InetSocketAddress client){
		try {
			Socket socket = fastSocket(client);
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println("attemp_connect");
			return socket;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public Socket getBestClient(SelectAlgorithm algorithm) {
		if(algorithm == null)
			return null;
		InetSocketAddress result = algorithm.getBestSelection(listClient());
		if (result == null) {
			return null;
		} else {
			return createSocketByAddress(result);
		}
	}

	private Client particleInformation(InetSocketAddress ads) {
		try {
			Socket socket = fastSocket(ads);
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader reader = fastReader(socket);
			writer.println(pwd);
			writer.println("full_info");
			String result = reader.readLine();
			Client rt = new Client(ads, Long.parseLong(result.split("/")[1]), Long.parseLong(result.split("/")[0]));
			return rt;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean addSubserver(InetSocketAddress ads) {
		try {
			Socket socket = fastSocket(ads);
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			writer.print(pwd);
			writer.println("ping");
			if (fastReader(socket).readLine().equals("alive")) {
				socket.close();
				return true;
			} else {
				socket.close();
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	private Socket fastSocket(InetSocketAddress ads) throws IOException {
		return new Socket(ads.getAddress(), ads.getPort());
	}

	private BufferedReader fastReader(Socket socket) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

}