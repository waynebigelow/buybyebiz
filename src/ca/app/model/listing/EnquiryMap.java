package ca.app.model.listing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

import ca.app.model.user.User;

@Entity
@Table(name="enquiry_map")
public class EnquiryMap implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seqEnquiryMap", sequenceName="seq_enquiry_map", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqEnquiryMap")
	@Column(name="enquiry_map_id", unique=true)
	private int enquiryMapId;

	@Column(name="listing_id")
	private int listingId;
	
	@OneToOne(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinColumn(name="poster_id")
	private User poster;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="enquiry_map_id")
	@OrderBy(clause="post_date ASC")
	private List<EnquiryPost> posts;
	
	public int getEnquiryMapId() {
		return enquiryMapId;
	}
	public void setEnquiryMapId(int enquiryMapId) {
		this.enquiryMapId = enquiryMapId;
	}
	
	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}
	
	public User getPoster() {
		return poster;
	}
	public void setPoster(User poster) {
		this.poster = poster;
	}
	
	public List<EnquiryPost> getPosts() {
		if (posts == null) {
			return new ArrayList<EnquiryPost>();
		}
		return posts;
	}
	public void setPosts(List<EnquiryPost> posts) {
		this.posts = posts;
	}
}