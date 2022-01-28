package com.mfu.cloudsim.constants;

import java.util.Calendar;

public enum CloudSimConstants {
	DEFAULT(1, false);

	public final int AMOUNT_OF_USER;
	public final Calendar CALENDAR_INSTANCE = Calendar.getInstance();
	public final boolean TRACE_FLAG;

	private CloudSimConstants(int amountOfUser, boolean traceFlag) {
		AMOUNT_OF_USER = amountOfUser;
		TRACE_FLAG = traceFlag;
	}
}
