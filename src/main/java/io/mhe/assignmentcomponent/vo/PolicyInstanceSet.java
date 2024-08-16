package io.mhe.assignmentcomponent.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PolicyInstanceSet implements Model{
	private long id;
	private String name;
	private long policySetId;
	
	private List<Policy> policyList = null;

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

	public long getPolicySetId() {
		return policySetId;
	}

	public void setPolicySetId(long policySetId) {
		this.policySetId = policySetId;
	}

	public List<Policy> getPolicyList() {
		return policyList;
	}
	
	public Map<String, Policy> getPolicyMap() throws Exception {
		if(policyList != null) {
			HashMap<String, Policy> map = new HashMap<String, Policy>();
			for(int i = 0; i < policyList.size(); i++) {
				Policy p = policyList.get(i);
				if(p == null) {
					throw new Exception("Null policy in policy list : policyInstanceSetId = " + this.getId());
				}	
				if(p.getExchange_key() == null) {
					throw new Exception("Policy instance present without an exchange key : policy_id = " + p.getId());
				}
				map.put(p.getExchange_key(), p);
			}
			return map;
		}
		return null;
	}

	public void setPolicyList(List<Policy> policies) {
		this.policyList = policies;
	}

	@Override
	public String toString() {
	    return "PolicyInstanceSet [id=" + id + ", name=" + name + ", policySetId=" + policySetId + ", policyList=" + policyList + "]";
	}
}
