package com.mfu.fog;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;
import org.fog.application.Application;
import org.fog.entities.FogBroker;

import com.mfu.cloudsim.constants.CloudSimConstants;

public class FogEnvironment {

	private static List<Application> applications = new ArrayList<>();

	public static void main(String[] args) {
		Log.printLine("Starting Fog Environment Simulation");
		try {
			CloudSimConstants cloudSimConstants = CloudSimConstants.DEFAULT;
			CloudSim.init(cloudSimConstants.AMOUNT_OF_USER, cloudSimConstants.CALENDAR_INSTANCE,
					cloudSimConstants.TRACE_FLAG);

			FogBroker broker = new FogBroker("broker");
			String appId = "task_graph_scheduling";
			Application application = Application.createApplication(appId, broker.getId());
			application.setUserId(broker.getId());

			applications.add(application);

			CloudSim.startSimulation();

			CloudSim.stopSimulation();

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.printLine("Simulation Complete");
	}

}
