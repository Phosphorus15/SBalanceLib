package net.steepout.balance;

import java.net.InetSocketAddress;

public class Client {
	long total;
	long free;
	InetSocketAddress address;

	public Client(InetSocketAddress ads, long total, long free) {
		this.total = total;
		this.free = free;
		address = ads;
	}

	public long getTotal() {
		return total;
	}

	public long getFree() {
		return free;
	}

	public InetSocketAddress getAddress() {
		return address;
	}
}