package fr.sma.core;

import java.util.UUID;

public class Robot implements Agent, IRobot {
	
	private UUID id = UUID.randomUUID();
	private boolean alive = true;

	@Override
	public void run() {
		System.out.println(id.toString() + ": I'm born");
		while (alive) {
			try {
				System.out.println(id.toString() + ": I'm alive");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				alive = false;
			}
		}
		System.out.println(id.toString() + ": I'm dead");
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

}
