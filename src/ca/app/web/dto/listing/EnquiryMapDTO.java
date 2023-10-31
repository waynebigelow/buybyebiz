package ca.app.web.dto.listing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.EnquiryPost;
import ca.app.service.common.TokenFieldType;
import ca.app.util.RequestUtil;
import ca.app.web.dto.user.UserDTO;

public class EnquiryMapDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int enquiryMapId;
	private int loggedInId;
	private ListingDTO listing;
	private UserDTO poster;
	private List<EnquiryPostDTO> posts;
	private int inboxType = 1;

	public EnquiryMapDTO(EnquiryMap enquiryMap) {
		this.enquiryMapId = enquiryMap.getEnquiryMapId();
		this.poster = new UserDTO(enquiryMap.getPoster());
		this.posts = new ArrayList<EnquiryPostDTO>();
		for(EnquiryPost post : enquiryMap.getPosts()) {
			this.posts.add(new EnquiryPostDTO(post));
		}
	}
	
	public EnquiryMapDTO(EnquiryMap enquiryMap, UserDTO userDTO) {
		this(enquiryMap);
		this.poster = userDTO;
	}
	
	@JsonIgnore
	public int getEnquiryMapId() {
		return enquiryMapId;
	}
	public void setEnquiryMapId(int enquiryMapId) {
		this.enquiryMapId = enquiryMapId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.ENQUIRY.getKey(), enquiryMapId);
	}
	
	public int getLoggedInId() {
		return loggedInId;
	}
	public void setLoggedInId(int loggedInId) {
		this.loggedInId = loggedInId;
	}

	public ListingDTO getListing() {
		return listing;
	}
	public void setListing(ListingDTO listing) {
		this.listing = listing;
	}
	
	public UserDTO getPoster() {
		return poster;
	}
	public void setPoster(UserDTO poster) {
		this.poster = poster;
	}
	
	public List<EnquiryPostDTO> getPosts() {
		if (posts == null) {
			return new ArrayList<EnquiryPostDTO>();
		}
		return posts;
	}
	public void setPosts(List<EnquiryPostDTO> posts) {
		this.posts = posts;
	}
	
	public int getInboxType() {
		return inboxType;
	}
	public void setInboxType(int inboxType) {
		this.inboxType = inboxType;
	}
	
	public String getPosterFormatted() {
		if (inboxType == 1) {
			return "Posted by:&nbsp;" + poster.getDisplayName() + "&nbsp;" + getCountFormatted();
		} else {
			if (listing.isSold()) {
				return "Posted for:&nbsp;" + listing.getTitle() + "&nbsp;SOLD";
			} else {
				return "Posted for:&nbsp;" + listing.getTitle() + "&nbsp;" + getCountFormatted();
			}
		}
	}
	
	public String getCountFormatted() {
		int count = 0;
		for(EnquiryPostDTO post : posts) {
			if (!post.isRead()) {
				if (inboxType == 1) {
					if (listing != null && post.getAuthorId() != listing.getUser().getUserId()) {
						count++;
					}
				} else {
					if (post.getAuthorId() != loggedInId) {
						count++;
					}
				}
			}
		}

		return "<span id=\"badge"+getToken()+"\" class=\"badge\" style=\"background-color:"+(count>0?"#ff0000":"#0000ff")+"\">"+count+" Msgs</span>";
	}
}