package fr.sma.fond.speadl.impl;

import java.util.ArrayList;
import java.util.List;

import fr.sma.fond.speadl.Log;
import Fond.Logger;

public class LoggerImpl extends Logger {

	@Override
	protected Log make_log() {
		return new Log() {
			
			private List<String> logs = new ArrayList<String>();
			
			@Override
			public void info(String source, String message) {
				logs.add("[INFO] - " + source + " : " + message);
			}

			@Override
			public void warning(String source, String message) {
				logs.add("[WARNING] - " + source + " : " + message);
			}

			@Override
			public void error(String source, String message) {
				logs.add("[SUCCESS] - " + source + " : " + message);
			}

			@Override
			public List<String> getLogs() {
				return logs;
			}

		};
	}

}
