package com.lft;

public class Timer {
	private long startTime;
	private long stopTime;
	private boolean isRunning;
		
	public Timer() {
		startTime = 0;
		stopTime = 0;
		isRunning = false;
	}
		
	public void start() {
		isRunning = true;
		startTime = System.nanoTime();
	}
		
	public void stop() {
		isRunning = false;
		stopTime = System.nanoTime();
	}
		
	public long getElapsedTime() {
		if (isRunning) {
			return System.nanoTime() - startTime;
		} else {
			return stopTime - startTime;
		}
	}
}
