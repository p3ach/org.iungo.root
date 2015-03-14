package org.iungo.root.api;

import org.iungo.id.api.IDFactory;
import org.iungo.node.api.NodeContext;

public class RootNodeContext extends NodeContext {

	private static final long serialVersionUID = 1L;

	public static final String ROOT_NODE_ID_FACTORY = idFactory.create(IDFactory.class.getSimpleName()).toString();
	
	public IDFactory getIDFactory() {
		return getAs(ROOT_NODE_ID_FACTORY);
	}
	
	public void putIDFactory(final IDFactory idFactory) {
		put(ROOT_NODE_ID_FACTORY, idFactory);
	}
}
