package fr.sma.speadl.impl;

import DiezeFond.Ecosystem;

public class EcosystemImpl extends Ecosystem {

	@Override
	protected Robot make_Robot() {
		return new RobotImpl();
	}

	@Override
	protected Environment make_Environment() {
		return new EnvironmentImpl();
	}

	@Override
	protected GUI make_GUI() {
		return new GuiImpl();
	}

}
