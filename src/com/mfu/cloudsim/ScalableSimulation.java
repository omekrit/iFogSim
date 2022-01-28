package com.mfu.cloudsim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;

import com.mfu.cloudsim.constants.CloudSimConstants;
import com.mfu.cloudsim.constants.CloudletConstants;
import com.mfu.cloudsim.constants.DatacenterCharacteristicsConstants;
import com.mfu.cloudsim.constants.HostConstants;
import com.mfu.cloudsim.constants.PeConstants;
import com.mfu.cloudsim.constants.VmConstants;

public class ScalableSimulation {

	private static List<Cloudlet> cloudlets;
	private static List<Cloudlet> cloudletResults;
	private static List<Vm> virtualMachines;

	public static void main(String[] args) {
		try {
			Log.printLine("Starting the Simulation");
			CloudSimConstants cloudSimConstants = CloudSimConstants.DEFAULT;
			CloudSim.init(cloudSimConstants.AMOUNT_OF_USER, cloudSimConstants.CALENDAR_INSTANCE,
					cloudSimConstants.TRACE_FLAG);

			int datacenterId = 0;
			String datacenterName = "Datacenter_" + datacenterId;
			Datacenter datacenter = createDatacenter(datacenterName);

			String datacenterBrokerName = "Broker";
			DatacenterBroker datacenterBroker = createDatacenterBroker(datacenterBrokerName);
			int datacenterBrokerId = datacenterBroker.getId();

			virtualMachines = createVmList(datacenterBrokerId, 2);

			cloudlets = createCloudletList(datacenterBrokerId, 10);

			datacenterBroker.submitVmList(virtualMachines);
			datacenterBroker.submitCloudletList(cloudlets);

			CloudSim.startSimulation();

			cloudletResults = datacenterBroker.getCloudletReceivedList();

			CloudSim.stopSimulation();

			printCloudletList(cloudletResults);

			Log.printLine("Simulation Complete");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Datacenter createDatacenter(String name) throws Exception {
		Datacenter datacenter;
		VmAllocationPolicySimple vmAllocationPolicy;
		DatacenterCharacteristicsConstants constants = DatacenterCharacteristicsConstants.DEFAULT;
		List<Host> hosts = new ArrayList<>();
		int cpuCore = 4;
		double schedulingInterval = 0;
		List<Pe> processingElements = createPeList(cpuCore);
		List<Storage> storages = new ArrayList<>();

		int hostId = 0;
		Host host = createHost(hostId, processingElements);
		hosts.add(host);

		vmAllocationPolicy = new VmAllocationPolicySimple(hosts);

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(constants.SYSTEM_ARCHITECTURE,
				constants.OPERATING_SYSTEM, constants.VIRTUAL_MACHINE_MONITOR, hosts, constants.TIME_ZONE,
				constants.PROCESSING_COST, constants.MEMORY_COST, constants.STORAGE_COST, constants.BANDWIDTH_COST);

		datacenter = new Datacenter(name, characteristics, vmAllocationPolicy, storages, schedulingInterval);

		return datacenter;
	}

	private static DatacenterBroker createDatacenterBroker(String name) throws Exception {
		DatacenterBroker broker = new DatacenterBroker(name);
		return broker;
	}

	private static Host createHost(int hostId, List<Pe> processingElements) {
		HostConstants hostConstants = HostConstants.DEFAULT;

		VmSchedulerTimeShared vmSchedulerTimeShared = new VmSchedulerTimeShared(processingElements);
		Host host = new Host(hostId, hostConstants.RAM_PROVISIONER_SIMPLE, hostConstants.BANDWIDTH_PROVISIONER_SIMPLE,
				hostConstants.STORAGE_SIZE, processingElements, vmSchedulerTimeShared);

		return host;
	}

	private static List<Pe> createPeList(int amount) {
		List<Pe> processingELements = new ArrayList<>();
		PeConstants peConstants = PeConstants.DEFAULT;

		for (int peId = 0; peId < amount; peId++) {
			Pe pe = new Pe(peId, peConstants.PROCESSING_ELEMENT_PROVISIONER_SIMPLE);
			processingELements.add(pe);
		}

		return processingELements;
	}

	private static List<Vm> createVmList(int brokerId, int amount) {
		List<Vm> virtualMachines = new ArrayList<>();
		VmConstants vmConstants = VmConstants.LIGHT;

		for (int vmId = 0; vmId < amount; vmId++) {
			Vm vm = new Vm(vmId, brokerId, vmConstants.MILLIONS_INSTRUCTIONS_PER_SECOND,
					vmConstants.AMOUNT_OF_PROCESSING_ELEMENT, vmConstants.RAM, vmConstants.BANDWIDTH,
					vmConstants.STORAGE_SIZE, vmConstants.VIRTUAL_MACHINE_MONITOR,
					vmConstants.CLOUDLET_SCHEDULER_TIME_SHARED);
			virtualMachines.add(vm);
		}
		return virtualMachines;
	}

	private static List<Cloudlet> createCloudletList(int brokerId, int amount) {
		List<Cloudlet> cloudlets = new ArrayList<>();
		CloudletConstants cloudletConstants = CloudletConstants.DEFAULT;

		for (int cloudletId = 0; cloudletId < amount; cloudletId++) {
			Cloudlet cloudlet = new Cloudlet(cloudletId, cloudletConstants.LENGTH,
					cloudletConstants.AMOUNT_OF_PROCESSING_ELEMENT, cloudletConstants.FILE_SIZE,
					cloudletConstants.OUTPUT_SIZE, cloudletConstants.UTILIZATION_MODEL,
					cloudletConstants.UTILIZATION_MODEL, cloudletConstants.UTILIZATION_MODEL);
			cloudlet.setUserId(brokerId);
			cloudlets.add(cloudlet);
		}

		return cloudlets;
	}

	private static void printCloudletList(List<Cloudlet> receivedCloudlets) {
		int size = receivedCloudlets.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent + "Data center ID" + indent + "VM ID" + indent + indent
				+ "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int cloudletId = 0; cloudletId < size; cloudletId++) {
			cloudlet = receivedCloudlets.get(cloudletId);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				Log.print("SUCCESS");

				Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId()
						+ indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent
						+ dft.format(cloudlet.getExecStartTime()) + indent + indent + indent
						+ dft.format(cloudlet.getFinishTime()));
			}
		}

	}
}
