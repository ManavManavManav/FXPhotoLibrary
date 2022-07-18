package app;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * The class User implements serializable
 *  @author Manankumar Patel mpp128
 *  @author Manav Gohil mrg225
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	String userName="";

	private ArrayList<Album> albumList=new ArrayList<Album>();
	/**
	 *
	 * User
	 *
	 * @param userName
	 */
	public User(String userName) {
		this.userName = userName;
	}
	/**
	 *
	 * Gets the username
	 *
	 * @return the username
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 *
	 * Sets the user name
	 *
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 *
	 * Add album to user
	 *
	 * @param album
	 */
	public void addAlbumToUser(Album album){
		albumList.add(album);
	}
	/**
	 *
	 * Gets the arraylist of album
	 *
	 * @return the albumlist
	 */
	public ArrayList<Album> getAlbumList() {
		return this.albumList;
	}
	/**
	 *
	 * Sets the albumlist
	 *
	 * @param albumList
	 */
	public void setAlbumList(java.util.ArrayList<Album> albumList) {
		this.albumList = albumList;
	}
}
