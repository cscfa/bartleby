package org.bartelby.ressources;

public interface StringRessource {

	/* ---- Configuration ---- */
	public static final String DEFAULT_CONFIGURATION_ABSOLUTE_PATH = "/opt/bartelby/config";
	public static final String DEFAULT_CONFIGURATION_RELATIVE_PATH = "config";
	public static final String DEFAULT_CONFIGURATION_IMPORT_FILE_DEFINER = "imports.yaml";

	/* ---- Logger configuration ---- */
	public static final String DEFAULT_LOGGER_CONFIGURATION_FILE_NAME = "logback.xml";

	/* ---- Configuration parameter registration ---- */
	public static final String DEFAULT_SERVER_SPACE = "server";

	/* ---- Configuration service registration ---- */
	public static final String SERVICE_LOGGER = "logger";
	public static final String SERVICE_CONSOLE = "console";

	/* ---- Configuration status registration ---- */
	public static final String DEFAULT_STATUS_SPACE = "status";
	public static final String DEFAULT_STATUS_SPACE_STARTED = "started";
	public static final String DEFAULT_STATUS_SPACE_WAITING = "waiting";
	public static final String DEFAULT_STATUS_SPACE_STOP = "stop";

	/* ---- Configuration security registration ---- */
	public static final String DEFAULT_SECURITY_USER_URL_PARAM = "username";
	public static final String DEFAULT_SECURITY_USER_KEY_URL_PARAM = "userkey";
	
}
