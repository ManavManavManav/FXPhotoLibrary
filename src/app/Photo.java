package app;

import com.sun.javafx.fxml.builder.JavaFXImageBuilder;
import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.MILLISECOND;

/**
 * The class Photo implements serializable
 *  @author Manankumar Patel mpp128
 *  @author Manav Gohil mrg225
 */
public class Photo implements Serializable {


    private transient Image image;
    private String caption = "";
    private String path;
    private ArrayList<Tag> tags;


    private Date date;
    /**
     *
     * Photo
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    public Photo() throws IOException, ClassNotFoundException {
//        Calendar c=Calendar.getInstance();
//        c.set(MILLISECOND,0);
//        this.date=c.getTime();
    }
    /**
     *
     * Gets the date
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }
    /**
     *
     * Gets the path
     *
     * @return the path
     */
    public String getPath(){
        return this.path;
    }

    /**
     *
     * Gets the image
     *
     * @return the image
     * @throws   FileNotFoundException
     */

    public Image getImage() throws FileNotFoundException {
        if(image == null&&path!=null){
            setImage(path);
        }
        return this.image;
    }
    /**
     *
     * Sets the image
     *
     * @param location
     * @throws   FileNotFoundException
     */
    public void setImage(String location) throws FileNotFoundException {
        this.path = location;
        Path filePath = Paths.get(location);
        File t = new File(String.valueOf(filePath));
        Date temp2=new Date(t.lastModified());
        FileInputStream temp = new FileInputStream(t);
        image = new Image(temp);
        this.date=temp2;
    }
    /**
     *
     * Gets the tags
     *
     * @return the tags
     */
    public ArrayList<Tag> getTags() {
        if(this.tags==null){
            tags=new ArrayList<Tag>();
        }
    	return this.tags;
    }
    /**
     *
     * Sets the tag list
     *
     * @param tags
     */
    public void setTagList(ArrayList<Tag> tags) {
    	this.tags = tags;
    }
    /**
     *
     * Add tag list
     *
     * @param tag
     */
    public void addTagList(String tag, String tagValue) {
        if(this.tags==null){
            tags=new ArrayList<Tag>();
        }
    	for(int i = 0; i < tags.size(); i++) {
    		if(tags.get(i).getTag().equalsIgnoreCase(tag)&&tags.get(i).getTagValue().equalsIgnoreCase(tagValue)) return;
    	}
    	tags.add(new Tag(tag,tagValue));
    	return;
    }
    /**
     *
     * Remove tag list
     *
     * @param tag
     */
    public void removeTagList(String tag,String tagValue) {
        if(this.tags==null){
            tags=new ArrayList<Tag>();
        }
    	for(int i = 0; i < tags.size(); i++) {
    		if(tags.get(i).getTag().equalsIgnoreCase(tag)&&tags.get(i).getTagValue().equalsIgnoreCase(tagValue)) {
    			tags.remove(i); 
    			return;
    		}
    	}
    }
    /**
     *
     * Gets the caption
     *
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }
    /**
     *
     * Sets the caption
     *
     * @param caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }


}
