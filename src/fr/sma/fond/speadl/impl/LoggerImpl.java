package fr.sma.fond.speadl.impl;

import java.util.ArrayList;
import java.util.List;

import fr.sma.fond.core.Level;
import fr.sma.fond.speadl.Log;
import fr.sma.fond.speadl.observer.LogObserver;
import Fond.Logger;

public class LoggerImpl extends Logger {

	@Override
	protected Log make_log() {
		return new Log() {
			
			private List<LogObserver> observerList = new ArrayList<>();
			
			@Override
			public void info(String source, String message) {
				String fullMessage = source + " : " + message;
				notifyObserver(Level.INFO, fullMessage);
			}

			@Override
			public void warning(String source, String message) {
				String fullMessage = source + " : " + message;
				notifyObserver(Level.WARNING, fullMessage);
			}

			@Override
			public void error(String source, String message) {
				String fullMessage = source + " : " + message;
				notifyObserver(Level.ERROR, fullMessage);
			}

			@Override
			public void addObserver(LogObserver obs) {
				observerList.add(obs);
			}

			@Override
			public void notifyObserver(Level level, String message) {
				for (LogObserver obs : observerList) {
					obs.update(level, message);
				}
			}

		};
	}

}
