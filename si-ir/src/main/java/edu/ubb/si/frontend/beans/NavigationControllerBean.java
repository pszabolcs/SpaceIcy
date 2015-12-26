package edu.ubb.si.frontend.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean(name = "navigationController")
public class NavigationControllerBean {
	
	public String moveToIndexImg(){
	    return "index_img.xhtml?faces-redirect=true";
	}
	
	public String moveToQuery(){
	    return "query.xhtml?faces-redirect=true";
	}

}
