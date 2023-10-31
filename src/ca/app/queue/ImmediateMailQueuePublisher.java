package ca.app.queue;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import ca.app.service.mail.GlobalEmailType;
import ca.app.service.mail.MailAttachment;
import ca.app.service.mail.MailMessage;
import ca.app.util.LogUtil;

public class ImmediateMailQueuePublisher {

	private JmsTemplate jmsTemplate;
	private Queue queue;

	public void sendMessageToQueue(final List<String> toList, final List<String> ccList, final List<String> bccList, final String from, 
		final String subject, final String body, final List<MailAttachment> attachmentList, final int applicationId, final int listingId, final int userId, final GlobalEmailType emailType) {
		
		this.jmsTemplate.send(queue, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MailMessage mm = new MailMessage(toList, ccList, bccList, from, subject, body, attachmentList, applicationId, listingId, userId, emailType.getId());
				ObjectMessage message = session.createObjectMessage(mm);
				return message;
			}
		});
		
		String msg = "Enqueue Email ";
		if (toList != null && toList.size() > 0) {
			String toStr = "";
			for (String to : toList) {
				toStr += ((toStr.length() == 0) ? "" : ", ") + to;
			}
			msg += "[to=" + toStr + "]";
		}
		
		if (ccList != null && ccList.size() > 0) {
			String ccStr = "";
			for (String cc : ccList) {
				ccStr += ((ccStr.length() == 0) ? "" : ", ") + cc;
			}
			msg += "[cc=" + ccStr + "]";
		}
		
		if (bccList != null && bccList.size() > 0) {
			String bccStr = "";
			for (String bcc : bccList) {
				bccStr += ((bccStr.length() == 0) ? "" : ", ") + bcc;
			}
			msg += "[bcc=" + bccStr + "]";
		}
		
		msg += "[subject=" + subject + "]";
		LogUtil.logInfo(this.getClass(), msg);
	}
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	public void setQueue(Queue queue) {
		this.queue = queue;
	}
}