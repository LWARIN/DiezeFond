package fr.sma.fond.speadl;

import fr.sma.fond.speadl.observer.LogObservable;

public interface Log extends LogObservable {

	public void info(String source, String message);

	public void warning(String source, String message);

	public void error(String source, String message);
}
