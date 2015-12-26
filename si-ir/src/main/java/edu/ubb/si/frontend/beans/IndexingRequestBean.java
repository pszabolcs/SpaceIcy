package edu.ubb.si.frontend.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import edu.ubb.si.crawler.Controller;
import edu.ubb.si.frontend.util.Messages;

@RequestScoped
@ManagedBean(name = "indexingRequest")
public class IndexingRequestBean {
	
	private static final String EMPTY_STRING = "";
	
	private String url;
	private String storage;
	private String maxpages;
	private String maxdepth;
	private Controller controller;
	
	public IndexingRequestBean() {
		controller = new Controller();
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		System.out.println(url);
		this.url = url;
	}
	
	public String getStorage() {
		return storage;
	}
	
	public void setStorage(String storage) {
		this.storage = storage;
	}
	
	public String getMaxpages() {
		return maxpages;
	}
	
	public void setMaxpages(String maxpages) {
		this.maxpages = maxpages;
	}
	
	public String getMaxdepth() {
		return maxdepth;
	}
	
	public void setMaxdepth(String maxdepth) {
		this.maxdepth = maxdepth;
	}
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	protected void setControllerParameters() {
		if (!storage.equals(EMPTY_STRING)) {
			controller.setStorageFolder(storage);
		} else {
			Messages.sendWarningMessage("Folder of storage on disk was not set!");
		}
		
		try {
			controller.setMaxPagesToFetch(Integer.parseInt(maxpages));
		} catch (NumberFormatException e) {
			Messages.sendErrorMessage("Maximum number pages to fetch was not set or is not a number!");
		}
		
		try {
			controller.setMaxDepthOfCrawling(Integer.parseInt(maxdepth));
		} catch (NumberFormatException e) {
			Messages.sendErrorMessage("Maximum depth of crawling was not set or is not a number!");
		}
		
	}
	
	public String fetchPages() {
		if (url.equals(EMPTY_STRING)) {
			Messages.sendErrorMessage("Please provide a valid url to start with!");
			return EMPTY_STRING;
		}
		
		setControllerParameters();
		try {		
			controller.fetchPage(url);
			Messages.sendInfoMessage("Crawling has ended. Storage folder: " + storage);
		} catch (Exception e) {
			Messages.sendErrorMessage("Something went wrong. Try again, please!");
			e.printStackTrace();
		}
		return EMPTY_STRING;
	}

}
