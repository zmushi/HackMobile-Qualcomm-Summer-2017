package control.pot.coffee.fakecallgenerator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactsListFragment.ContactsListFragmentInterface} interface
 * to handle interaction events.
 * Use the {@link ContactsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener{
    private static final String TAG = "ContactsListFragment";

    private ContactsListFragmentInterface mListener;

    // Defines a variable for the search string
    private String mSearchString;

    ListView mContactsList;
    private SimpleCursorAdapter mCursorAdapter;

    Long mContactId;
    String mContactKey;
    Uri  mContactUri;

    //Column from cursor and which views to bing them too
    @SuppressLint("InlinedApi")
    private static final String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };

    private final static int[] TO_IDS = {
            R.id.contacts_list_item_name_textView
    };


    //Which columns in contacts to search
    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
            /*
             * The detail data row ID. To make a ListView work,
             * this column is required.
             */
                    ContactsContract.Data._ID,
                    ContactsContract.Data.LOOKUP_KEY,
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Photo.PHOTO_URI
            };

    //Selection criteria for search
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";

    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = { mSearchString };

    int CONTACT_ID_INDEX = 0;
    int CONTACT_KEY_INDEX = 1;
    int CONTACT_KEY_NAME = 2;
    int CONTACT_KEY_NUMBER = 3;
    int CONTACT_KEY_PHOTO = 4;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SEARCH_STRING= "ContactsListFragmentArgSearchString";

    public ContactsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param searchString Parameter 1.
     * @return A new instance of fragment ContactsListFragment.
     */
    public static ContactsListFragment newInstance(String searchString) {
        ContactsListFragment fragment = new ContactsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_STRING, searchString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchString = getArguments().getString(ARG_SEARCH_STRING);
        }
    }

    //Set adapter for list view when activity is created
    public void onActivityCreated(Bundle savedInstanceState)    {
        super.onActivityCreated(savedInstanceState);
        mContactsList = (ListView) getActivity().findViewById(R.id.contacts_list_view);
        mCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.fragment_contacts_list_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        mContactsList.setAdapter(mCursorAdapter);
        mContactsList.setOnItemClickListener(this);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContactsListFragmentInterface) {
            mListener = (ContactsListFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onItemClick(
            AdapterView<?> parent, View item, int position, long rowID) {


        // Get the Cursor
        Cursor cursor = mCursorAdapter.getCursor();
        // Move to the selected contact
        cursor.moveToPosition(position);
        // Get the _ID value
        mContactId = cursor.getLong(CONTACT_ID_INDEX);
        // Get the selected LOOKUP KEY
        mContactKey = cursor.getString(CONTACT_KEY_INDEX);
        // Create the contact's content Uri
        mContactUri = ContactsContract.Contacts.getLookupUri(mContactId, mContactKey);
        /*
         * You can use mContactUri as the content URI for retrieving
         * the details for a contact.
         */


        Log.i(TAG, "Attempting to get data contact info from cursor");

        //Extract contact details from cursor
        String name = cursor.getString(CONTACT_KEY_NAME);
        String number = cursor.getString(CONTACT_KEY_NUMBER);
        String photoStr = cursor.getString(CONTACT_KEY_PHOTO);

        Log.i(TAG, "Got data contact info from cursor");
        Log.v(TAG, name + " | " + number + " | " + photoStr);


        //put contact details in shared prefs
        SharedPreferences sharedPrefs = getActivity().getPreferences(0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(Constants.PREFS_KEY_CONTACT_MAIN_NAME, name);
        editor.putString(Constants.PREFS_KEY_CONTACT_MAIN_NUMBER, number);
        editor.putString(Constants.PREFS_KEY_CONTACT_MAIN_PHOTO, photoStr);
        editor.commit();

        onContactPressed(mContactKey);
    }

    public void onContactPressed(String lookupKey) {
        if (mListener != null) {
            mListener.onContactClicked(lookupKey);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */

        Log.v(TAG, "Constructing new cursor");
        for (int i = 0; i < PROJECTION.length; i++) {
            Log.v(TAG, "Projection col " + i + " : " + PROJECTION[i]);
        }

        mSelectionArgs[0] = "%" + mSearchString + "%";
        // Starts the query
        return new CursorLoader(
                getActivity(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
        );
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
        Log.i(TAG, "Cursor has finished loading");
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        mCursorAdapter.swapCursor(null);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    //TODO code onNewSearchString method stub
    public void onNewSearchString(String searchString)   {
        mSearchString = searchString;
    }

    public interface ContactsListFragmentInterface {
        // TODO: Update argument type and name
        void onContactClicked(String lookupKey);
    }
}