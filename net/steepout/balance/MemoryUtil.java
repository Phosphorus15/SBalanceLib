package net.steepout.balance;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class MemoryUtil {
	OperatingSystemMXBean bean;

	public MemoryUtil() {
		bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	}

	public long getTotal() {
		return bean.getTotalPhysicalMemorySize();
	}

	public long getFree() {
		return bean.getFreePhysicalMemorySize();
	}

	public static void main(String[] args) {
		MemoryUtil util = new MemoryUtil();
		System.out.println(util.getFree() / 1024 / 1024);
	}
}