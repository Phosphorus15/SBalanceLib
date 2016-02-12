package net.steepout.balance;

import java.net.Socket;

public interface ConnectListener {
	public void onConnect(Socket connection);
}