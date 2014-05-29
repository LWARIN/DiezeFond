package fr.sma.fond.speadl.impl;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import Fond.Clock;

public class ClockImpl extends Clock {
	
	private final static Logger LOGGER = Logger.getLogger(ClockImpl.class.getName());
	
	public ClockImpl() {
		startScheduling();
	}
	
	private void startScheduling() {
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				LOGGER.info("Clock tic : " + new Date(System.currentTimeMillis()));
				requires().ecoRobot().moveRobots();
				requires().guiUpdater().refresh();
			}
		}, 1000, 1000);
	}
}
