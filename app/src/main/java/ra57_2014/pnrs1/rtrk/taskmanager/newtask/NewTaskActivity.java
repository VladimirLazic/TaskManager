package ra57_2014.pnrs1.rtrk.taskmanager.newtask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ra57_2014.pnrs1.rtrk.taskmanager.main.MainActivity;
import ra57_2014.pnrs1.rtrk.taskmanager.R;


public class NewTaskActivity extends AppCompatActivity implements NewTaskModel.View {

    EditText taskName , taskDescription;
    EditText taskTime;
    CheckBox reminder;
    Button red, green, yellow, add , cancel;
    NewTaskPresenter presenter;
    int priorityButton = 0;
    Task t;
    String TAG = "NewTaskActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        presenter = new NewTaskPresenter(this);

        initView();

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityButton = 3;
                yellow.setEnabled(false);
                green.setEnabled(false);

                if(!taskName.getText().toString().isEmpty() && !taskTime.getText().toString().isEmpty())
                    add.setEnabled(true);
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityButton = 2;
                red.setEnabled(false);
                green.setEnabled(false);

                if(!taskName.getText().toString().isEmpty() && !taskTime.getText().toString().isEmpty())
                    add.setEnabled(true);
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityButton = 1;
                red.setEnabled(false);
                yellow.setEnabled(false);

                if(!taskName.getText().toString().isEmpty() && !taskTime.getText().toString().isEmpty())
                    add.setEnabled(true);
            }
        });

        taskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!taskTime.getText().toString().isEmpty() && priorityButton != 0)
                    add.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!taskTime.getText().toString().isEmpty() && priorityButton != 0)
                    add.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        taskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                final int year , mMonth , day , hour , minute;

                day = currentTime.get(Calendar.DAY_OF_MONTH);
                mMonth = currentTime.get(Calendar.MONTH) + 1;
                year = currentTime.get(Calendar.YEAR);
                hour = currentTime.get(Calendar.HOUR_OF_DAY);
                minute = currentTime.get(Calendar.MINUTE);

                if(taskTime.getText().toString().isEmpty()) {
                    DatePickerDialog date = new DatePickerDialog(NewTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            taskTime.setText(dayOfMonth + "/" + month + "/" + year);
                        }
                    }, year, mMonth, day);
                    date.setTitle("Select the date");
                    date.show();
                }

                if(taskTime.getText().toString().indexOf("/") != -1) {
                    TimePickerDialog time = new TimePickerDialog(NewTaskActivity.this , new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            taskTime.setText(taskTime.getText().toString() + (" " + hourOfDay + ":" + minute));
                        }
                    }, hour , minute , true);
                    time.setTitle("Select the time");
                    time.show();
                }

            }
        });

        taskTime.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!taskTime.getText().toString().isEmpty() && !taskName.getText().toString().isEmpty() && priorityButton != 0)
                    add.setEnabled(true);
                else
                    add.setEnabled(false);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add.getText().equals(getResources().getString(R.string.add))) {
                    presenter.addTask(taskName.getText().toString(),
                            taskDescription.getText().toString(),
                            taskTime.getText().toString(),
                            reminder.isChecked(),
                            priorityButton);

                    Log.d(TAG, "onClick: Adding new task");
                } else {
                    presenter.addTask(taskName.getText().toString(),
                            taskDescription.getText().toString(),
                            taskTime.getText().toString(),
                            reminder.isChecked(),
                            priorityButton);

                    Log.d(TAG, "onClick: Saving task");
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cancel.getText().equals(getResources().getString(R.string.cancel))) {
                    priorityButton = 0;

                    taskName.getText().clear();
                    taskDescription.getText().clear();
                    taskTime.getText().clear();
                    reminder.setChecked(false);

                    add.setEnabled(false);

                    red.setEnabled(true);
                    yellow.setEnabled(true);
                    green.setEnabled(true);
                } else {
                    Log.d(TAG, "onClick: Deleting task from database");
                    deleteTask(t);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NewTaskActivity.this , MainActivity.class);
        setResult(Activity.RESULT_FIRST_USER , intent);
    }

    private void initView() {
        taskName = (EditText) findViewById(R.id.taskName);
        taskDescription = (EditText) findViewById(R.id.taskDescription);
        taskTime = (EditText) findViewById(R.id.taskTime);

        reminder = (CheckBox) findViewById(R.id.reminder);

        red = (Button) findViewById(R.id.red);
        yellow = (Button) findViewById(R.id.yellow);
        green = (Button) findViewById(R.id.green);
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);

        Bundle extras = getIntent().getExtras();

        cancel.setText(extras.getString("Right"));
        add.setText(extras.getString("Left"));

        if(add.getText().equals(getResources().getString((R.string.add)))) {
            add.setEnabled(false);
        } else {
            add.setEnabled(true);
        }

        if(extras.get("Task") != null) {
            t = ((Task) extras.get("Task"));
            taskName.setText(t.getName());
            taskTime.setText(t.getTime());
            taskDescription.setText(t.getDescription());

            if(t.isReminder()) {
                reminder.setChecked(true);
            } else {
                reminder.setChecked(false);
            }

            switch (t.getPriority()) {
                case 1:
                    green.setEnabled(true);
                    red.setEnabled(false);
                    yellow.setEnabled(false);
                    break;
                case 2:
                    green.setEnabled(false);
                    red.setEnabled(false);
                    yellow.setEnabled(true);
                    break;
                case 3:
                    green.setEnabled(false);
                    red.setEnabled(true);
                    yellow.setEnabled(false);
                    break;
            }
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this , message , Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateList(Task t) {
        Intent intent = new Intent(getBaseContext() , MainActivity.class);
        intent.putExtra("Task" , t);
        setResult(Activity.RESULT_OK , intent);

        finish();
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(NewTaskActivity.this , MainActivity.class);
        startActivity(intent);
        //finish();
    }

    @Override
    public void deleteTask(Task t) {
        Intent intent = new Intent(getBaseContext() , MainActivity.class);
        intent.putExtra("Task" , t);
        setResult(Activity.RESULT_CANCELED , intent);

        finish();
    }


}
