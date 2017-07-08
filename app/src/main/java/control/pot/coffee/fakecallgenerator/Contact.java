package control.pot.coffee.fakecallgenerator;

/**
 * Created by loker on 7/8/2017.
 */

//Class to handle loading contact info for a single contact
public class Contact {
    private String lookupKey;
    private String name;
    private String number;
    private long photoId;

    Contact(String lookupKey)   {
        load(lookupKey);
    }

    //TODO implement load() method stub
    public void load(String lookupKey)  {}

    public String getName() {
        return name;
    }

    public String getNumber()   {
        return number;
    }

    public long getPhotoId() {
        return photoId;
    }
}
