package com.example.nasapp.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.LibraryImage.LibraryImageCollection;
import com.example.nasapp.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ImageSearchFragment extends Fragment {
    private static final String TAG = ImageSearchFragment.class.getSimpleName();
    private static final String ARG = "library_image_collection";
    private static final int SPEECH_REQUEST_CODE = 0;

    View view;
    EditText searchBox_et;
    ImageButton voice_bt;
    ImageButton search_bt;

    LibraryImageCollection libraryImageCollection;
    InteractionListener listener;
    InputMethodManager imm;

    String keyword;

    public ImageSearchFragment() {
        // Required empty public constructor
    }

    public static ImageSearchFragment newInstance(LibraryImageCollection libraryImageCollection) {
        ImageSearchFragment fragment = new ImageSearchFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG, libraryImageCollection);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        if (getArguments() != null) {
            libraryImageCollection = getArguments().getParcelable(ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        view = inflater.inflate(R.layout.image_search_fragment,container,false);
        searchBox_et = view.findViewById(R.id.search_box);
        voice_bt = view.findViewById(R.id.voice_bt);
        search_bt = view.findViewById(R.id.search_bt);

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
        listener = (InteractionListener) context;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");

        searchBox_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    keyword = (searchBox_et.getText().toString());
                    searchBox_et.setText(keyword);
                    imm.hideSoftInputFromWindow(searchBox_et.getWindowToken(), 0);
                    searchBox_et.setCursorVisible(false);

                    return true;
                }
                return false;
            }
        });

        voice_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                // Start the activity, the intent will be populated with the speech text
                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            }
        });

        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSearchImageryInteraction(keyword);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
            keyword = spokenText;

            searchBox_et.setText(keyword);
            imm.hideSoftInputFromWindow(searchBox_et.getWindowToken(), 0);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
        listener=null;
    }
}
