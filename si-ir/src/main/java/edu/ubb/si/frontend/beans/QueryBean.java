package edu.ubb.si.frontend.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * 
 * @author bordie
 *
 */
@RequestScoped
@ManagedBean(name = "query")
public class QueryBean {
	
	private static final String EMPTY_STRING = "";
	
	@ManagedProperty(value = "#{tabMenu}")
	private TabMenuBean tabMenuBean;
	
	public TabMenuBean getTabMenuBean() {
		return tabMenuBean;
	}

	public void setTabMenuBean(TabMenuBean tabMenuBean) {
		this.tabMenuBean = tabMenuBean;
	}

	public String doSomeWork(){
		// Do some work
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Some Work Achieved"));
		// Change the index that TabMenu refers as activated tab
		tabMenuBean.setIndex(1);
		return EMPTY_STRING;
	}
}
