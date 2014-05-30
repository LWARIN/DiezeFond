package fr.sma.fond.speadl;

import fr.sma.fond.speadl.observer.LogObserver;

public interface IGuiLogger extends LogObserver {
	void logInfo(String message);
	void logWarning(String message);
	void logError(String message);
}
