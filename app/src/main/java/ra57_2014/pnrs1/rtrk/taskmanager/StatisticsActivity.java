package ra57_2014.pnrs1.rtrk.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ra57_2014.pnrs1.rtrk.taskmanager.main.MainActivity;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(StatisticsActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }
}
