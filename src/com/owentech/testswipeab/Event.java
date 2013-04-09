package com.owentech.testswipeab;

public class Event {
	private String schedule_Id;
	private String schedule_mob_Id;
	private String name;
	
	public String getSchedule_Id() {
		return schedule_Id;
	}
	public void setSchedule_Id(String schedule_Id) {
		this.schedule_Id = schedule_Id;
	}
	public String getSchedule_mob_Id() {
		return schedule_mob_Id;
	}
	public void setSchedule_mob_Id(String schedule_mob_Id) {
		this.schedule_mob_Id = schedule_mob_Id;
	}
	public void setName(String name){
		this.name = name;
	}	
	public String getName(){
		return name;
	}
}
