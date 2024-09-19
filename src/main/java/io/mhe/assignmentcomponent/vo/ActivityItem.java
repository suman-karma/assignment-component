package io.mhe.assignmentcomponent.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityItem implements Model {

    protected long id = 0L;
    protected long alaManagerID = 0L;
    protected float weight = 0F;
    protected long sequenceNo = 0L;
    protected long[] skillCategoryIds = null;
    protected Date updatedDate = new Date();
    protected Date createdDate = new Date();
    protected long activityId = 0L;
    private Map<String, String> extraEZTInfoMap = new HashMap<String, String>(); 

private static final int QUESTION_TITLE_COL_SIZE = 1024;
    private static final float MAX_POINT_LIMIT =2000.00f;
    
    private boolean manualGradingRequired;   

    public boolean isManualGradingRequired() {
		return manualGradingRequired;
}

public void setManualGradingRequired(boolean manualGradingRequired) {
		this.manualGradingRequired = manualGradingRequired;
}

    public String getTitle() {
		return title;
}

public static float getMaxPointLimit() {
		return MAX_POINT_LIMIT;
}

public void setTitle(String title) {
		// Chopping the title for EZTest Questions.
		if(title != null && title.length() > QUESTION_TITLE_COL_SIZE) {
		    String str = title.substring(0, QUESTION_TITLE_COL_SIZE);
			title = str;
		}
		
		this.title = title;
}

public String getRenderingUrl() {
		return renderingUrl;
}

public void setRenderingUrl(String renderingUrl) {
		this.renderingUrl = renderingUrl;
}

private String title = null;
private String renderingUrl = null;
    
    public String getNativeAlaId() {
        return nativeAlaId;
    }   
    

    private String nativeAlaId;
    
    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public long getAlaManagerID() {
        return alaManagerID;
    }

    public void setAlaManagerID(long alaManagerID) {
        this.alaManagerID = alaManagerID;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public long getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setNativeAlaId(String nativeAlaId) {
        this.nativeAlaId = nativeAlaId;
    }
   

public long[] getSkillCategoryIds() {
		return skillCategoryIds;
}

public void setSkillCategoryIds(long[] skillCategoryIds) {
		this.skillCategoryIds = skillCategoryIds;
}


public long getActivityId() {
		return activityId;
}

public void setActivityId(long activityId) {
		this.activityId = activityId;
}
    
    public Map<String, String> getExtraEZTInfoMap() {
		return extraEZTInfoMap;
}

public void setExtraEZTInfoMap(Map<String, String> extraEZTInfoMap) {
		this.extraEZTInfoMap = extraEZTInfoMap;
}
}
