/*
package io.mhe.assignmentcomponent.basiclti.consumersecret.impl;


import io.mhe.assignmentcomponent.basiclti.consumersecret.api.LMSConsumerService;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public class LMSConsumerServiceImpl implements LMSConsumerService {

    private static Logger log = Logger.(LMSConsumerServiceImpl.class);
    
    */
/**
     * Gets a consumer key, returns null if none found.
     *
     * @param consumerName
     * @return a key if one is stored, null if the name does not have a key assiciated.
     *//*

    @Override
    @Transactional(readOnly=true, propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
    public String getConsumerKey(String consumerName)  {
        try {
            return DaoFactory.getInstance().getoAuthDAO().getConsumerKey(consumerName);
        } catch (DataStoreException e) {
            log.error("Exception for API getConsumerKey for name = " + consumerName);
            return null;
        }
    }
    
    */
/**
     * Gets a consumer secret, returns null if none found.
     *
     * @param consumerKey
     * @return a secret if one is stored, null if the key does not have a secret assiciated.
     *//*

    @Override
    @Transactional(readOnly=true, propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
    public String getConsumerSecret(String consumerKey)  {
        try {
            return DaoFactory.getInstance().getoAuthDAO().getConsumerSecret(consumerKey);
        } catch (DataStoreException e) {
            log.error("Exception for API getConsumerSecret for key = " + consumerKey);
            return null;
        }
    }

    */
/**
     * Fetches all the available secrets.
     *
     * @return
     *//*

    @Override
    public List<OauthConsumerSecret> searchOauthConsumerSecrets() {
    	//To change body of implemented methods use File | Settings | File Templates.
    	return null;  
    }

    */
/**
     * Stores a consumer secret. Also supports the implicit deletion by setting the secret to null or an empty string.
     *
     * @param consumerKey,    the key, may never be null.
     * @param consumerSecret, the secret, can be null or empty string in order to delete the key/secret association.
     *//*

    @Override
    public void setConsumerSecret(String consumerKey, String consumerSecret) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly=true, propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
    public OAuthConsumerDetails getConsumerDetail(String consumerKey) {
        try {
            return DaoFactory.getInstance().getoAuthDAO().getConsumerDetail(consumerKey);
        } catch (DataStoreException e) {
            log.error("Exception for API getConsumerSecret for key = " + consumerKey);
            return null;
        }
    }
}
*/
