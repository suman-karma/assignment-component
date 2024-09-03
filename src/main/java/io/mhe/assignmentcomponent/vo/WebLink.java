package io.mhe.assignmentcomponent.vo;

/**
 * Created by IntelliJ IDEA.
 * User: Tarams
 * Date: May 24, 2008
 * Time: 5:35:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebLink implements Model {

	private String id = "";
    private String webLinkName = "";
    private String webLinkURL = "";

    public String getWebLinkName() {
        return webLinkName;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setWebLinkName(String webLinkName) {
        this.webLinkName = webLinkName;
    }

    public String getWebLinkURL() {
        return webLinkURL;
    }

    public void setWebLinkURL(String webLinkURL) {
        this.webLinkURL = webLinkURL;
    }

    public WebLink(String name, String url){
    	this.webLinkName = name;
       this.webLinkURL = url;
    }
    
    public WebLink(String id, String name, String url){
    	this.id = id;
       this.webLinkName = name;
       this.webLinkURL = url;
    }

	/**
	 * 
	 */
	public WebLink() {
		
	}

}
