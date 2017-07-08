package control.pot.coffee.fakecallgenerator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
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

    //Column from cursore and which views to bing them too
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

    ListView mContactsList;
    private SimpleCursorAdapter mCursorAdapter;

    Long mContactId;
    Long mContactKey;
    Uri mContactUri;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ContactsListFragmentInterface mListener;

    public ContactsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsListFragment newInstance(String param1, String param2) {
        ContactsListFragment fragment = new ContactsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onContactClicked(uri);
        }
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
    public interface ContactsListFragmentInterface {
        // TODO: Update argument type and name
        void onContactClicked(Uri uri);
    }
}
