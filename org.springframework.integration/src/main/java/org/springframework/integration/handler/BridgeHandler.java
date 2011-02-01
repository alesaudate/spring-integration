/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.handler;

import org.springframework.integration.core.Message;
import org.springframework.util.Assert;

/**
 * A simple MessageHandler implementation that passes the request Message
 * directly to the output channel without modifying it. The main purpose of this
 * handler is to bridge a PollableChannel to a SubscribableChannel or
 * vice-versa.
 * <p/>
 * The BridgeHandler can be used as a stopper at the end of an assembly line of
 * channels. In this setup the output channel doesn't have to be set, but if the
 * output channel is omitted the <tt>REPLY_CHANNEL</tt> MUST be set on the
 * message.
 * 
 * @author Mark Fisher
 * @author Iwein Fuld
 */
public class BridgeHandler extends AbstractReplyProducingMessageHandler {

	@Override
	protected void handleRequestMessage(Message<?> requestMessage, ReplyMessageHolder replyMessageHolder) {
		if (requestMessage.getHeaders().getReplyChannel() == null) {
			this.verifyOutputChannel();
		}
		replyMessageHolder.set(requestMessage);
	}

	private void verifyOutputChannel() {
		Assert.state(super.getOutputChannel() != null, "Bridge handler requires an output channel");
	}

}