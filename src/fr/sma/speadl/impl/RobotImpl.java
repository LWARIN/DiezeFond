package fr.sma.speadl.impl;

import java.util.logging.MemoryHandler;

import fr.sma.speadl.ActionHandler;
import DiezeFond.EcoEnvironment.Robot;
import DiezeFond.RobotActionManager;
import DiezeFond.RobotMemory;

public class RobotImpl extends Robot {

	public RobotImpl(String id) {
		System.out.println("ID ROBOT : " + id);
	}

	@Override
	protected RobotMemory make_memory() {
		return new RobotMemory() {

			@Override
			protected MemoryHandler make_memoryHandler() {
				return new MemoryHandler() {
					
				};
			}
			
		};
	}

	@Override
	protected RobotActionManager make_actionManager() {
		return new RobotActionManager() {

			@Override
			protected ActionHandler make_actionHandler() {
				return new ActionHandler() {
					
				};
			}
			
		};
	}

}
