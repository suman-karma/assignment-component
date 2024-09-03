/* 
 * NAME : RestConstant  
 * Package : com.mhe.connect.business.common.httpRestClient
 * Version : 1.0
 * Author: Rest Framework Team
 * Date : 4th March 2013
 * Description : Interface to define constants to be used across 
 * 				 all classes of REST FRAMEWORK. 
 * Copyright 2010 The McGraw-Hill Companies.
 * All Rights Reserved *  
 */
package io.mhe.assignmentcomponent.service.generic.constants;

public interface RestConstant {
	
	String HTTP_AUTH_SECURITY_TYPE = "oauth";
	String BASIC_AUTH_HEADER = "Basic";
	String AUTHORIZATION_HEADER_KEY = "Authorization";
	String DATE_FORMAT_FROM_RMS = "MMM dd, yyyy HH:mm:ss a";
	String DATE_FORMAT_FROM_RMS_LICENCE_EXPIRY = "MMM dd, yyyy hh:mm:ss a";

	
  /**
   * Purpose: Stores String value of License Type for checkLicense interface.  
   */
   String HM_LICENSE="HM";   
  /**   
   * Purpose: Stores String value of License Type for checkLicense interface.
   */
  String HM_LICENSE_PLUS="HMPLUS"; 
 /**   
   * Purpose: Stores String value of no license  
   */
  String LICENSE_NONE = "noLicense";  
  /**   
   * Purpose: Stores String value of active license   
   */
  String LICENSE_ACTIVE ="active";	 
  /**   
   * Purpose: Stores String value of expired license   
   */
  String LICENSE_EXPIRED="expired";
  
  String NO_LICENSE = "noLicense"; 
  String TRIAL = "trial"; 
  String ACTIVE = "active";  
  String INACTIVE = "inactive";
  String STUDENT = "S";
  String STUDENT_IN_WORD = "Student";
  String INSTRUCTOR = "I";
  
  
  
  /* Constants used in IntegrationServiceImpl - STARTS */
	String SESSION_ID_KEY = "sessionId";
	String RA_USERNAME_KEY = "rausername";
	String ISBNS_KEY = "isbns";
	String SECURITY_TYPE_BASIC = "basic";
	String INSTALLATION_ID = "installationId";
	String EXTERNAL_USER_ID = "externalUserId";
	String EXTERNAL_ROLE = "externalRole";
	String SYSTEM_KEY = "systemKey";
	String ACCESS_LEVEL = "accessLevel";
	String ERIGHTS_USER_IDS = "erightsUserIds";
	String MH_USER_ID = "mhUserId";
	String MH_EXT_USER_ID = "externalUserIds";
	String INTERNAL_USER_ID = "internalUserId";
	String ISVALIDATE = "isValidate";
	/* Constants used in IntegrationServiceImpl - ENDS */
	
	/* Constants used in MHC Service Impl - STARTS */
	String SECURITY_TYPE_NONE = "none";
	/* Constants used in MHC Service Impl - ENDS */
	
	String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
	String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
	String OAUTH_TIMESTAMP = "oauth_timestamp";
	String OAUTH_NONCE = "oauth_nonce";
	String OAUTH_VERSION = "oauth_version";
}
