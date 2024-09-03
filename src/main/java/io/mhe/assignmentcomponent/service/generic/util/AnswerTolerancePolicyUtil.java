package io.mhe.assignmentcomponent.service.generic.util;



import io.mhe.assignmentcomponent.common.util.GenUtil;
import io.mhe.assignmentcomponent.service.generic.vo.HomeworkManagerPolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * This util class prepares answer tolerance policies from the given tolerance policy value
 * 
 * @author bssomashekhara
 */
public final class AnswerTolerancePolicyUtil {
	private static final int	P_FB_EZTOTOLERANCE_COMBINED_POLICY_COUNT	= 3;
	private static final long	P_FB_IGNOREACCENTS_POLICY_ID				= -1;
	private static final long	P_FB_IGNORESPACING_POLICY_ID				= -2;
	private static final long	P_FB_IGNORECASE_POLICY_ID					= -3;

	private AnswerTolerancePolicyUtil() {
	}

	/**
	 * Method to take a single tolerance policy with values like 000,001,110 etc. and convert into 3 different tolerance
	 * policies. This has to be called whenever an assignment's policies are required for display.
	 * 
	 * @param fbEztoTolerance
	 * @return a list of tolerance policies (List<HomeworkManagerPolicy>)
	 */
	public static List<HomeworkManagerPolicy> getAnswerTolerancePolicyList(final String fbEztoTolerance) {
		String policyType = "YES|NO";
		if (!GenUtil.isBlankString(fbEztoTolerance) && fbEztoTolerance.length() >= P_FB_EZTOTOLERANCE_COMBINED_POLICY_COUNT) {
			final List<HomeworkManagerPolicy> policies = new ArrayList<HomeworkManagerPolicy>();
			final char[] tolerancePolicies = fbEztoTolerance.toCharArray();

			HomeworkManagerPolicy policy1 = new HomeworkManagerPolicy();
			policy1.setId(P_FB_IGNOREACCENTS_POLICY_ID);
			policy1.setExchange_key("p_fb_ignoreaccents");
			policy1.setValue(tolerancePolicies[0] == '0' ? "NO" : "YES");
			policy1.setName("accept non-accented characters");
			policy1.setType(policyType);
			policy1.setContentDrivenPolicy(false);
			policies.add(policy1);

			HomeworkManagerPolicy policy2 = new HomeworkManagerPolicy();
			policy2.setId(P_FB_IGNORESPACING_POLICY_ID);
			policy2.setExchange_key("p_fb_ignorespacing");
			policy2.setValue(tolerancePolicies[1] == '0' ? "NO" : "YES");
			policy2.setName("accept any spacing and punctuation");
			policy2.setType(policyType);
			policy2.setContentDrivenPolicy(false);
			policies.add(policy2);

			HomeworkManagerPolicy policy3 = new HomeworkManagerPolicy();
			policy3.setId(P_FB_IGNORECASE_POLICY_ID);
			policy3.setExchange_key("p_fb_ignorecase");
			policy3.setValue(tolerancePolicies[2] == '0' ? "NO" : "YES");
			policy3.setName("accept any letter case");
			policy3.setType(policyType);
			policy3.setContentDrivenPolicy(false);
			policies.add(policy3);

			return policies;
		}
		return null;
	}

	/**
	 * Method to take a single tolerance policy with values like 000,001,110 etc. and convert into 3 different tolerance
	 * policies. This has to be called whenever an assignment's policies are required for display.
	 * 
	 * @param fbEztoTolerance
	 * @return an array of tolerance policies (HomeworkManagerPolicy[])
	 */
	public static HomeworkManagerPolicy[] decodeAnswerTolerancePolicies(final String fbEztoTolerance) {
		final List<HomeworkManagerPolicy> policyList = getAnswerTolerancePolicyList(fbEztoTolerance);
		if (!GenUtil.isNull(policyList)) {
			return (HomeworkManagerPolicy[]) policyList.toArray(new HomeworkManagerPolicy[policyList.size()]);
		}
		return null;
	}

}
