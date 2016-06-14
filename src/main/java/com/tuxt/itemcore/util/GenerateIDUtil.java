package com.tuxt.itemcore.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class GenerateIDUtil {
	private static AtomicInteger counter = new AtomicInteger(0);
	private static final int MAX = 9999999;
	public static String generateSequenceNo() {
		NumberFormat numberFormat = new DecimalFormat("0000000");
		final FieldPosition HELPER_POSITION = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		if (counter.get() > MAX) {
			counter.set(1);
		}
		sb.append(System.currentTimeMillis());
		numberFormat.format(counter.incrementAndGet(), sb, HELPER_POSITION);
		return sb.toString();
	}
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(generateSequenceNo());
		}
		
	}
}
