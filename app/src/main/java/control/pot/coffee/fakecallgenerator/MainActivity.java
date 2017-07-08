package control.pot.coffee.fakecallgenerator;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ContactsListFragment.ContactsListFragmentInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Method called when ContactListFragment has a contact clicked
    public void onContactClicked(String lookupKey)    {
        
    }
}
