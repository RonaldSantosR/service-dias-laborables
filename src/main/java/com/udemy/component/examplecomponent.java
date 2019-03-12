package com.udemy.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component("examplecomponent")

public class examplecomponent {
	
	public static final Log LOG = LogFactory.getLog(examplecomponent.class);
	
	public void saycontroler() {
		LOG.info("controler");
	}

}
