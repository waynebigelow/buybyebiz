package ca.app.security.channel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.ChannelDecisionManager;
import org.springframework.security.web.access.channel.ChannelProcessor;
import org.springframework.util.Assert;

public class AppChannelDecisionManagerImpl implements ChannelDecisionManager, InitializingBean {

	public static final String ANY_CHANNEL = "ANY_CHANNEL";
	private List<ChannelProcessor> channelProcessors;

	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(channelProcessors, "A list of ChannelProcessors is required");
	}

	public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException, ServletException {
		System.out.println("ChannelDecisionManagerImpl.decide()");
		
		Iterator<ConfigAttribute> attrs = config.iterator();
		while (attrs.hasNext()) {
			ConfigAttribute attribute = attrs.next();
			System.out.println("attribute: " + attribute.getAttribute());
			if (ANY_CHANNEL.equals(attribute.getAttribute())) {
				return;
			}
		}

		for (ChannelProcessor processor : channelProcessors) {
			System.out.println("processor: " + processor.getClass().getName());
			processor.decide(invocation, config);
			if (invocation.getResponse().isCommitted()) {
				break;
			}
		}
	}

	protected List<ChannelProcessor> getChannelProcessors() {
		return this.channelProcessors;
	}

	@SuppressWarnings("cast")
	public void setChannelProcessors(List<?> newList) {
		Assert.notEmpty(newList, "A list of ChannelProcessors is required");
		channelProcessors = new ArrayList<ChannelProcessor>(newList.size());

		for (Object currentObject : newList) {
			Assert.isInstanceOf(ChannelProcessor.class, currentObject, "ChannelProcessor " + currentObject.getClass().getName() + " must implement ChannelProcessor");
			channelProcessors.add((ChannelProcessor) currentObject);
		}
	}

	public boolean supports(ConfigAttribute attribute) {
		System.out.println("ChannelDecisionManagerImpl.supports()");
		
		if (ANY_CHANNEL.equals(attribute.getAttribute())) {
			return true;
		}

		for (ChannelProcessor processor : channelProcessors) {
			if (processor.supports(attribute)) {
				return true;
			}
		}

		return false;
	}
}