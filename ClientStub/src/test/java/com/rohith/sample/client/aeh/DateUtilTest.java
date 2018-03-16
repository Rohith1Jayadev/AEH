package com.rohith.sample.client.aeh;

import java.util.Date;

import com.rohith.app.authclient.util.AEHClientDateUtil;

public class DateUtilTest {

	public static void main(String[] args) {
		
		long currentTime = System.currentTimeMillis();
		
		System.out.println(new Date(currentTime));
		
		
		long daysADDED =  AEHClientDateUtil.addDays(30);
		
		System.out.println((long)daysADDED*1000);
		
		System.out.println(new Date(currentTime+(daysADDED*1000)));
		
		
		DateUtilTest test = null;
		
		test.print();
	}
	
	
	public void print(){
		
		System.out.println("Hella");
	}
	
}
