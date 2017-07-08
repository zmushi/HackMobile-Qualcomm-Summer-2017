package control.pot.coffee.fakecallgenerator;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ContactsListFragment.ContactsListFragmentInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onContactClicked(String lookupKey)  {

    }

    public void searchContacts(View view) {

    }

    public void placeCall(View view) {

    }
}
