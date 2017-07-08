package control.pot.coffee.fakecallgenerator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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
        Intent intent = new Intent(this, CallingActivity.class);
        startActivity(intent);
    }
}
