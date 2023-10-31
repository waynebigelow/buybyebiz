package ca.app.util;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import ca.app.service.common.TokenFieldType;

public class AssetFolderUtil {
	
	public enum AssetFolder {
		APPLICATION						(10),
		LISTING								(15),
		PHOTOS								(20),
		VIDEOS									(30),
		VIDEO_THUMBNAILS				(35),
		DOCUMENTS							(40);
		
		private int id;
		private static final Map<Integer,AssetFolder> lookup = new HashMap<Integer,AssetFolder>();
		
		AssetFolder(int id){
			this.setId(id);
		}
		
		static {
			for (AssetFolder currEnum : EnumSet.allOf(AssetFolder.class)) {
				lookup.put(currEnum.getId(), currEnum);
			}
		}
		
		public static AssetFolder get(int id) {
			return lookup.get(id);
		}

		public void setId(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public static String getPath(AssetFolder type, String appName, int listingId, boolean fileSystemAccess) {
		String path = "";
		String fileSeperator = (fileSystemAccess) ? File.separator : "/";
		
		switch(type) {
		case APPLICATION:
			path = "buybyemedia" + fileSeperator + "app" + fileSeperator + appName.toLowerCase();
			break;
			
		case LISTING:
			path = getPath(AssetFolder.APPLICATION, appName, listingId, fileSystemAccess);
			path += fileSeperator + "listing" + fileSeperator + RequestUtil.getToken(TokenFieldType.LISTING.getKey(), listingId);
			break;
			
		case PHOTOS:
			path = getPath(AssetFolder.LISTING, appName, listingId, fileSystemAccess);
			path += fileSeperator + "photo";
			break;
			
		case VIDEOS:
			path = getPath(AssetFolder.LISTING, appName, listingId, fileSystemAccess);
			path += fileSeperator + "video";
			break;
			
		case VIDEO_THUMBNAILS:
			path = getPath(AssetFolder.VIDEOS, appName, listingId, fileSystemAccess);
			path += fileSeperator + "thumbnails";
			break;
			
		case DOCUMENTS:
			path = getPath(AssetFolder.LISTING, appName, listingId, fileSystemAccess);
			path += fileSeperator + "documents";
			break;
		}
		
		return path;
	}
	
	public static void createAssetFolder(String appName, int listingId) throws IOException {
		String baseDir = ProjectUtil.getProperty("upload.location");
		String photoPath = AssetFolderUtil.getPath(AssetFolder.PHOTOS, appName, listingId, true);

		new File(baseDir + File.separator + photoPath).mkdirs();
	}
}