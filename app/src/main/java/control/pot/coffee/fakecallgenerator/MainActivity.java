package control.pot.coffee.fakecallgenerator;


import android.app.Fragment;
import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements
        ContactsListFragment.ContactsListFragmentInterface,
        AdapterView.OnItemSelectedListener {

    private EditText searchEditText;
    private String searchString;
    private Contact contact;

    private static final String TAG  = "class";
    //Variable and constants to track fragment states
    private String fragState  = STATE_EMPTY;
    private static final String STATE_EMPTY     = "empty";      //No fragment displayed
    private static final String STATE_LIST      = "list";       //ContactsListFragment displayed
    private static final String STATE_DISPLAY   = "display";    //ContactDisplayFragment displayed

    private int repeat;
    private int delay;
    private int interval;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner repeat_spinner = (Spinner) findViewById(R.id.repeat_spinner);
        ArrayAdapter<CharSequence> r_adapter = ArrayAdapter.createFromResource(this,
                R.array.repeat_array, android.R.layout.simple_spinner_item);
        r_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeat_spinner.setAdapter(r_adapter);

        Spinner delay_spinner = (Spinner) findViewById(R.id.delay_spinner);
        ArrayAdapter<CharSequence> d_adapter = ArrayAdapter.createFromResource(this,
                R.array.delay_array, android.R.layout.simple_spinner_item);
        d_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        delay_spinner.setAdapter(d_adapter);

        Spinner interval_spinner = (Spinner) findViewById(R.id.interval_spinner);
        ArrayAdapter<CharSequence> i_adapter = ArrayAdapter.createFromResource(this,
                R.array.interval_array, android.R.layout.simple_spinner_item);
        i_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interval_spinner.setAdapter(i_adapter);

        searchEditText = (EditText)findViewById(R.id.search_field);
        repeat_spinner.setOnItemSelectedListener(this);
        delay_spinner.setOnItemSelectedListener(this);
        interval_spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch(parent.getId()) {
            case R.id.repeat_spinner: repeat = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                break;
            case R.id.delay_spinner: delay = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                break;
            case R.id.interval_spinner: interval = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Nothing needs to be done
    }

    //called when contact is pressed in ContactsListFragment
    public void onContactClicked(String lookupKey)  {
        //TODO code method
        /*
        replace fragments
        create contact
        load contact
         */

        contact = new Contact(lookupKey);

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
