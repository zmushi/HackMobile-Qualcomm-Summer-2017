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
        //TODO code method stub
        String searchString = searchEditText.getText().toString().trim();
        ContactsListFragment fragment = ContactsListFragment.newInstance(searchString);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(R.id.result_layout, fragment);
        fragmentTransaction.commit();

    }
    //Called when call button is pressed
    public void placeCall(View view) {
        Intent intent = new Intent(this, CallingActivity.class);
        startActivity(intent);
    }
}
