package app;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * The class Tag implements serializable
 *  @author Manankumar Patel mpp128
 *  @author Manav Gohil mrg225
 */
public class Tag implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tagName;
	private String tagValue;
	/**
	 *
	 * Tag
	 *
	 * @param tagName
	 */
	public Tag(String tagName, String tagValue) {

		this.tagName = tagName;
		this.tagValue=tagValue;
	}

	/**
	 *
	 * Gets the tag
	 *
	 * @return the tag
	 */
	public String getTag() {
		return this.tagName;
	}
	public String getTagValue(){
		return this.tagValue;
	}
	/**
	 *
	 * Sets the tag
	 *
	 * @param TagName
	 */
	public void setTag(String TagName, String TagValue) {

		this.tagName = tagName;
		this.tagValue=TagValue;
	}
	
}
