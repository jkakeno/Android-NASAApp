package com.example.nasapp.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.RoverImage.Annotation;
import com.example.nasapp.Model.RoverImage.PhotosItem;
import com.example.nasapp.R;

import java.util.ArrayList;

public class MarsImageFragment extends Fragment {
    private static final String TAG = MarsImageFragment.class.getSimpleName();
    private static final String ARG = "mars_image";

    View view;
    ImageView marsImage_iv;
    FrameLayout imageContainer;
    ImageButton edit_ib;
    ImageButton text_ib;
    Button share_bt;

    PhotosItem marsImage;
    InteractionListener listener;
    Bitmap bitmapStored;
    ArrayList<Annotation> annotationList = new ArrayList<>();


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
        marsImage_iv = view.findViewById(R.id.marsImage);
        imageContainer = view.findViewById(R.id.imageContainer);
        edit_ib = view.findViewById(R.id.edit_bt);
        text_ib=view.findViewById(R.id.text_bt);
        share_bt=view.findViewById(R.id.share_bt);


        /*Make image view non clickable.*/
//        marsImage_iv.setOnClickListener(null);

        /*Set the shared element transition name. The shared element of this transition is the holder.image view of the adapter which is replaced by this fragment.
        * The transition name must match the name given at the adapter.*/
        view.setTransitionName(String.valueOf(marsImage));

        /*Prepare shared element transition.*/
        Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        /*Start the enter transition of this fragment.*/
        startPostponedEnterTransition();

        Glide.with(getActivity()).load(marsImage.getImgSrc()).into(marsImage_iv);

        /*TODO: Add text and free drawing on the image.*/
        /*TODO: Flatten the image and create another bitmap and pass the final bitmap to activity.*/
        /*Project to study Project -> Meme_Maker ; Files -> CreateMemeActivity (Place edit text on image), MemeItemFragment, Meme, MemeAnnotations */

        text_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Enter text...",Toast.LENGTH_SHORT).show();
            }
        });

        edit_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Make drawing...",Toast.LENGTH_SHORT).show();
            }
        });

        edit_ib.setImageResource(R.drawable.ic_edit);

        marsImage_iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
//Get the x and y coordinates where user touched on image view
                    int touchX = (int) motionEvent.getX();
                    int touchY = (int) motionEvent.getY();

//                    int xRatio=bitmapStored.getWidth()/marsImage_iv.getWidth();
//                    int yRatio=bitmapStored.getHeight()/marsImage_iv.getHeight();
//
//                    int x = touchX * xRatio;
//                    int y = touchY * yRatio;
//
//                    int xRatio, yRatio;
//                    int xPos, yPos;
//                    if (bitmapStored.getWidth() >= marsImage_iv.getWidth()) {
//                        xRatio = bitmapStored.getWidth() / marsImage_iv.getWidth();
//                        xPos = (int) Math.floor(xRatio * motionEvent.getX());
//                    } else {
//                        xRatio = marsImage_iv.getWidth() / bitmapStored.getWidth();
//                        xPos = (int) Math.floor(xRatio * motionEvent.getX()-((marsImage_iv.getWidth() - bitmapStored.getWidth())/2));
//                    }
//
//                    if (bitmapStored.getHeight() >= marsImage_iv.getHeight()) {
//                        yRatio = bitmapStored.getHeight() / marsImage_iv.getHeight();
//                        yPos = (int)Math.floor(motionEvent.getY()*yRatio);
//                    } else {
//                        yRatio = marsImage_iv.getHeight() / bitmapStored.getHeight();
//                        yPos = (int) Math.floor(yRatio * motionEvent.getY()-((marsImage_iv.getHeight() - bitmapStored.getHeight())/2));
//                    }

                    /*TODO: Match x and y with bitmap size.*/
                    /*https://stackoverflow.com/questions/27945642/ontouch-event-coordinates-and-imageview-draw-coordinates-mismatch*/
                    addEditTextOverImage("Title", touchX, touchY, Color.RED);

                    return false;
                } else {
                    return true;
                }
            }
        });

        /*Create a bitmap from url and pass the bitmap to  activity.*/
        Glide.with(getActivity())
                .load(marsImage.getImgSrc())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(500,500) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        // loaded bitmap is here (bitmap)
                        bitmapStored=bitmap;
                    }
                });

        share_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Annotation> annotationList = marsImage.getAnnotationList();
                Bitmap editedBitmap = flattenImage(bitmapStored,annotationList);
                listener.onMarsImageShareInteraction(editedBitmap);

            }
        });

        return view;
    }

    private Bitmap flattenImage(Bitmap bitmapStored, ArrayList<Annotation> annotationList) {
        //Make sure the bitmap is mutable
        Bitmap editedBitmap = bitmapStored.copy(Bitmap.Config.ARGB_8888, true);
        //Get the scale of the currently display screen
        float scale = getActivity().getResources().getDisplayMetrics().density;
//Create a canvas frame to place the text on top of the bitmap
        Canvas canvas = new Canvas(editedBitmap);
//Create a paint object that holds text color and size
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        for(Annotation annotation:annotationList){
            paint.setColor(Color.RED);
            paint.setTextSize(12 * scale);
//Create a rectangle to place the text
            Rect bounds = new Rect();
//Get the text from edit text
            String text = annotation.getText();
//Place the text in the box
            paint.getTextBounds(text, 0, text.length(), bounds);
            int x = annotation.getTextLocationX();
            int y = annotation.getTextLocationY();
//Place the text rectagle at location touchX and touchY with the text on the canvas
            canvas.drawText(text,x, y, paint);
        }

        return editedBitmap;
    }


    private void addEditTextOverImage(String title, int x, int y, int color) {
        //Create an edit text
        EditText editText = new EditText(getActivity());
//Set a default text in the edit text
        editText.setText(title);
        editText.setBackground(null);
        editText.setTextColor(color);
//Create edit text layout parameters
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(x, y, 0, 0);
        editText.setLayoutParams(layoutParams);
//Add edit text to container
        imageContainer.addView(editText);
//Request focus on edit text so user can edit edit text
        editText.requestFocus();
//Store the annotation in the mars image object
        Annotation annotation = new Annotation(editText.getText().toString(),x,y, color);
        annotationList.add(annotation);
        marsImage.setAnnotationList(annotationList);
//Store the edit text location in a list
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
