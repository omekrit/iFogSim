package com.mfu.cloudsim.constants;

import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;

public enum CloudletConstants {
	DEFAULT(400_000, 300, 300);

	public final long LENGTH;
	public final long FILE_SIZE;
	public final long OUTPUT_SIZE;
	public final int AMOUNT_OF_PROCESSING_ELEMENT = 1;
	public final UtilizationModel UTILIZATION_MODEL = new UtilizationModelFull();

	private CloudletConstants(long length, long fizeSize, long outputSize) {
		LENGTH = length;
		FILE_SIZE = fizeSize;
		OUTPUT_SIZE = outputSize;
	}
}
