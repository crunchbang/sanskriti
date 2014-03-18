package com.crunchbang.sanskriti.helper;

public class EventResult {
	private String event;
	private String first;
	private String second;
	private String third;

	public EventResult(String event, String first, String second, String third) {
		this.event = event;
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public String getThird() {
		return third;
	}

	public void setThird(String third) {
		this.third = third;
	}

	@Override
	public String toString() {
		return event;
	}

}
