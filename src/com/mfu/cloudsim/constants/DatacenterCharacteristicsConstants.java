package com.mfu.cloudsim.constants;

public enum DatacenterCharacteristicsConstants {
	NO_COST(0.0, 0.0, 0.0, 0.0), DEFAULT(3.0, 0.05, 0.001, 0.0), HIGH_COST(10.0, 5.0, 1.0, 1.0);

	public final String SYSTEM_ARCHITECTURE = "x86";
	public final String OPERATING_SYSTEM = "Linux";
	public final String VIRTUAL_MACHINE_MONITOR = "Xen";
	public final double TIME_ZONE = 7.0;
	
	public final double PROCESSING_COST;
	public final double MEMORY_COST;
	public final double STORAGE_COST;
	public final double BANDWIDTH_COST;

	private DatacenterCharacteristicsConstants(double processingCost, double memoryCost, double storageCost,
			double bandwidthCost) {
		PROCESSING_COST = processingCost;
		MEMORY_COST = memoryCost;
		STORAGE_COST = storageCost;
		BANDWIDTH_COST = bandwidthCost;
	}
}
