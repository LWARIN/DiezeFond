package fr.sma.speadl.impl;

import fr.sma.core.Cell;
import fr.sma.speadl.ActionHandler;
import fr.sma.speadl.MemoryHandler;
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

					@Override
					public Cell[][] getMemorizedInformation(int x, int y) {
						return null;
					}
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
