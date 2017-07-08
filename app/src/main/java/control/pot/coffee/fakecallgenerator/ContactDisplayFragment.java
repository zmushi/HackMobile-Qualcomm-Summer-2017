package control.pot.coffee.fakecallgenerator;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ContactDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactDisplayFragment extends Fragment {
    private static final String ARG_NAME = "name";
    private static final String ARG_NUMBER = "number";
    private static final String ARG_PHOTO_ID = "photo";

    private String name;
    private String number;
    private Uri photo;


    public ContactDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactDisplayFragment newInstance(String name, String number, String photoStr) {
        ContactDisplayFragment fragment = new ContactDisplayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_NUMBER, number);
        args.putString(ARG_PHOTO_ID, photoStr);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME, "No name found");
            number = getArguments().getString(ARG_NUMBER);
            String photoStr = getArguments().getString(ARG_PHOTO_ID);
            photo = null;
            if (photoStr != null) photo = Uri.parse(photoStr);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_contact_display, container, false);

        ImageView imageView = (ImageView) v.findViewById(R.id.fragment_contact_display_photo_imageView);
        TextView nameTextView = (TextView) v.findViewById(R.id.fragment_contact_display_name_textView);
        TextView numberTextView = (TextView) v.findViewById(R.id.fragment_contact_display_number_textView);

        nameTextView.setText(name);
        numberTextView.setText(number);

        if (photo != null) {
            imageView.setImageURI(photo);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        else {
            Log.v(TAG, "Image is Null");
        }

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
}
