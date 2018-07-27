package com.example.nasapp.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.nasapp.R;

import java.util.ArrayList;

public class SketchSheetView extends View {

    Canvas canvas;
    Path linePath;
    Paint paint;

    public SketchSheetView(Context context, Bitmap bitmap) {
        super(context);

        /*Create the canvas to draw. Passing the bitmap set the canvas to the bitmap size.*/
        canvas = new Canvas(bitmap);
        /*Create a line path object.*/
        linePath = new Path();
        /*Create paint object and set parameters for the line. */
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.red));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5);

        /*Set the background of this view transparent so the bitmap image can be seen.*/
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    private ArrayList<DrawingClass> DrawingClassArrayList = new ArrayList<DrawingClass>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /*Create a drawing object. This object will hold the path and paint of each drawing attempts.*/
        DrawingClass pathWithPaint = new DrawingClass();

        /*Draw the path onto the canvas which is attached to the bitmap.*/
        /*Important: this line draws the line on the bitmap.*/
        canvas.drawPath(linePath, paint);

        /*Start drawing line when the user touches the screen.*/
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            linePath.moveTo(event.getX(), event.getY());

            linePath.lineTo(event.getX(), event.getY());
        }
        /*Continue drawing line as long as the user doesn't lift finger from screen.*/
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            linePath.lineTo(event.getX(), event.getY());

            /*Store the path in a list.*/
            pathWithPaint.setPath(linePath);
            pathWithPaint.setPaint(paint);
            DrawingClassArrayList.add(pathWithPaint);
        }
        /*This line calls onDraw.*/
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (DrawingClassArrayList.size() > 0) {
            /*Draw the last path in the path list on the canvas. This line draws the line on the screen.*/
            canvas.drawPath(
                    DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPath(),
                    DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPaint());
        }
    }
}
