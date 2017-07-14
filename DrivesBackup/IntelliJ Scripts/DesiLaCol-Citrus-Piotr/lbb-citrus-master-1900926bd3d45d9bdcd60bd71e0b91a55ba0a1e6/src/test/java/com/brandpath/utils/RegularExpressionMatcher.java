package com.brandpath.utils;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RegularExpressionMatcher extends TypeSafeMatcher {

	private final Pattern pattern;

	public RegularExpressionMatcher(String pattern) {
		this(Pattern.compile(pattern));
	}
	public RegularExpressionMatcher(Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("matches regular expression ").appendValue(pattern);
	}


	@Factory
	public static Matcher matchesPattern(Pattern pattern) {
		return new RegularExpressionMatcher(pattern);
	}
	@Factory
	public static Matcher matchesPattern(String pattern) {
		return new RegularExpressionMatcher(pattern);
	}


	@Override protected boolean matchesSafely(Object o) {
		return pattern.matcher((String)o).matches();
	}
}

