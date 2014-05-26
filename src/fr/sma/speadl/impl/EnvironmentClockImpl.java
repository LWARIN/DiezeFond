package fr.sma.speadl.impl;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import DiezeFond.EnvironmentClock;

public class EnvironmentClockImpl extends EnvironmentClock {

	public EnvironmentClockImpl() {
		
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Clock tic : " + new Date(System.currentTimeMillis()));
				requires().moveTrigger().moveAgents();
				requires().renderEnvironment().refresh();
			}
		}, 3000, 3000);
	}
}
