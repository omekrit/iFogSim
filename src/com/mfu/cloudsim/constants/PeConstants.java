package com.mfu.cloudsim.constants;

import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;

public enum PeConstants {
	DEFAULT(1_000);
	
	public final int MILLIONS_INSTRUCTIONS_PER_SECOND;
	public final PeProvisionerSimple PROCESSING_ELEMENT_PROVISIONER_SIMPLE;
	
	private PeConstants(int mips) {
		MILLIONS_INSTRUCTIONS_PER_SECOND = mips;
		PROCESSING_ELEMENT_PROVISIONER_SIMPLE = new PeProvisionerSimple(mips);
	}
}
