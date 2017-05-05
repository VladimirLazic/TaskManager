package ra57_2014.pnrs1.rtrk.taskmanager.statistics;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import ra57_2014.pnrs1.rtrk.taskmanager.R;
import ra57_2014.pnrs1.rtrk.taskmanager.main.MainActivity;

public class StatisticsActivity extends AppCompatActivity {

    RelativeLayout statisticLayout;
    Button backButton;
    CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Bundle bundle = getIntent().getExtras();
        int[] numberOfTasks =  bundle.getIntArray("Tasks");

        circleView = new CircleView(getApplicationContext() , numberOfTasks);
        statisticLayout = (RelativeLayout) findViewById(R.id.statLayout);
        backButton = (Button) findViewById(R.id.button);

        statisticLayout.addView(circleView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this , MainActivity.class);
                setResult(Activity.RESULT_OK , intent);
                circleView.animationThread.cancel(true);
                finish();
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        circleView.animationThread.cancel(true);
        Intent intent = new Intent(StatisticsActivity.this , MainActivity.class);
        setResult(Activity.RESULT_OK , intent);
        finish();
    }
}
