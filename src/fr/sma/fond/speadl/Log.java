package fr.sma.fond.speadl;

import java.util.List;

public interface Log {

	public void info(String source, String message);

	public void warning(String source, String message);

	public void error(String source, String message);
	
	public List<String> getLogs();
}
