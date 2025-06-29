package kms.service;

import java.util.Random;

public class QuoteService {
	    private static final String[] QUOTES = {
	        "Education is the most powerful weapon you can use to change the world.",
	        "Teaching is the one profession that creates all other professions.",
	        "To teach is to touch a life forever.",
	        "Through your wisdom, you shape the ummah."
	    };

	    public static String getRandomQuote(int adminId) {
	        int index = new Random().nextInt(QUOTES.length);
	        return QUOTES[index];
	    }
	}
