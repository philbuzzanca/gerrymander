package com.orioles.constants;

public class Constants {
	// FRONT END DISPLAY TEXT
	public static final String HELP = "<p>To begin using this tool, select a state either by clicking on the state " +
			"on the map or by selecting it from the dropdown menu.</p>\n<p>Once a state has been selected, specify " +
			"any constraints that the district generation should adhere to by toggling the checkboxes on the sidebar." +
			" After selecting the constraints, you can generate congressional districts by clicking on the " +
			"<strong>Build</strong> button.</p>\n";
	public static final String ABOUT = "<p>\"A Gerrymander is a voting district that is designed to serve some " +
			"political purpose. The name refers to both a salamander and Eldridge Gerry, whose newly created voting " +
			"district about 200 years ago was said to resemble a salamander. Within the past 10 years, databases for " +
			"voter characterization as well as tools for precise map drawing have made it possible to create " +
			"congressional districts that favor the party responsible for the creation of the districts. " +
			"Redistricting is done in states where census data requires a change in the number of delegates in the " +
			"state, and the 2010 census triggered redistricting in a number of states. Many of these redistricting " +
			"efforts resulted in a shift in the political representation in the states. As the realization of the " +
			"impact of these changes has grown, various technical approaches to the issue have been proposed, some " +
			"as quantitative measures of the presence of Gerrymandering, others as legal challenges to redistricting," +
			" and yet others as draft bills in Congress to minimize the effect of future redistricting. The system to" +
			" be developed in this project will allow for the generation of congressional district boundaries without" +
			" any political influence.\"</p>";

	// STATES
	public static final String VA = "VIRGINA";
	public static final String NM = "NEW MEXICO";
	public static final String UT = "UTAH";

	// ALGORITHM
	public static final int MAX_ITERATIONS = 100;
	public static final double EARTH_RADIUS = 6371.01;
	public static final double GERRYMANDERING_THRESHOLD = 0.2;

	// AlgoController Parser
	public static final String MEASURE = "measure";
	public static final int MEASURE_LENGTH = MEASURE.length();
	public static final String CONSTRIANT = "constraint";
	public static final int CONSTRAINT_LENGTH = CONSTRIANT.length();
	public static final String STATE = "state";

	// StateManager / StateController
	public static final String GEOMETRY = "geometry";
	public static final String NEIGHBORS = "NEIGHBORS";
	public static final String COORDINATES = "coordinates";
	public static final String PROPERTIES = "properties";

	// COLOR
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
}
