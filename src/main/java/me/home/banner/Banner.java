package me.home.banner;

import org.springframework.core.env.Environment;

import java.io.PrintStream;

public interface Banner {

	void printBanner(Environment environment, PrintStream out);

	/**
	 * An enumeration of possible values for configuring the Banner.
	 */
	enum Mode {

		/**
		 * Disable printing of the banner.
		 */
		OFF,

		/**
		 * Print the banner to System.out.
		 */
		CONSOLE,

		/**
		 * Print the banner to the log file.
		 */
		LOG

	}

}
