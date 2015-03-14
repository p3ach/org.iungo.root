package org.iungo.root.api;

import org.iungo.lifecycle.api.ServiceLifecycle;
import org.iungo.logger.api.Logger;
import org.iungo.logger.api.SimpleLogger;

public class Bootstrap {
	
	private static final Logger logger = new SimpleLogger(Bootstrap.class.getName());
	
	public static void main(String[] args) {
		final RootNode root = RootNode.create();
		root.setState(ServiceLifecycle.STARTED);
	}

}
