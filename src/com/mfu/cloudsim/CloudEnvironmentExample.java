package com.mfu.cloudsim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class CloudEnvironmentExample {
	public static void main(String[] args) {
		int totalUser = 1;
		Calendar calendar = Calendar.getInstance();
		boolean traceFlag = true;

		CloudSim.init(totalUser, calendar, traceFlag);
		Datacenter datacenter = createDatacenter("Datacenter");
		DatacenterBroker broker = createBroker();
		int brokerId = broker.getId();

		List<Vm> vmList = new ArrayList<>();
		int vmId = 0;
		int mips = 250;
		long size = 10_000;
		int ram = 512;
		long bw = 1000;
		int pesNumber = 1;
		String vmm = "Xen";

		Vm vm1 = new Vm(vmId, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
		vmList.add(vm1);

		broker.submitVmList(vmList);

		List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();
		// Cloudlet properties
		int id = 0;
		long length = 400000;
		long fileSize = 300;
		long outputSize = 300;
		UtilizationModel utilizationModel = new UtilizationModelFull();
		Cloudlet cloudlet1 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel,
				utilizationModel, utilizationModel);
		Cloudlet cloudlet2 = new Cloudlet(id + 1, length, pesNumber, fileSize, outputSize, utilizationModel,
				utilizationModel, utilizationModel);
		Cloudlet cloudlet3 = new Cloudlet(id + 2, length, pesNumber, fileSize, outputSize, utilizationModel,
				utilizationModel, utilizationModel);

		cloudlet1.setUserId(brokerId);
		cloudlet1.setVmId(vmId);
		cloudletList.add(cloudlet1);

		cloudlet2.setUserId(brokerId);
		cloudlet2.setVmId(vmId);
		cloudletList.add(cloudlet2);

		cloudlet3.setUserId(brokerId);
		cloudlet3.setVmId(vmId);
		cloudletList.add(cloudlet3);
		
		broker.submitCloudletList(cloudletList);

		// Start the simulation
		CloudSim.startSimulation();

		CloudSim.stopSimulation();

		// Print results when simulation is over
		List<Cloudlet> newList = broker.getCloudletReceivedList();
		printCloudletList(newList);

	}

	private static Datacenter createDatacenter(String name) {
		int hostId = 0;
		int peId = 0;
		int ram = 2048;
		long storage = 1_000_000;
		int bandwidth = 10_000;
		int mips = 1000;

		List<Host> hostList = new ArrayList<>();
		List<Pe> peList = new ArrayList<>();
		LinkedList<Storage> storageList = new LinkedList<>();

		Pe pe = new Pe(peId, new PeProvisionerSimple(mips));
		peList.add(pe);

		Host host = new Host(hostId, new RamProvisionerSimple(ram), new BwProvisionerSimple(bandwidth), storage, peList,
				new VmSchedulerTimeShared(peList));
		hostList.add(host);

		String systemArchitecture = "x86";
		String os = "Linux";
		String hypervisor = "Xen";
		double timeZone = 10.0;
		double processingCost = 3.0;
		double memoryCost = 0.05;
		double storageCost = 0.001;
		double bandwidthCost = 0.0;

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(systemArchitecture, os, hypervisor,
				hostList, timeZone, processingCost, memoryCost, storageCost, bandwidthCost);

		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;

	}

	private static DatacenterBroker createBroker() {
		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return broker;
	}

	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent + "Data center ID" + indent + "VM ID" + indent + "Time"
				+ indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				Log.print("SUCCESS");

				Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId()
						+ indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent
						+ dft.format(cloudlet.getExecStartTime()) + indent + indent
						+ dft.format(cloudlet.getFinishTime()));
			}
		}
	}

}
