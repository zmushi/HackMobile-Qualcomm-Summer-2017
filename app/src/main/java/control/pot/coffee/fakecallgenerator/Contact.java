package control.pot.coffee.fakecallgenerator;

import android.provider.ContactsContract;

/**
 * Created by loker on 7/8/2017.
 */

//Class to handle loading contact info for a single contact
public class Contact {
    private String lookupKey;
    private String name;
    private String number;
    private long photoId;

    private static final String[] PROJECTION = {
              ContactsContract.Data._ID,
                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Photo.PHOTO_FILE_ID,
        };

    Contact(String lookupKey)   {
        load(lookupKey);
    }

    //TODO implement load() method stub
    public void load(String lookupKey)  {

    }

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
