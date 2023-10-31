package ca.app.model.listing;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MetaDataType {
	//										id
	TITLE								(1),
	DESCRIPTION					(2),
	HOURS_OF_OPERATION		(3),
	NUMBER_OF_EMPLOYEES	(4),
	YEAR_ESTABLISHED			(5),
	SQUARE_FOOTAGE				(6),
	ACREAGE							(7),
	SELLING_REASON				(8),
	SUPPORT							(9),
	OWNER_FINANCING			(10),
	WEBSITE_URL					(11),
	FACEBOOK_URL					(12),
	TWITTER_URL					(13),
	TRIP_ADVISOR_URL			(14),
	FRONTAGE							(15),
	FINANCIAL_OTHER			(16),
	PROPERTY_OTHER				(17),
	OPERATION_OTHER			(18),
	OWNERS_RESIDENCE			(19),
	PROPERTY_TAX					(20),
	MULTIMEDIA_LINK			(21),
	AGENT_LISTING_LINK		(22)
	;
	
	private int id;
	
	MetaDataType(int id){
		this.setId(id);
	}
	
	private static final Map<Integer, MetaDataType> lookup = new HashMap<Integer, MetaDataType>();
	static {
		for (MetaDataType currEnum : EnumSet.allOf(MetaDataType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static MetaDataType get(int id) {
		return lookup.get(id);
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}