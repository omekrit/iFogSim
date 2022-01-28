package com.mfu.cloudsim.constants;

import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;

public enum VmConstants {
	DEFAULT(1, 1_000, 512), LIGHT(2, 250, 1_024), MEDIUM(4, 500, 2_048), HEAVY(8, 1_000, 4_096);

	public final int MILLIONS_INSTRUCTIONS_PER_SECOND;
	public final int AMOUNT_OF_PROCESSING_ELEMENT;
	public final int RAM;
	public final String VIRTUAL_MACHINE_MONITOR = "Xen";
	public final long STORAGE_SIZE = 10_000;
	public final int BANDWIDTH = 1_000;
	public final CloudletSchedulerTimeShared CLOUDLET_SCHEDULER_TIME_SHARED = new CloudletSchedulerTimeShared();

	private VmConstants(int pesNumber, int mips, int ram) {
		AMOUNT_OF_PROCESSING_ELEMENT = pesNumber;
		MILLIONS_INSTRUCTIONS_PER_SECOND = mips;
		RAM = ram;
	}

}
