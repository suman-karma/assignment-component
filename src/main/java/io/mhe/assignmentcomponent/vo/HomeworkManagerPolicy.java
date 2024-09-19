package io.mhe.assignmentcomponent.vo;

import java.util.ArrayList;
import java.util.List;

public class HomeworkManagerPolicy implements Model {

	public enum APX_POLICY_NAME{
		p_startdate, p_duedate
	}
	
    private long id = 0L;

    private String name = "";

    private String exchange_key = "";

    private String value = "";
    
    private boolean  isContentDrivenPolicy; 
    
    private String assignmentType = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange_key() {
        return exchange_key;
    }

    public void setExchange_key(String exchange_key) {
        this.exchange_key = exchange_key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    

	public boolean isContentDrivenPolicy() {
		return isContentDrivenPolicy;
	}

	public void setContentDrivenPolicy(boolean isContentDrivenPolicy) {
		this.isContentDrivenPolicy = isContentDrivenPolicy;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type = "";

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }
    
	
	
	/**
	 * @return the assignmentType
	 */
	public String getAssignmentType() {
		return assignmentType;
	}

	/**
	 * @param assignmentType the assignmentType to set
	 */
	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	private long categoryID = 0L;

	
	public boolean isAPXPolicy() {
		for(APX_POLICY_NAME apxPolicyName : APX_POLICY_NAME.values()){
			if(this.getExchange_key().equalsIgnoreCase(apxPolicyName.name())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns the exchane key names which are specific to APX only.
	 * @return
	 */
	public static final List<String> getAPXPolicyList(){
		List<String> list = new ArrayList<String>();
		for(APX_POLICY_NAME p:APX_POLICY_NAME.values()){
			list.add(p.name());
		}
		return list;
	}
	
    @Override
    public String toString() {
	return "HomeworkManagerPolicy [categoryID=" + categoryID
		+ ", exchange_key=" + exchange_key + ", id=" + id
		+ ", isContentDrivenPolicy=" + isContentDrivenPolicy
		+ ", name=" + name + ", type=" + type + ", value=" + value
		+ "]";
    }
    
    
}

