package net.steepout.balance;

import java.net.InetSocketAddress;
import java.util.List;

public class SimilarBestAlgorithm implements SelectAlgorithm {
	public static final int BIGGER_ONLY = 1;
	public static final int NO_MATTER_BIGGER_OR_EQUAL = 2;
	public static final int NO_MATTER_BIGGER_OR_SMALLER = 3;
	int mode = BIGGER_ONLY;
	long target;

	public SimilarBestAlgorithm(long target) {
		this.target = target;
	}

	public SimilarBestAlgorithm(long target, int workmode) {
		this.target = target;
		this.mode = workmode;
	}

	@Override
	public InetSocketAddress getBestSelection(List<Client> address) {
		// TODO Auto-generated method stub
		long least = 0xffffffffffffffffl;
		InetSocketAddress result = null;
		for (Client x : address) {
			long tmp = x.getFree() - target;
			if (tmp == 0) {
				if (mode < 2)
					continue;
				else {
					least = 0;
					result = x.getAddress();
					break;
				}
			} else if (tmp < 0) {
				if (mode < 3) {
					continue;
				} else {
					tmp = -tmp;
					if (tmp < least) {
						least = tmp;
					}
					result = x.getAddress();
				}
			} else {
				if (tmp < least) {
					least = tmp;
					result = x.getAddress();
				}
			}
		}
		return result;
	}

}