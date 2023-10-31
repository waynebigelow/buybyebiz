package ca.app.model.photo;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PhotoType{
										//id		isOption			i18n				vpWd		vpHt		bdWd		bdHt		width		height	dispWd		dispHt
	PROFILE						(1,			true,				"90.type.1",	"200",	"200",	"250",	"250",	400,		400,		"160",		"160"),
	GALLERY						(2,			true,				"90.type.2",	"200",	"200",	"250",	"250",	800,		800,		"100%",		"100%"),
	THEME							(3,			true,				"90.type.3",	"300",	"125",	"325",	"150",	2000,		400,		"100%",		"");

	private int id;
	private boolean isOption;
	private String i18n;
	private String viewPortWidth;
	private String viewPortHeight;
	private String boundaryWidth;
	private String boundaryHeight;
	private int photoWidth;
	private int photoHeight;
	private String displayWidth;
	private String displayHeight;
	
	private PhotoType(int id, boolean isOption,	 String i18n, String viewPortWidth, String viewPortHeight, 
			String boundaryWidth, String boundaryHeight, int photoWidth, int photoHeight, String displayWidth, String displayHeight) {
		this.id = id;
		this.isOption = isOption;
		this.i18n = i18n;
		this.viewPortWidth = viewPortWidth;
		this.viewPortHeight = viewPortHeight;
		this.boundaryWidth = boundaryWidth;
		this.boundaryHeight = boundaryHeight;
		this.photoWidth = photoWidth;
		this.photoHeight = photoHeight;
		this.displayWidth = displayWidth;
		this.displayHeight = displayHeight;
	}
	
	private static final Map<Integer, PhotoType> lookup = new HashMap<Integer, PhotoType>();
	static {
		for (PhotoType currEnum : EnumSet.allOf(PhotoType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static PhotoType get(int id) {
		return lookup.get(id);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}

	public boolean isOption() {
		return isOption;
	}
	public void setOption(boolean isOption) {
		this.isOption = isOption;
	}

	public String getI18n() {
		return i18n;
	}
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}

	public String getViewPortWidth() {
		return viewPortWidth;
	}
	public void setViewPortWidth(String viewPortWidth) {
		this.viewPortWidth = viewPortWidth;
	}

	public String getViewPortHeight() {
		return viewPortHeight;
	}
	public void setViewPortHeight(String viewPortHeight) {
		this.viewPortHeight = viewPortHeight;
	}

	public String getBoundaryWidth() {
		return boundaryWidth;
	}
	public void setBoundaryWidth(String boundaryWidth) {
		this.boundaryWidth = boundaryWidth;
	}

	public String getBoundaryHeight() {
		return boundaryHeight;
	}
	public void setBoundaryHeight(String boundaryHeight) {
		this.boundaryHeight = boundaryHeight;
	}

	public int getPhotoWidth() {
		return photoWidth;
	}
	public void setPhotoWidth(int photoWidth) {
		this.photoWidth = photoWidth;
	}

	public int getPhotoHeight() {
		return photoHeight;
	}
	public void setPhotoHeight(int photoHeight) {
		this.photoHeight = photoHeight;
	}

	public String getDisplayWidth() {
		return displayWidth;
	}
	public void setDisplayWidth(String displayWidth) {
		this.displayWidth = displayWidth;
	}

	public String getDisplayHeight() {
		return displayHeight;
	}
	public void setDisplayHeight(String displayHeight) {
		this.displayHeight = displayHeight;
	}
}