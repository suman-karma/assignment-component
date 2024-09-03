package io.mhe.assignmentcomponent.service.generic.vo;


import io.mhe.assignmentcomponent.common.util.GenUtil;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.*;

/**
 * Created by IntelliJ IDEA. User: Praveen_Meruga Date: Mar 8, 2007 Time: 5:37:17 PM To change this template use File |
 * Settings | File Templates.
 */
public class HomeworkManagerCategoryPolicy extends HomeworkManagerCategory {
	public HomeworkManagerPolicy[] getPolicies() {
		return policies;
	}

	public void setPolicies(HomeworkManagerPolicy[] policies) {
		this.policies = policies;
	}

	public HashMap<String, String> getPoliciesInAMap() {
		if (!GenUtil.isBlankArray(this.policies)) {
			this.policyMap = new HashMap<String, String>();
			for (int i = 0; i < this.policies.length; i++) {
				this.policyMap.put(this.policies[i].getExchange_key(), this.policies[i].getValue());
			}
			return policyMap;
		}
		return null;
	}

	public Map<String, HomeworkManagerPolicy> getHMPolicyMapFromPoliciesList() {
		Map<String, HomeworkManagerPolicy> map = new HashMap<String, HomeworkManagerPolicy>();
		for (HomeworkManagerPolicy p : policiesList) {
			map.put(p.getExchange_key(), p);
		}
		return map;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public void addPolicyToList(HomeworkManagerPolicy policy) {
		// Note: If policy added to this list directly, we should call preparePolicyArrayFromList()
		// finally to prepare policy array as it may be used in some places
		policiesList.add(policy);
	}

	public List<HomeworkManagerPolicy> getPoliciesList() {
		return policiesList;
	}

	public void setPoliciesList(List<HomeworkManagerPolicy> policiesList) {
		this.policiesList = policiesList;
		preparePolicyArrayFromList();
	}

	private HomeworkManagerPolicy[] policies = new HomeworkManagerPolicy[0];
	private List<HomeworkManagerPolicy> policiesList = new ArrayList<HomeworkManagerPolicy>(0);
	public HashMap<String, String> policyMap = null;

	/**
	 * Method to prepare policy array from policy list
	 */
	public void preparePolicyArrayFromList() {
		this.policies = (!GenUtil.isNull(this.policiesList)) ?
				(this.policiesList.toArray(new HomeworkManagerPolicy[this.policiesList.size()]))
				: new HomeworkManagerPolicy[0];
	}

	/**
	 * Method to prepare policy list from policy array
	 */
	public void preparePolicyListFromArray() {
		this.policiesList = (!GenUtil.isBlankArray(this.policies)) ? (Arrays.asList(this.policies))
				: new ArrayList<HomeworkManagerPolicy>(0);
	}

}
