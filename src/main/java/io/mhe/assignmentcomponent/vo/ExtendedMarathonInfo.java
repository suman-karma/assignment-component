package io.mhe.assignmentcomponent.vo;

import java.util.List;

public class ExtendedMarathonInfo extends MarathonInfo implements Model {
	private static final long	serialVersionUID	= 9149098819062160411L;
	
	private List<Marathon> marathon =null;
	
	/**
	 * @return getMarathon
	 */
	public List<Marathon> getMarathon() {
		return marathon;
	}
	
	/**
	 * @param setMarathon the setMarathon to set
	 */
	public void setMarathon(List<Marathon> marathon) {
		this.marathon = marathon;
	}
	
}
	

