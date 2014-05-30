package fr.sma.fond.speadl.impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import Fond.Clock;

public class ClockImpl extends Clock {
	
	public ClockImpl() {
		startScheduling();
	}
	
	private void startScheduling() {
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				requires().log().info("ClockImpl", "Tick at " + DateFormat.getTimeInstance().format(new Date(System.currentTimeMillis())));
				requires().ecoRobot().moveRobots();
				requires().guiUpdater().refresh();
			}
		}, 500, 500);
	}
}
