package control.pot.coffee.fakecallgenerator;

import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements
        ContactsListFragment.ContactsListFragmentInterface {

    private EditText searchEditText;
    private String searchString;


    //Variable and constants to track fragment states
    private String fragState  = STATE_EMPTY;
    private static final String STATE_EMPTY     = "empty";      //No fragment displayed
    private static final String STATE_LIST      = "list";       //ContactsListFragment displayed
    private static final String STATE_DISPLAY   = "display";    //ContactDisplayFragment displayed


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = (EditText)findViewById(R.id.search_field);
    }

    //called when contact is pressed in ContactsListFragment
    public void onContactClicked(String lookupKey)  {
        SharedPreferences sharedPrefs = getPreferences(0);
        String name   = sharedPrefs.getString(Constants.PREFS_KEY_CONTACT_MAIN_NAME, null);
        String number = sharedPrefs.getString(Constants.PREFS_KEY_CONTACT_MAIN_NUMBER, null);
        int photo = Integer.parseInt(sharedPrefs.getString(Constants.PREFS_KEY_CONTACT_MAIN_PHOTO, null));


        ContactDisplayFragment fragmentDisplay =
                ContactDisplayFragment.newInstance(name, number, photo);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragState) {
            case STATE_LIST:
            case STATE_DISPLAY:
                fragmentTransaction.replace(R.id.result_layout, fragmentDisplay);
                break;
            case STATE_EMPTY:
            default:
                fragmentTransaction.add(R.id.result_layout, fragmentDisplay);
                break;
        }
        fragmentTransaction.commit();
        fragState = STATE_DISPLAY;
    }

    //Called when search button is pressed
    public void searchContacts(View view) {
        updateSearchString();
        ContactsListFragment fragmentList = ContactsListFragment.newInstance(searchString);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragState) {
            case STATE_LIST:
            case STATE_DISPLAY:
                fragmentTransaction.replace(R.id.result_layout, fragmentList);
                break;
            case STATE_EMPTY:
            default:
                fragmentTransaction.add(R.id.result_layout, fragmentList);
                break;
        }
        fragmentTransaction.commit();
        fragState = STATE_LIST;
    }

    //Called when call button is pressed
    public void placeCall(View view) {
        Intent intent = new Intent(this, CallingActivity.class);
        startActivity(intent);
    }

    private void updateSearchString()   {
        searchString = searchEditText.getText().toString().trim();
    }
}
