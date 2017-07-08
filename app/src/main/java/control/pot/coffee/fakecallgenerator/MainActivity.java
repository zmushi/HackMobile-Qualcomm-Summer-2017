package control.pot.coffee.fakecallgenerator;

import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements
        ContactsListFragment.ContactsListFragmentInterface {

    private FrameLayout fragmentContainer;
    private EditText searchEditText;


    //Variable and constants to track fragment states
    private String fragState = STATE_EMPTY;
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
        //TODO code method
        /*
        replace fragments
        create contact
        load contact
         */
    }

    //Called when search button is pressed
    public void searchContacts(View view) {
        String searchString = searchEditText.getText().toString().trim();
        ContactsListFragment fragmentList = ContactsListFragment.newInstance(searchString);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragState) {
            case STATE_LIST:
            case STATE_DISPLAY:
                //fragmentTransaction.replace(R.id.result_layout, fragmentList);
                break;
            case STATE_EMPTY:
            default:
                //fragmentTransaction.add(R.id.result_layout, fragmentList);
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
}
