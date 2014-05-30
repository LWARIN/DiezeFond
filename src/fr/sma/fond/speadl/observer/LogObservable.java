package fr.sma.fond.speadl.observer;

import fr.sma.fond.core.Level;

public interface LogObservable {
	void addObserver(LogObserver obs);
	void notifyObserver(Level level, String message);
}
