package io.mhe.assignmentcomponent.vo;

public class Marathon implements Model {
	private static final long	serialVersionUID	= 9149098819762160411L;

	private long				marathonId			= 0l;
	private String				marathonTitle;
	private long				sectionId;
	private long				userId;

	/**
	 * @return the marathonId
	 */
	public long getMarathonId() {
		return marathonId;
	}

	/**
	 * @param marathonId the marathonId to set
	 */
	public void setMarathonId(long marathonId) {
		this.marathonId = marathonId;
	}

	/**
	 * @return the marathonTitle
	 */
	public String getMarathonTitle() {
		return marathonTitle;
	}

	/**
	 * @param marathonTitle the marathonTitle to set
	 */
	public void setMarathonTitle(String marathonTitle) {
		this.marathonTitle = marathonTitle;
	}

	/**
	 * @return the sectionId
	 */
	public long getSectionId() {
		return sectionId;
	}

	/**
	 * @param sectionId the sectionId to set
	 */
	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
    //SONAR-OFF
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }			
		if (o == null || getClass() != o.getClass()) {
            return false;
        }
			
		Marathon marathon = (Marathon) o;

		if (marathonId != marathon.marathonId) {
            return false;
        }			
		if (sectionId != marathon.sectionId) {
            return false;
        }			
		if (userId != marathon.userId) {
            return false;
        }			
		if (marathonTitle != null ? !marathonTitle.equals(marathon.marathonTitle) : marathon.marathonTitle != null) {
            return false;
        }			

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (marathonId ^ (marathonId >>> 32));
		result = 31 * result + (marathonTitle != null ? marathonTitle.hashCode() : 0);
		result = 31 * result + (int) (sectionId ^ (sectionId >>> 32));
		result = 31 * result + (int) (userId ^ (userId >>> 32));
		return result;
	}
    //SONAR-ON

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Marathon : {");
        sb.append("marathonID : ");
        sb.append(marathonId);
        sb.append(",");
        sb.append("marathonTitle : ");
        sb.append(marathonTitle);
        sb.append(",");
        sb.append("sectionId : ");
        sb.append(sectionId);
        sb.append(",");
        sb.append("userId : ");
        sb.append(userId);
        sb.append("}");
		return sb.toString();
    }
}
