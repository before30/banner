package me.home.banner;

import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class DefaultBanner implements Banner {

	private static final String[] BANNER = {
			"\\ \\   / /(_)| |_  __ _  _ __ ___  (_) _ __  \n" +
			" \\ \\ / / | || __|/ _` || '_ ` _ \\ | || '_ \\ \n" +
			"  \\ V /  | || |_| (_| || | | | | || || | | |\n" +
			"   \\_/   |_| \\__|\\__,_||_| |_| |_||_||_| |_|\n" +
			"                                            "
			};


	@Override
	public void printBanner(Environment environment, PrintStream printStream) {
		for (String line : BANNER) {
			printStream.println(line);
		}

		printStream.println();
	}

}
