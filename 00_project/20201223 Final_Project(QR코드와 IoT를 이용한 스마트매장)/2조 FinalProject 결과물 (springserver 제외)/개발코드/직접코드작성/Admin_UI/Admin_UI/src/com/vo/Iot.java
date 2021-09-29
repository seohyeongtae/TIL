package com.vo;

public class Iot {
	
	private int light;
	private int door;
	private int temperature;
	public Iot() {
		super();
	}
	public Iot(int light, int door, int temperature) {
		super();
		this.light = light;
		this.door = door;
		this.temperature = temperature;
	}
	public Iot(int light, int door) {
		super();
		this.light = light;
		this.door = door;
	}
	public int getLight() {
		return light;
	}
	public void setLight(int light) {
		this.light = light;
	}
	public int getDoor() {
		return door;
	}
	public void setDoor(int door) {
		this.door = door;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	@Override
	public String toString() {
		return "Iot [light=" + light + ", door=" + door + ", temperature=" + temperature + "]";
	}
	
	
	
}
