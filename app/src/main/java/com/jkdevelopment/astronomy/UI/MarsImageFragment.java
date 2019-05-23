package com.jkdevelopment.astronomy.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jkdevelopment.astronomy.InteractionListener;
import com.jkdevelopment.astronomy.Model.RoverImage.Annotation;
import com.jkdevelopment.astronomy.Model.RoverImage.PhotosItem;
import com.jkdevelopment.astronomy.R;

import java.util.ArrayList;

public class MarsImageFragment extends Fragment {
    private static final String TAG = MarsImageFragment.class.getSimpleName();
    private static final String ARG = "mars_image";

    View view;
    ImageView marsImage_iv;
    RelativeLayout textContainer;
    RelativeLayout lineContainer;
    RadioButton add_line_rb;
    RadioButton add_text_rb;
    Button share_bt;
    TextView rover_tv;
    TextView camera_tv;
    TextView sol_tv;

    PhotosItem marsImage;
    InteractionListener listener;
    Bitmap bitmapStored;
    ArrayList<Annotation> annotationList = new ArrayList<>();
    InputMethodManager imm;

    String rover;
    String camera;
    String sol;

    public MarsImageFragment() {
        // Required empty public constructor
    }

    public static MarsImageFragment newInstance(PhotosItem marsImage) {
        MarsImageFragment fragment = new MarsImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG, marsImage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null) {
            marsImage = getArguments().getParcelable(ARG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view = inflater.inflate(R.layout.mars_image_fragment, container, false);
        marsImage_iv = view.findViewById(R.id.mars_image);
        textContainer = view.findViewById(R.id.textContainer);
        lineContainer = view.findViewById(R.id.lineContainer);
        add_line_rb = view.findViewById(R.id.add_line);
        add_text_rb =view.findViewById(R.id.add_text);
        share_bt=view.findViewById(R.id.share_image);
        rover_tv=view.findViewById(R.id.rover);
        camera_tv=view.findViewById(R.id.camera);
        sol_tv=view.findViewById(R.id.martian);


        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        /*Make fragment non clickable.*/
        view.setOnClickListener(null);

        /*Set the shared element transition name. The shared element of this transition is the holder.image view of the adapter which is replaced by this fragment.
        * The transition name must match the name given at the adapter.*/
        view.setTransitionName(String.valueOf(marsImage));

        /*Prepare shared element transition.*/
        Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.mars_image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        /*Start the enter transition of this fragment.*/
        startPostponedEnterTransition();

        /*Load the image on image view*/
        Glide.with(getActivity()).load(marsImage.getImgSrc()).into(marsImage_iv);

        /*Load the image info on image view*/
        rover=marsImage.getRover().getName();
        camera=marsImage.getCamera().getFullName();
        sol=String.valueOf(marsImage.getSol());
        rover_tv.setText(rover);
        camera_tv.setText(camera);
        sol_tv.setText(sol);

        add_text_rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.d(TAG,"Edit Text button: "+isChecked);
                if(isChecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_text_selected);
                    /*Get the touch coordinates of the text container*/
                    textContainer.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                                /*Get the x and y coordinates where user touched on image view*/
                                int touchX = (int) motionEvent.getX();
                                int touchY = (int) motionEvent.getY();

                                addEditTextOverImage(touchX, touchY, ContextCompat.getColor(getActivity(),R.color.red));

                                return false;
                            } else {
                                return true;
                            }
                        }
                    });
                }else{
                    /*When the add text button is not pressed set the text button background to normal and bring the line container to the fore ground so user can add line.*/
                    compoundButton.setBackgroundResource(R.drawable.ic_text);
                    lineContainer.bringToFront();
                }
            }
        });

        add_line_rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.d(TAG,"Edit Drawing button: "+isChecked);
                if(isChecked){
                    compoundButton.setBackgroundResource(R.drawable.ic_edit_selected);
                    /*Create an instance of the SketchSheetView class. This is the view where the lines will be placed.*/
                    /*NOTE: bitmapStore is stored in this fragment so any line drawn will be registered in the bitmapStored.*/
                    View lineView = new SketchSheetView(getActivity(),bitmapStored);
                    /*Add the line view to the line container.*/
                    lineContainer.addView(lineView);
                }else{
                    /*When the add line button is not pressed set the line button background to normal and bring the text container to the fore ground so user can add text.*/
                    compoundButton.setBackgroundResource(R.drawable.ic_edit);
                    textContainer.bringToFront();
                }
            }
        });


        /*Create a bitmap from url and pass the bitmap to  activity.*/
        /*https://stackoverflow.com/questions/37847987/glide-load-into-simpletargetbitmap-not-honoring-the-specified-width-and-height*/
        /*Important that the bitmap and the image view match size. Since image view size is approximatly 1100 x 1100.*/
        /*https://stackoverflow.com/questions/37847987/glide-load-into-simpletargetbitmap-not-honoring-the-specified-width-and-height*/
        Glide.with(getActivity())
                .load(marsImage.getImgSrc())
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>(1100,1100) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        /*loaded bitmap is here (bitmap)*/
                        bitmapStored=bitmap;
                    }
                });



        share_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Pass the stored bitmap so the bitmap can be flatten with text and image info.*/
                Bitmap editedBitmap = flattenImage(bitmapStored,annotationList);
                listener.onMarsImageShareInteraction(editedBitmap);

            }
        });

        return view;
    }

    private void addEditTextOverImage(final int x, final int y, final int color) {
        /*Create an edit text*/
        final EditText editText = new EditText(getActivity());
        /*Set edit text background and text color*/
        editText.setBackground(null);
        editText.setTextColor(color);
        /*Set edit text layout parameters*/
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(x, y, 0, 0);
        editText.setLayoutParams(layoutParams);
        /*Add edit text to container*/
        textContainer.addView(editText);
        /*Display the keyboard*/
        if(imm != null){
            imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        }
        /*Request focus on edit text so user can edit edit text*/
        editText.requestFocus();
        /*Make cursor visible on edit text*/
        editText.setMinWidth(40);
        /*Set DONE button on keyboard*/
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setSingleLine();

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                /*Once DONE button is clicked*/
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    /*Store the annotation in the mars image object*/
                    Annotation annotation = new Annotation(editText.getText().toString(),color,x,y);
                    /*Add annotation to a list*/
                    annotationList.add(annotation);
                    /*Hide the keyboard*/
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

    }

    private Bitmap flattenImage(Bitmap bitmapStored, ArrayList<Annotation> annotationList) {
        /*Get the scale of the currently display screen*/
        float scale = getActivity().getResources().getDisplayMetrics().density;
        /*Create a canvas frame to place the text on top of the bitmap*/
        Canvas canvas = new Canvas(bitmapStored);
        /*Create a paint object that holds text color and size*/
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        for(Annotation annotation:annotationList){
            /*Get the text from annotation*/
            String text = annotation.getText();
            /*Get the text location from annotations*/
            int x = annotation.getTextLocationX();
            int y = annotation.getTextLocationY();

            /*Create a rectangle to place the text*/
            Rect annonationBounds = new Rect();
            /*Set the paint color and text size*/
            paint.setColor(annotation.getColor());
            paint.setTextSize(15 * scale);
            /*Place the text in the box*/
            paint.getTextBounds(text, 0, text.length(), annonationBounds);
            /*Place the text rectangle with the text on the canvas at location touchX and touchY*/
            canvas.drawText(text,x, y, paint);
        }

        /*Create the text for each image info*/
        String roverName="Rover: "+rover;
        String cameraName="Camera: "+camera;
        String martianSol="Sol taken: "+sol;

        /*Create the x and y coordinates of the start of the text box where each image info will be placed with respect to the image view*/
        int[] coords = {0,0};
        marsImage_iv.getLocationOnScreen(coords);
        int imageBottom = coords[1]+marsImage_iv.getHeight();

        int solLocationY=imageBottom-200;
        int cameraLocationY=solLocationY-50;
        int roverLocationY=cameraLocationY-50;

        /*Create a rectangles to place image info text*/
        Rect roverBounds = new Rect();
        Rect cameraBounds = new Rect();
        Rect solBounds = new Rect();

        /*Set the paint color and text size*/
        paint.setColor(ContextCompat.getColor(getActivity(),R.color.green));
        paint.setTextSize(12 * scale);
        /*Place each image info in their respective box*/
        paint.getTextBounds(roverName, 0, roverName.length(), roverBounds);
        paint.getTextBounds(cameraName, 0, cameraName.length(), cameraBounds);
        paint.getTextBounds(martianSol, 0, martianSol.length(), solBounds);

        canvas.drawText(roverName,10, roverLocationY, paint);
        canvas.drawText(cameraName,10, cameraLocationY, paint);
        canvas.drawText(martianSol,10, solLocationY, paint);

        return bitmapStored;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        listener = (InteractionListener) context;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");


    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        listener = null;
    }

}
