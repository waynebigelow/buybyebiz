package ca.app.service.usage; 

import ca.app.model.listing.Listing;
import ca.app.model.usage.RefererType;
import ca.app.util.StringUtil;

public class RefererParsingServiceImpl implements RefererParsingService {
	
	@Override
	public RefererType parseReferer(Listing listing, String rawReferer, String source, String s) {
		// The new obfuscated way
		if (s != null && s.length() > 0) {
			int refererTypeId = StringUtil.convertStringToInt(s, 0);
			if (refererTypeId > 0) {
				RefererType type = RefererType.get(refererTypeId);
				if (type != null) {
					return type;
				}
			}
		}
		
		// The old verbose way, which we still need to support since emails that use this method are out in the wild.
		if (source != null && source.length() > 0) {
			return RefererType.getBySource(source);
		}
		
		if (rawReferer != null && rawReferer.length() > 0) {
		
			// TODO - need to see some real data before continuing this.  May not give enough ROI to even bother.
			
//			/** Split referer into pieces */
//			String str = rawReferer;
//			String protocol = null;
//			String host = null;
//			String resource = null;
//			String queryStr = null;
//			
//			// Query string
//			int idx = str.lastIndexOf('?');
//			if (idx>0) {
//				queryStr = str.substring(idx);
//				str = str.substring(0,idx);
//			}
//			
//			// Protocol
//			idx = str.indexOf("://");
//			if (idx>0) {
//				protocol = str.substring(0,idx);
//				str = str.substring(idx+3);
//			}
//			
//			// Host and resource
//			idx = str.indexOf("/");
//			if (idx>0) {
//				host = str.substring(0,idx);
//				resource = str.substring(idx);
//			} else {
//				host = str;
//				resource = "";
//			}
//			
//			System.out.println("[protocol=" + protocol + "][host=" + host + "][resource=" + resource + "][queryStr=" + queryStr + "]");
		}
		
		return RefererType.UNKNOWN;
	}
}  