package io.mhe.assignmentcomponent.vo;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Map;

public class RestTransferTO<T> {

	private String				restRelativeURL;
	private HttpMethod			httpMethod;
	private Map<String, Object>	parameters				= new HashMap<String, Object>();
	private String				httpSecurityType;
	private Class<T>			responseType;
	private Object				requestType;
	private MediaType			contentType				= MediaType.APPLICATION_JSON;
	private String				baseUrl;
	private Map<String, String>	headerParams			= new HashMap<String, String>();
	private boolean				populateHeaderParams	= false;

	/**
	 * @return the contentType
	 */
	public MediaType getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentTypee(MediaType contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the httpSecurityType
	 */
	public String getHttpSecurityType() {
		return httpSecurityType;
	}

	/**
	 * @param httpSecurityType the httpSecurityType to set
	 */
	public void setHttpSecurityType(String httpSecurityType) {
		this.httpSecurityType = httpSecurityType;
	}

	/**
	 * @return the restRelativeURL
	 */
	public String getRestRelativeURL() {
		return restRelativeURL;
	}

	/**
	 * @param restTargetSystem the restRelativeURL to set
	 */
	public void setRestRelativeURL(String restTargetSystem) {
		this.restRelativeURL = restTargetSystem;
	}

	/**
	 * @return the httpMethod
	 */
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	/**
	 * @param httpMethod the httpMethod to set
	 */
	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the responseType
	 */
	public Class<T> getResponseType() {
		return responseType;
	}

	/**
	 * @param responseType the responseType to set
	 */
	public void setResponseType(Class<T> responseType) {
		this.responseType = responseType;
	}

	/**
	 * @return the requestType
	 */
	public Object getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(Object requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Map<String, String> getHeaderParams() {

		return headerParams;
	}

	public void setHeaderParams(Map<String, String> headerParams) {

		this.headerParams = headerParams;
	}

	public void addHeaderParam(String headerParamKey, String headerParamValue) {
		headerParams.put(headerParamKey, headerParamValue);
	}

	public boolean isPopulateHeaderParams() {

		return populateHeaderParams;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseUrl == null) ? 0 : baseUrl.hashCode());
		result = prime * result
				+ ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result
				+ ((headerParams == null) ? 0 : headerParams.hashCode());
		result = prime * result
				+ ((httpMethod == null) ? 0 : httpMethod.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RestTransferTO other = (RestTransferTO) obj;
		if (baseUrl == null) {
			if (other.baseUrl != null) {
				return false;
			}
		} else if (!baseUrl.equals(other.baseUrl)) {
			return false;
		}
		if (contentType == null) {
			if (other.contentType != null) {
				return false;
			}
		} else if (!contentType.equals(other.contentType)) {
			return false;
		}
		if (headerParams == null) {
			if (other.headerParams != null) {
				return false;
			}
		} else if (!headerParams.equals(other.headerParams)) {
			return false;
		}
        return httpMethod == other.httpMethod;
    }

	public void setPopulateHeaderParams(boolean populateHeaderParams) {

		this.populateHeaderParams = populateHeaderParams;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        String builder = "RestTransferTO [baseUrl=" +
                baseUrl +
                ", contentType=" +
                contentType +
                ", headerParams=" +
                headerParams +
                ", httpMethod=" +
                httpMethod +
                ", httpSecurityType=" +
                httpSecurityType +
                ", parameters=" +
                parameters +
                ", populateHeaderParams=" +
                populateHeaderParams +
                ", requestType=" +
                requestType +
                ", responseType=" +
                responseType +
                ", restRelativeURL=" +
                restRelativeURL +
                "]";
		return builder;
	}

}