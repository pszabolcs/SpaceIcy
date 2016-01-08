package edu.ubb.si.frontend.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name = "navigationController")
public class NavigationControllerBean {
	
	@ManagedProperty(value = "#{tabMenu}")
	private TabMenuBean tabMenu;
	
	public TabMenuBean getTabMenu() {
		return tabMenu;
	}

	public void setTabMenu(TabMenuBean tabMenu) {
		this.tabMenu = tabMenu;
	}

	public String moveToIndexImg(){
		tabMenu.setIndex(1);
	    return "index_img.xhtml?faces-redirect=true";
	}
	
	public String moveToQuery(){
		tabMenu.setIndex(0);
	    return "query.xhtml?faces-redirect=true";
	}

}
