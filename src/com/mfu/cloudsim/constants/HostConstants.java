package com.mfu.cloudsim.constants;

import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public enum HostConstants {
	DEFAULT(2_048), MEDIUM(4_096), HEAVY(8_192);

	public final long STORAGE_SIZE = 1_000_000;
	public final int BANDWIDTH = 10_000;
	public final RamProvisionerSimple RAM_PROVISIONER_SIMPLE;
	public final BwProvisionerSimple BANDWIDTH_PROVISIONER_SIMPLE;

	private HostConstants(int ram) {
		RAM_PROVISIONER_SIMPLE = new RamProvisionerSimple(ram);
		BANDWIDTH_PROVISIONER_SIMPLE = new BwProvisionerSimple(BANDWIDTH);
	}

}
