package org.iungo.root.api;

import java.util.UUID;

import org.iungo.id.api.IDFactory;
import org.iungo.node.api.AbstractNode;
import org.iungo.router.api.Router;
import org.iungo.router.api.SimpleRouter;

public class Root extends AbstractNode {
	
	private static final long serialVersionUID = 1L;

	public static Root create() {
		final Router router = new SimpleRouter();
		final IDFactory idFactory = new IDFactory(UUID.randomUUID().toString(), null);
		return new Root(router, idFactory);
	}
	
	public Root(final Router router, final IDFactory idFactory) {
		super(router, idFactory.create("0"));
	}

	@Override
	public String toString() {
		return String.format("%s [\n%s\n]", Root.class.getName(), super.toString());
	}
}