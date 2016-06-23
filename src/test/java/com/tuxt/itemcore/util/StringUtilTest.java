package com.tuxt.itemcore.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testTrimStringString() {
		Object actual=StringUtil.trim("|a|b|", "|");
		Object expected="a|b";
		Assert.assertEquals(expected, actual);
	}

}
