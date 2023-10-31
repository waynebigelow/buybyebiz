package ca.app.service.mail;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum GlobalEmailType {
	//										id		subject									i18n					template
	WELCOME							(1,		"Welcome",								"501.subject",		"velocity/user/welcome.vm"),
	WELCOME_ENQUIRY			(2,		"Welcome",								"501.subject",		"velocity/user/welcomeEnquiry.vm"),
	CONFIRM_PWD_RESET		(3,		"Confirm Password Reset",		"502.subject",		"velocity/user/confirmPwdReset.vm"),
	PWD_RESET						(4,		"Password Reset",					"503.subject",		"velocity/user/pwdReset.vm"),
	PWD_CHANGED					(5,		"Password Changed",				"504.subject",		"velocity/user/pwdChanged.vm"),
	ACCT_LOCKED					(6,		"Account Locked",					"505.subject",		"velocity/user/acctLocked.vm"),
	LISTING_ENQUIRY			(7,		"Listing Enquiry",						"506.subject",		"velocity/site/listingEnquiry.vm"),
	ENQUIRY_REPLY				(8,		"Enquiry Reply",						"507.subject",		"velocity/site/enquiryReply.vm"),
	SUPPORT_ISSUE				(9,		"Support Issue (Internal)",			"",						""),
	SYSTEM_PROBLEM				(10,	"System Problem Detection",		"",						""),
	USER_LOCKOUT					(11,	"User lockout",						"",						""),
	SITE_RECEIPT					(12,	"Listing Receipt",						"",						""),
	SHARE_BY_EMAIL				(13,	"Social Media Sharing",				"",						""),
	ADMIN_REQUEST				(14,	"Admin Request",						"",						"velocity/admin/adminRequest.vm"),
	SITE_CONTENT_REJECTED	(15,	"Site Content Rejected",			"508.subject",		"velocity/site/contentRejected.vm"),
	SITE_ACTIVATED				(16,	"Site Activated",						"509.subject",		"velocity/site/activated.vm"),
	SITE_EXPIRY_PENDING		(17,	"Site Expiry Pending",				"510.subject",		"velocity/site/pendingExpiry.vm"),
	SITE_EXPIRED					(18,	"Site Expired",							"511.subject",		"velocity/site/expired.vm"),
	PROMOTIONAL_WELCOME	(19,	"Promotional Email",					"512.subject",		"velocity/admin/promoWelcome.vm"),
	EMAIL_CHANGE					(20,	"Email Change",						"513.subject",		"velocity/user/emailChange.vm"),
	SITE_EXTENDED				(21,	"Site Extended",						"514.subject",		"velocity/site/extended.vm"),
	;
	
	private int id;
	private String subject;
	private String i18n;
	private String template;
	
	private GlobalEmailType(int id, String subject, String i18n, String template) {
		this.id = id;
		this.subject = subject;
		this.i18n = i18n;
		this.template = template;
	}

	private static final Map<Integer,GlobalEmailType> lookup = new HashMap<Integer,GlobalEmailType>();
	static {
		for (GlobalEmailType currEnum : EnumSet.allOf(GlobalEmailType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	public static GlobalEmailType get(int id) {
		return lookup.get(id);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getI18n() {
		return i18n;
	}
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}

	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}