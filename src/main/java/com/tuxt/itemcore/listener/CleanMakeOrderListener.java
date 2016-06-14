package com.tuxt.itemcore.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;
import com.tuxt.itemcore.service.IItemService;

/**
 * Application Lifecycle Listener implementation class CleanMakeOrderListener
 *
 */
@WebListener
public class CleanMakeOrderListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public CleanMakeOrderListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servletContextEvent)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent)  { 
    	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
    	IItemService itemService=	(IItemService) context.getBean("itemService");
    	if (itemService!=null) {
    		InputObject inputObject=new InputObject();
			OutputObject outputObject=new OutputObject();
			try {
				itemService.cleanMarkItems(inputObject, outputObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
	
}
