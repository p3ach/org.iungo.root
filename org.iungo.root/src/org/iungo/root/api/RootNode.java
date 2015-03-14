package org.iungo.root.api;

import java.util.UUID;

import javax.xml.soap.Node;

import org.iungo.cli.api.ArgumentHandle;
import org.iungo.cli.api.CloseNodeArgument;
import org.iungo.cli.api.Console;
import org.iungo.cli.api.CreateTDBNodeArgument;
import org.iungo.cli.api.NodeEchoRequestArgument;
import org.iungo.cli.api.Session;
import org.iungo.cli.api.ShowRouterArgument;
import org.iungo.cli.api.ShowSystemArgument;
import org.iungo.cli.api.SimpleSession;
import org.iungo.common.properties.api.JavaRuntimeProperties;
import org.iungo.common.properties.api.JavaSystemProperties;
import org.iungo.id.api.ID;
import org.iungo.id.api.IDSequence;
import org.iungo.lifecycle.api.ServiceLifecycle;
import org.iungo.logger.api.Logger;
import org.iungo.logger.api.Loggers;
import org.iungo.message.api.Message;
import org.iungo.node.api.AbstractNode;
import org.iungo.node.api.MessageLoopDispatchObservable;
import org.iungo.node.api.MessageLoopDispatchObserver;
import org.iungo.node.api.WeakNodeSet;
import org.iungo.result.api.Result;
import org.iungo.router.api.SimpleRouter;
import org.iungo.tdb.api.TDBNode;
import org.iungo.tdb.api.TDBNodeContext;

public class RootNode extends AbstractNode {
	
	private static final Logger logger = Loggers.create(RootNode.class.getName());
	
	private static final long serialVersionUID = 1L;

	public static RootNode create() {
		final RootNodeContext rootNodeContext = new RootNodeContext();
		rootNodeContext.putRouter(new SimpleRouter());
		rootNodeContext.putID(new ID(UUID.randomUUID().toString(), null, "0"));
		return new RootNode(rootNodeContext);
	}
	
	public static void main(final String[] args) {
		final RootNode root = RootNode.create();
		root.setState(ServiceLifecycle.STARTED);
	}
	
	private final Session session = new SimpleSession();
	
	private final WeakNodeSet nodes = new WeakNodeSet();

	private final IDSequence idSequence;
	
	public RootNode(final RootNodeContext rootNodeContext) {
		/*
		 * Super...
		 */
		super(rootNodeContext);

		idSequence = new IDSequence(getID().getRoot(), Node.class.getSimpleName());
		
		/*
		 * Configure console.
		 */
		Console.getInstance().setSession(session);

		/*
		 * Configure session.
		 */
		
		session.getExecuteEnvironment().getArgumentHandles().add(CreateTDBNodeArgument.class.getName(), new ArgumentHandle() {
			@Override
			public Result go(final Result result) {
				final TDBNodeContext tdbNodeContext = result.getValue();
				tdbNodeContext.putRouter(getRouter());
				tdbNodeContext.putID(idSequence.next());
				return new Result(true, null, new TDBNode(tdbNodeContext));
			}
		});
		
		session.getExecuteEnvironment().getArgumentHandles().add(ShowRouterArgument.class.getName(), new ArgumentHandle() {
			@Override
			public Result go(final Result result) {
				final String text = getRouter().toString();
				return new Result(true, text, text);
			}
		});
		
		session.getExecuteEnvironment().getArgumentHandles().add(ShowSystemArgument.class.getName(), new ArgumentHandle() {
			@Override
			public Result go(final Result result) {
				final String text = getShowSystem();
				return new Result(true, text, text);
			}
		});

		session.getExecuteEnvironment().getArgumentHandles().add(CloseNodeArgument.class.getName(), new ArgumentHandle() {
			@Override
			public Result go(final Result result) {
				final ID id = result.getValue();
				final Message message = new Message(getMessageIDSequence().next(), getID(), id, Message.NORMAL_PRIORITY, NODE_CLOSED_ID);
				return send(message);
			}
		});
		
		session.getExecuteEnvironment().getArgumentHandles().add(NodeEchoRequestArgument.class.getName(), new ArgumentHandle() {
			@Override
			public Result go(final Result result) {
				final ID to = result.getValue();
				final Message message = new Message(getMessageIDSequence().next(), getID(), to, Message.NORMAL_PRIORITY, NODE_ECHO_REQUEST_ID);
				return send(message);
			}
		});
		
		/*
		 * Observers.
		 */
		
		/*
		 * 
		 */
		rootMessageLoop.getReceiveMessageLoopQueue().getMessageLoopDispatchObservers().add(NODE_ECHO_REPLY_ID, new MessageLoopDispatchObserver() {
			@Override
			public Result observe(final MessageLoopDispatchObservable messageLoopDispatchObservable) {
				logger.info(messageLoopDispatchObservable.getResult().toString());
				return Result.TRUE;
			}
		});
		
		// TODO Add VM Shutdown hook.
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				setState(CLOSED);
			}
		});
		
		/*
		 * Splash...
		 */
		logger.info(getShowSystem());
	}
	
	protected String getShowSystem() {
		return String.format("%s\n%s\n%s", JavaSystemProperties.instance.toString(), JavaRuntimeProperties.instance.toString(), toString());
	}
}