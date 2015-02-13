package org.iungo.root.test;

import org.iungo.id.api.IDSequence;
import org.iungo.lifecycle.api.ServiceLifecycle;
import org.iungo.logger.api.Logger;
import org.iungo.logger.api.Loggers;
import org.iungo.logger.api.SimpleLogger;
import org.iungo.message.api.DefaultMessage;
import org.iungo.message.api.Message;
import org.iungo.node.api.Node;
import org.iungo.result.api.Result;
import org.iungo.root.api.Root;

public class Test {

	@org.junit.Test
	public void test() {
		final Logger logger = new SimpleLogger(Test.class.getName());
		
		Loggers.getInstance().add(logger);
		
		final Root root = Root.create();
		System.out.println(root.toString());
		logger.info(root.setState(ServiceLifecycle.STARTED).toString());
		
		final IDSequence idSequence = new IDSequence(Test.class.getName(), null);
		final Message message = new DefaultMessage(idSequence.next(), idSequence.next(), idSequence.next(), Message.NORMAL_PRIORITY, Node.NODE_ECHO_REQUEST_ID);
		
		Result result = root.receive(message);
		System.out.println(result);
		
		logger.info(Loggers.getInstance().toString());
	}

}
