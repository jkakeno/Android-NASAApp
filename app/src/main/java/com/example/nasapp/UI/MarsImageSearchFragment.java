package com.example.nasapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Rover;
import com.example.nasapp.Model.RoverList;
import com.example.nasapp.R;

public class MarsImageSearchFragment extends Fragment {
    private static final String TAG = MarsImageSearchFragment.class.getSimpleName();
    private static final String ARG = "rover_list";

    View view;
    GridView roverGrid;
    GridView cameraGrid;
    EditText sol_et;
    Button getMarsImage_bt;

//    ArrayList<Rover> roverList;
    InteractionListener listener;
    Rover rover;
    RoverList roverList;
    InputMethodManager imm;

    String[] roverNames = {"CURIOSITY","OPPORTUNITY","SPIRIT"};
    int[] roverImageIds ={R.drawable.rover_curiosity,R.drawable.rover_opportunity,R.drawable.rover_spirit};
    String[] cameraNames = {"All","fhaz","rhaz","mast","chemcam","mahli","mardi","navcam","pancam","minites"};
    String roverName;
    String solSetting;
    String cameraSetting;

    public MarsImageSearchFragment() {
        // Required empty public constructor
    }

    public static MarsImageSearchFragment newInstance(RoverList roverList) {
        MarsImageSearchFragment fragment = new MarsImageSearchFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG, roverList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        if (getArguments() != null) {
            roverList = getArguments().getParcelable(ARG);
        }
        rover=new Rover();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        view = inflater.inflate(R.layout.mars_image_search_fragment,container,false);

//        final RoverAdapter roverAdapter = new RoverAdapter(getActivity(),roverNames,roverImageIds);
        final RoverAdapter roverAdapter = new RoverAdapter(getActivity(),roverList);
        roverGrid=view.findViewById(R.id.rover_grid);
        roverGrid.setAdapter(roverAdapter);

//        for(Rover rover:roverList.getRoverList()){
//            rover.setSelected(false);
//        }

//        CameraAdapter cameraAdapter = new CameraAdapter(getActivity(),cameraNames);
        final CameraAdapter cameraAdapter = new CameraAdapter(getActivity(),roverList);
        cameraGrid=view.findViewById(R.id.camera_grid);
        cameraGrid.setAdapter(cameraAdapter);

        sol_et = view.findViewById(R.id.sol_setting_select);

        getMarsImage_bt = view.findViewById(R.id.get_mars_images);
        getMarsImage_bt.setVisibility(View.GONE);

        /*TODO: Persist selected item state.*/
        /*https://stackoverflow.com/questions/11326089/android-gridview-keep-item-selected*/

        roverGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                view.setSelected(true);
//                roverList.getRoverList().get(position).setSelected(true);
                roverAdapter.selectedRover =position;
                roverAdapter.notifyDataSetChanged();

                String roverName = roverList.getRoverList().get(position).getRoverName();
                rover.setRoverName(roverName);

//                rover.setRoverName(roverNames[position]);
//                roverName=roverNames[position];
                checkStartButtonVisibility();
                Toast.makeText(getActivity(), "You Clicked at " + roverName, Toast.LENGTH_SHORT).show();
            }
        });

        cameraGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                view.setSelected(true);

                cameraAdapter.selectedCamera =position;
                cameraAdapter.notifyDataSetChanged();

                String camera = roverList.getRoverList().get(0).getCameraList().get(position);
                rover.setCameraSetting(camera);

//                rover.setCameraSetting(cameraNames[position]);
//                cameraSetting=cameraNames[position];
                checkStartButtonVisibility();
                Toast.makeText(getActivity(), "You Clicked at " + camera, Toast.LENGTH_SHORT).show();
            }
        });

//        rover.setSolSetting(sol_ev.getText().toString());
//        solSetting=sol_ev.getText().toString();

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        sol_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {

                    rover.setSolSetting(sol_et.getText().toString());
                    checkStartButtonVisibility();
                    imm.hideSoftInputFromWindow(sol_et.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    private void checkStartButtonVisibility() {
        Log.d(TAG,"Rover name: " +rover.getRoverName());
        Log.d(TAG,"Camera: " + rover.getCameraSetting());
        Log.d(TAG,"Martian Sol: " + rover.getSolSetting());

        if(rover.getRoverName()==null||rover.getCameraSetting()==null||rover.getSolSetting()==null||rover.getSolSetting().isEmpty()){
            getMarsImage_bt.setVisibility(View.GONE);
        }else{
            getMarsImage_bt.setVisibility(View.VISIBLE);
            getMarsImage_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onGetRoverImageryInteraction(rover);
                }
            });
        }
//        if(roverName==null||cameraSetting==null||solSetting==null){
//            getMarsImage_bt.setVisibility(View.GONE);
//        }else{
//            getMarsImage_bt.setVisibility(View.VISIBLE);
//            getMarsImage_bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onGetRoverImageryInteraction(rover);
//                }
//            });
//        }
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
        listener=null;
    }
}
