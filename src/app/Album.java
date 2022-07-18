package app;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * The class Album implements serializable
 * @author Manankumar Patel mpp128
 * @author Manav Gohil mrg225
 */
public class Album implements Serializable{
	private ArrayList<Photo> photoList = new ArrayList<>();
	private String albumName;

	/**
	 *
	 * Gets the albumname
	 *
	 * @return albumname
	 */
	public String getAlbumname() {
		return albumName;
	}
	/**
	 *
	 * Album
	 *
	 * @param albumName
	 */
	public Album(String albumName) {
		this.albumName = albumName;
	}
	/**
	 *
	 * Sets the albumname
	 *
	 * @param albumname
	 */
	public void setAlbumname(String albumname) {
		albumName = albumname;
	}

	private static final long serialVersionUID = 1L;
	/**
	 *
	 * Gets the photo list
	 *
	 * @return the photolist
	 */
	public ArrayList<Photo> getPhotoList() {
		return photoList;
	}
	/**
	 *
	 * Sets the photo list
	 *
	 * @param photoList
	 */
	public void setPhotoList(ArrayList<Photo> photoList) {
		this.photoList = photoList;
	}
	/**
	 *
	 * Add photo to album
	 *
	 * @param p
	 */
	public void addPhotoToAlbum(Photo p){
		Photo temp=p;
		photoList.add(temp);
	}

}
