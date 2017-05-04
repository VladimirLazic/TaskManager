package ra57_2014.pnrs1.rtrk.taskmanager.statistics;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import ra57_2014.pnrs1.rtrk.taskmanager.R;

/**
 * Created by ironm on 5/4/2017.
 */

public class CircleView extends View {

    private static final float RADIUS = 200.0f;

    private Paint mPaint = null;
    double angleOfHigh , angleOfLow , angleOfMedium;
    double[] currentAngles = new double[3];
    int percentOfHigh = 0 , percentOfMedium = 0 , percentOfLow = 0;
    float sumTotalOfTasks;
    String TAG = "CircleView: ";

    public CircleView(Context context , int tasks[]) {
        super(context);

        float numberOfHighPriority = tasks[2];
        float numberOfMedioumPrioriy = tasks[1];
        float numberOfLowPriority = tasks[0];

        sumTotalOfTasks = numberOfHighPriority + numberOfMedioumPrioriy + numberOfLowPriority;

        angleOfHigh = 360*(numberOfHighPriority/sumTotalOfTasks);
        angleOfMedium = 360*(numberOfMedioumPrioriy/sumTotalOfTasks);
        angleOfLow = 360*(numberOfLowPriority/sumTotalOfTasks);

        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

        for(double angle : currentAngles) {
            angle = 0;
        }

        new AnimationThread().execute();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int displayHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        mPaint.setColor(getContext().getResources().getColor(R.color.colorRed));
        canvas.drawCircle(getWidth()/2, getHeight()/4, RADIUS, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(65);
        canvas.drawText("High priority" , getWidth()/2 - 0.9f*RADIUS, getHeight()/4 + displayHeight/6 , mPaint);

        mPaint.setColor(getContext().getResources().getColor(R.color.colorGreen));
        canvas.drawCircle(getWidth()/4, getHeight()/2 + displayHeight/10, RADIUS, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(65);
        canvas.drawText("Low priority" , getWidth()/4 - 0.85f*RADIUS, getHeight()/2 + displayHeight/10 + displayHeight/6 , mPaint);

        mPaint.setColor(getContext().getResources().getColor(R.color.colorYellow));
        canvas.drawCircle(getWidth()/4 + displayWidth/2, getHeight()/2 + displayHeight/10, RADIUS, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(65);
        canvas.drawText("Medium priority" , getWidth()/4 + displayWidth/2 - 1.1f*RADIUS, getHeight()/2 + displayHeight/10 + displayHeight/6 , mPaint);

        RectF rektHigh = new RectF(getWidth()/2 - RADIUS , getHeight()/4 - RADIUS , getWidth()/2 + RADIUS , getHeight()/4 + RADIUS);
        RectF rektMedium = new RectF(getWidth()/4 + displayWidth/2 - RADIUS , getHeight()/2 + displayHeight/10 - RADIUS , getWidth()/4 + displayWidth/2 + RADIUS , getHeight()/2 + displayHeight/10 + RADIUS);
        RectF rektLow = new RectF(getWidth()/4 - RADIUS , getHeight()/2 + displayHeight/10 - RADIUS , getWidth()/4 + RADIUS , getHeight()/2 + displayHeight/10 + RADIUS);

        mPaint.setColor(getResources().getColor(R.color.colorBlue));
        canvas.drawArc(rektHigh , 0 , (float) currentAngles[2] , true , mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawText(Integer.toString(percentOfHigh) + "%" , getWidth()/2 - 0.20f*RADIUS, getHeight()/4 + 0.1f*RADIUS, mPaint);

        mPaint.setColor(getResources().getColor(R.color.colorBlue));
        canvas.drawArc(rektMedium , 0 , (float) currentAngles[1] , true , mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawText(Integer.toString(percentOfMedium) + "%" , getWidth()/4 + displayWidth/2 - 0.20f*RADIUS,  getHeight()/2 + displayHeight/10 + 0.1f*RADIUS ,mPaint);

        mPaint.setColor(getResources().getColor(R.color.colorBlue));
        canvas.drawArc(rektLow , 0 , (float) currentAngles[0] , true , mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawText(Integer.toString(percentOfLow) + "%" , getWidth()/4 - 0.20f*RADIUS, getHeight()/2 + displayHeight/10 + 0.1f*RADIUS , mPaint);
    }

    private class AnimationThread extends AsyncTask<Void , Void , Void> {

        double[] maxAngles = {angleOfLow , angleOfMedium , angleOfHigh};
        int indexOfMax;
        @Override
        protected void onPreExecute() {

            double highestAngle = maxAngles[0];
            indexOfMax = 0;
            for(int i = 0; i < maxAngles.length; i++) {
                if (highestAngle < maxAngles[i]) {
                    highestAngle = maxAngles[i];
                    indexOfMax = i;
                }
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
                while(currentAngles[indexOfMax] <= maxAngles[indexOfMax]) {
                    if (currentAngles[0] <= maxAngles[0]) {
                        currentAngles[0] = currentAngles[0] + 1.00;
                        percentOfHigh = (int) Math.round(currentAngles[0]/sumTotalOfTasks);
                    }
                    if (currentAngles[1] <= maxAngles[1]) {
                        currentAngles[1] = currentAngles[1] + 1.00;
                        percentOfMedium = (int) Math.round(currentAngles[1]/sumTotalOfTasks);
                    }
                    if (currentAngles[2] <= maxAngles[2]) {
                        currentAngles[2] = currentAngles[2] + 1.00;
                        percentOfLow = (int) Math.round(currentAngles[2]/sumTotalOfTasks);
                    }

                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(null);
                }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            invalidate();
        }
    }
}