package net.steepout.balance;

import java.net.InetSocketAddress;
import java.util.List;

public interface SelectAlgorithm{
	public InetSocketAddress getBestSelection(List<Client> address);
}