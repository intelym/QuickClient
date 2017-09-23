package com.intelym.quick.java.client.services;

public class Configuration {

	public String SERVER_ADDRESS, MULTICAST_GROUP_ADDRESS;

	public int SERVER_PORT, MULTICAST_PORT;

	public String USER_NAME, PASSWORD;

	public int RECEIVE_TIMEOUT = 5000;
	public static final String DOMAIN = "icicidirect";

	public static final int QUOTE = 101, MDEPTH = 102, INDEX = 103, NEWS = 104,
			GENERALBCAST = 105, TIME = 106, BROKERALERTS = 107, LINKNEWS = 108,
			LOGIN = 11, ANONYMOUS_LOGIN = 999, ADDSCRIP = 81, DELETESCRIP = 83,
			ADDSCRIPS = 82, DELETESCRIPTS = 84, ADDMDPETH = 86,
			DELETEMDEPTH = 87, ADDDERIVATIVECHAIN = 91,
			DELETEDERIVATIVECHAIN = 92, UNSOLICITED = 122, ADDFUTURECHAIN = 93,
			ADDOPTIONCHAIN = 94, DELETEFUTURECHAIN = 95,
			DELETEOPTIONCHAIN = 96;

	public static final int NSE = 0, BSE = 1, NSEFO = 2, NSECUR = 3, MCX = 4,
			NCDEX = 5, BSEFO = 6, INDEX_EXCHANGE = 11;

	public boolean LOG_ENABLED = false;
	public String LOG_PATH = "";
}
