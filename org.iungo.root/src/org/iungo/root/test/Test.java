package org.iungo.root.test;

import org.iungo.id.api.IDSequence;
import org.iungo.lifecycle.api.ServiceLifecycle;
import org.iungo.logger.api.Logger;
import org.iungo.logger.api.Loggers;
import org.iungo.logger.api.SimpleLogger;
import org.iungo.message.api.Message;
import org.iungo.message.api.Message;
import org.iungo.node.api.MessageLoop;
import org.iungo.node.api.Node;
import org.iungo.result.api.Result;
import org.iungo.root.api.RootNode;

public class Test {

	@org.junit.Test
	public void test() throws InterruptedException {
		final Logger logger = new SimpleLogger(Test.class.getName());
		
		final RootNode root = RootNode.create();
		
		Loggers.getInstance().setLevel(MessageLoop.class.getName(), Logger.DEBUG);
		
		Result result;
		
		result = root.setState(ServiceLifecycle.STARTED);
		logger.info(result.toString());

		result = root.setState(ServiceLifecycle.STOPPED);
		logger.info(result.toString());

		result = root.setState(ServiceLifecycle.CLOSED);
		logger.info(result.toString());
		
//		final IDSequence idSequence = new IDSequence(Test.class.getName(), null);
//		Message message = new DefaultMessage(idSequence.next(), idSequence.next(), root.getID(), Message.NORMAL_PRIORITY, Node.NODE_ECHO_REQUEST_ID);
//		
//		result = root.receive(message);
//		logger.info(result.toString());
//		
//		message = new DefaultMessage(idSequence.next(), idSequence.next(), root.getID(), Message.NORMAL_PRIORITY, Node.NODE_STOPPED_ID);
//		logger.info(root.receive(message).toString());
//
////		logger.info(root.setState(ServiceLifecycle.STOPPED).toString());
//
//		message = new DefaultMessage(idSequence.next(), idSequence.next(), root.getID(), Message.NORMAL_PRIORITY, Node.NODE_STARTED_ID);
//		logger.info(root.receive(message).toString());
//
//		
//		logger.info(root.setState(ServiceLifecycle.CLOSED).toString());
//
//		logger.info(Loggers.getInstance().toString());
	}

}
