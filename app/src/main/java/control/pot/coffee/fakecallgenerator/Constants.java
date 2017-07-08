package control.pot.coffee.fakecallgenerator;

/**
 * Created by loker on 7/7/2017.
 */

public class Constants {
    private static final String PREFIX = "control.pot.coffee.fakecallgenerator.";
    public static final String PREFS_KEY_CONTACT_MAIN_NAME      = PREFIX + "pref.main_contact_name";
    public static final String PREFS_KEY_CONTACT_MAIN_NUMBER    = PREFIX + "pref.main_contact_number";
    public static final String PREFS_KEY_CONTACT_MAIN_PHOTO     = PREFIX + "pref.main_contact_photo";

    public static final String EXTRA_KEY_NAME      = PREFIX + "extra_name";
    public static final String EXTRA_KEY_NUMBER    = PREFIX + "extra_number";
    public static final String EXTRA_KEY_PHOTO     = PREFIX + "extra_photo";
    public static final String EXTRA_KEY_INTERVAL     = PREFIX + "extra_interval";

    public static final String EXTRA_KEY_REPEATS = PREFIX + "extra_repeats";

    public static final String ACTION_CALL  = PREFIX + "action_call";
    public static final String ACTION_WIDGET_CLICK = PREFIX + "action_widget_click";


    public static final String PREFS_WIDGET_NAME = PREFIX + "pref.widget.name";
    public static final String PREFS_WIDGET_ID = PREFIX + "pref.widget_id";
    public static String PREFS_WIDGET_NAME(String str)      { return PREFIX + "pref.widget_name.id=" + str;}
    public static String PREFS_WIDGET_NUMBER(String str)    { return PREFIX + "pref.widget_number.id=" + str;}
    public static String PREFS_WIDGET_PHOTO(String str)     { return PREFIX + "pref.widget_photo.id=" + str;}
    public static String PREFS_WIDGET_DELAY(String str)     { return PREFIX + "pref.widget_delay.id=" + str;}
    public static String PREFS_WIDGET_INTERVAL(String str)  { return PREFIX + "pref.widget_interval.id=" + str;}
    public static String PREFS_WIDGET_REPEATS(String str)   { return PREFIX + "pref.widget_repeats.id=" + str;}
}
