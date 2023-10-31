package ca.app.service.corporate;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.app.model.listing.Listing;
import ca.app.service.listing.ListingService;

@Controller
public class SitemapController {
	@Autowired
	private ListingService listingService;
	
	@RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET, produces = "application/xml;charset=utf-8")
	@ResponseBody
	public XmlUrlSet main() {
		List<Listing> listings = listingService.getAllActiveListings();
		
		XmlUrlSet xmlUrlSet = new XmlUrlSet();
		
		create(xmlUrlSet, "", getFormattedDate(System.currentTimeMillis()), XmlUrl.Priority.HIGH, XmlUrl.Frequency.WEEKLY);
		create(xmlUrlSet, "home.html", getFormattedDate(System.currentTimeMillis()), XmlUrl.Priority.HIGH, XmlUrl.Frequency.WEEKLY);
		create(xmlUrlSet, "search.html", getFormattedDate(System.currentTimeMillis()), XmlUrl.Priority.HIGH, XmlUrl.Frequency.DAILY);
		for (Listing listing : listings) {
			create(xmlUrlSet, listing.getListingURI()+".html", getFormattedDate(System.currentTimeMillis()), XmlUrl.Priority.HIGH, XmlUrl.Frequency.DAILY);
		}
		create(xmlUrlSet, "faq.html", getFormattedDate(System.currentTimeMillis()), XmlUrl.Priority.MEDIUM, XmlUrl.Frequency.MONTHLY);
		create(xmlUrlSet, "listIt.html", getFormattedDate(System.currentTimeMillis()), XmlUrl.Priority.MEDIUM, XmlUrl.Frequency.MONTHLY);
		create(xmlUrlSet, "contact.html", getFormattedDate(System.currentTimeMillis()), XmlUrl.Priority.LOW, XmlUrl.Frequency.YEARLY);
		create(xmlUrlSet, "tou.html", getFormattedDate(System.currentTimeMillis()), XmlUrl.Priority.LOW, XmlUrl.Frequency.YEARLY);
		create(xmlUrlSet, "pp.html", getFormattedDate(System.currentTimeMillis()), XmlUrl.Priority.LOW, XmlUrl.Frequency.YEARLY);
		
		return xmlUrlSet;
	}
	
	private void create(XmlUrlSet xmlUrlSet, String link, String lastMod, XmlUrl.Priority priority, XmlUrl.Frequency frequency) {
		xmlUrlSet.addUrl(new XmlUrl("https://buybyebiz.com/" + link, lastMod, priority, frequency));
	}
	
	private String getFormattedDate(Long time) {
		return new DateTime(time).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
	}
}