package ra57_2014.pnrs1.rtrk.taskmanager.statistics;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ra57_2014.pnrs1.rtrk.taskmanager.R;
import ra57_2014.pnrs1.rtrk.taskmanager.main.MainActivity;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        int[] numberOfTasks =  bundle.getIntArray("Tasks");

        CircleView circleView = new CircleView(getApplicationContext() , numberOfTasks);
        setContentView(circleView);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        setResult(Activity.RESULT_OK , intent);
    }
}
