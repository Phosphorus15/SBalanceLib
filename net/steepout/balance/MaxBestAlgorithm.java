package net.steepout.balance;

import java.net.InetSocketAddress;
import java.util.List;

public class MaxBestAlgorithm implements SelectAlgorithm{
	
	long least = 0;
	
	public MaxBestAlgorithm(){
		
	}
	
	public MaxBestAlgorithm(long least){
		this.least = least;
	}

	@Override
	public InetSocketAddress getBestSelection(List<Client> address) {
		// TODO Auto-generated method stub
		long max = least;
		InetSocketAddress result = null;
		for(Client x:address){
			if(x.getFree()>max){
				max = x.getFree();
				result = x.getAddress();
			}
		}
		return result;
	}
	
}