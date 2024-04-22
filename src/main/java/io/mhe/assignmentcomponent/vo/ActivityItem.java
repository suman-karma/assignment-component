/*
 * $Source$
 * $Revision$
 * $Author$
 * $Date$
 *
 * Copyright 2003 The McGraw-Hill Companies.
 * All Rights Reserved
 *
 * REVISION HISTORY
 * -------------------------
 * NOTE: CVS automatically inserts check-in comments below.  Add
 * manually only if reqd.
 *
 * $Log$
 * Revision 1.10  2014/02/11 16:15:32  csekhar
 * Sonar fixes
 *
 * Revision 1.9  2013/01/11 15:24:53  rvohra
 * Manual Grading Changes
 *
 * Revision 1.8  2013/01/03 15:15:39  rvohra
 * Revertion - Manually Graded Assignment
 *
 * Revision 1.7  2013/01/03 10:34:03  rvohra
 * Manual Grading Changes for Review & Assign
 *
 * Revision 1.6  2012/10/05 14:18:24  dnabin
 * Changes for ActivityItem_ExtendedInfo by Jahangir
 *
 * Revision 1.5  2012/06/04 13:13:59  sreejith
 * Fixed : QC item: 8658:
 *
 * Revision 1.4  2011/04/12 20:09:13  wkeung
 * sync as of 2011-04-12 from connect for connect properties, from HEAD of connect, TAG: R5A_1_4
 *
 * Revision 1.5  2011/02/15 13:08:53  rsivaram
 * chopping the question title for an EZTest. 1024 is the maximum characters allowed for a question title. rest of the title are ignored.
 *
 * Revision 1.4  2011/02/11 12:44:34  cvelmuni
 * Added activity id in the object.
 *
 * Revision 1.3  2010/10/19 13:09:35  pkumar
 * ALA Refactoring - Modified Activity APIs to fetch ALA Information
 *
 * Revision 1.2  2010/10/13 23:34:43  pkumar
 * ala refactoring changes
 *
 * Revision 1.1.1.1  2010/03/15 21:40:11  pkumar
 * connect
 *
 * Revision 1.1  2010/03/11 21:28:02  pkumar
 * no message
 *
 * Revision 1.1  2010/03/08 18:43:18  cgomes
 * *** empty log message ***
 *
 * Revision 1.1  2010/03/08 18:33:03  cgomes
 * *** empty log message ***
 *
 * Revision 1.1  2010/03/05 15:42:41  kshriva
 * kapil
 *
 * Revision 1.1  2010/03/05 00:01:59  kshriva
 * *** empty log message ***
 *
 * Revision 1.1  2010/03/03 20:01:39  kshriva
 * Changed the package from gradeBook to gradebook
 *
 * Revision 1.1  2010/02/12 17:37:43  cgomes
 * First Commit of Spring Changes
 *
 * Revision 1.3  2008/05/30 15:09:18  eanand
 * added skillcategoryids property
 *
 * Revision 1.2  2008/02/20 12:48:39  minakshi
 * setting default value of weight as 0 to fix points discrepancy issue.
 *
 * Revision 1.1.1.1  2007/12/14 19:31:00  smogalip
 * no message
 *
 * Revision 1.4.16.1  2007/11/14 12:18:26  pkumar
 * manual grading clean up.
 *
 * Revision 1.4  2004/04/24 01:59:41  ychen
 * change assignment node, activity node from clob to varchar2(4000)
 *
 * Revision 1.3  2004/01/14 00:05:41  ychen
 * set default to 1
 *
 * Revision 1.2  2003/10/10 18:35:58  llavallee
 * Added proper CVS header, removed old attributes as not needed
 *
 *
 */

package io.mhe.assignmentcomponent.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  Kameshwar
 * $Author$
 */
public class ActivityItem implements Model {

    protected long id = 0L;
    protected long alaManagerID = 0L;
    protected float weight = 0F;
    protected long sequenceNo = 0L;
    protected long[] skillCategoryIds = null;
    protected Date updatedDate = new Date();
    protected Date createdDate = new Date();
    protected long activityId =0l;
    
    //addedd for ExtraEZTInfo
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

/**
 * @return the activityId
 */
public long getActivityId() {
		return activityId;
}

/**
 * @param activityId the activityId to set
 */
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
