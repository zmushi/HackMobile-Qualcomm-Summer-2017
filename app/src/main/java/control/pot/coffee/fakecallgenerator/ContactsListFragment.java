package control.pot.coffee.fakecallgenerator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
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
                    // The primary display name
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Data.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Data.DISPLAY_NAME,
                    // The contact's _ID, to construct a content URI
                    ContactsContract.Data.CONTACT_ID,
                    // The contact's LOOKUP_KEY, to construct a content URI
                    ContactsContract.Data.LOOKUP_KEY
            };

    //Selection criteria for search
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";

    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = { mSearchString };

    long CONTACT_ID_INDEX = 0;
    long CONTACT_KEY_INDEX = 1;

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
        mContactId = cursor.getLong((int)CONTACT_ID_INDEX);
        // Get the selected LOOKUP KEY
        mContactKey = cursor.getString((int)CONTACT_KEY_INDEX);
        // Create the contact's content Uri
        mContactUri = ContactsContract.Contacts.getLookupUri(mContactId, mContactKey);
        /*
         * You can use mContactUri as the content URI for retrieving
         * the details for a contact.
         */
        onContactPressed(mContactUri);
    }

    public void onContactPressed(Uri uri) {
        if (mListener != null) {
            mListener.onContactClicked(uri);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */

        mSelectionArgs[0] = "%" + mSearchString + "%";
        // Starts the query
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
        );
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
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
    public void onNewSearchString(String searchString)   {
        mSearchString = searchString;
    }

    public interface ContactsListFragmentInterface {
        // TODO: Update argument type and name
        void onContactClicked(Uri uri);
    }
}