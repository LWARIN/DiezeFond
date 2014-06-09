package fr.sma.fond.speadl.impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import fr.sma.fond.speadl.ClockManager;
import Fond.Clock;

public class ClockImpl extends Clock {
	private Timer timer;
	
	public ClockImpl() {
		this.startScheduling(500, 500);
	}
	
	private void startScheduling(int delay, int period) {
		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				requires().log().info("ClockImpl", "Tick at " + DateFormat.getTimeInstance().format(new Date(System.currentTimeMillis())));
				requires().ecoRobot().moveRobots();
				requires().guiUpdater().refresh();
			}
		}, delay, period);
	}

	@Override
	protected ClockManager make_clockManager() {
		return new ClockManager(){

			@Override
			public void start(int period) {
				startScheduling(500, period);
			}

			@Override
			public void pause() {
				timer.cancel();
			}
			
		};
	}
}
