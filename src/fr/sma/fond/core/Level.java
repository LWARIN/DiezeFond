package fr.sma.fond.core;

public enum Level {
	INFO("info"), WARNING("warning"), ERROR("error");

	private final String value;

	Level(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static Level parseValue(String value) {
		for (Level level : Level.values()) {
			if (level.value.equals(value)) {
				return level;
			}
		}
		throw new IllegalArgumentException("'" + value
				+ "' is not a valid level");
	}
}
