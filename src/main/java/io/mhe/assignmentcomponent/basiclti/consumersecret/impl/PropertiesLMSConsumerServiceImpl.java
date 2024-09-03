/*
package io.mhe.assignmentcomponent.basiclti.consumersecret.impl;

import com.mhe.connect.business.basiclti.consumersecret.api.LMSConsumerService;
import com.mhe.connect.business.basiclti.consumersecret.api.OAuthConsumerDetails;
import com.mhe.connect.business.basiclti.consumersecret.api.UnexpectedConsumerServiceExcption;
import com.mhe.connect.business.basiclti.consumersecret.model.OauthConsumerSecret;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesLMSConsumerServiceImpl extends PropertiesLoaderSupport implements LMSConsumerService {

	@Override
	public String getConsumerSecret(String consumerKey) {
		Properties properties = getPropertiesInternal();
		return properties.getProperty(consumerKey);
	}
	@Override
	public String getConsumerKey(String consumerName) {
		return null;
	}
	
	protected Properties getPropertiesInternal() {
		try {
			return mergeProperties();
		} catch (IOException e) {
			throw new UnexpectedConsumerServiceExcption(e);
		}
	}

	@Override
	public List<OauthConsumerSecret> searchOauthConsumerSecrets() {
		Properties properties = getPropertiesInternal();
		List<OauthConsumerSecret> rv = new ArrayList<OauthConsumerSecret>(properties.size());
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String consumerSecret = ObjectUtils.toString(entry.getValue());
			String consumerKey = ObjectUtils.toString(entry.getKey());
			if (StringUtils.isNotEmpty(consumerKey) && StringUtils.isNotEmpty(consumerSecret)) {
				rv.add(new OauthConsumerSecret(consumerKey, consumerSecret));
			}
		}
		return rv;
	}

	@Override
	public void setConsumerSecret(String consumerKey, String consumerSecret) {
		throw new UnsupportedOperationException("This method is not supported by the PropertiesLMSConsumerServiceImpl");
	}

    @Override
    public OAuthConsumerDetails getConsumerDetail(String consumerKey) {
        throw new UnsupportedOperationException("This method is not supported by the PropertiesLMSConsumerServiceImpl");
    }

}
*/
