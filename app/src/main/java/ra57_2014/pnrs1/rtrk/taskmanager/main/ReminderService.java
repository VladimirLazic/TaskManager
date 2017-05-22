package ra57_2014.pnrs1.rtrk.taskmanager.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.FileDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ra57_2014.pnrs1.rtrk.taskmanager.R;
import ra57_2014.pnrs1.rtrk.taskmanager.newtask.Task;

/**
 * Created by ironm on 5/21/2017.
 */

public class ReminderService extends Service implements ServiceConnection {

    ArrayList<Task> tasks = new ArrayList<>();
    ReminderThread mThread = new ReminderThread();
    private static final String TAG = "ReminderService";

    public  ReminderService() {}
    
    private IBinder mBinder = new IBinder() {
        @Override
        public String getInterfaceDescriptor() throws RemoteException {
            return null;
        }

        @Override
        public boolean pingBinder() {
            return false;
        }

        @Override
        public boolean isBinderAlive() {
            return false;
        }

        @Override
        public IInterface queryLocalInterface(String descriptor) {
            return null;
        }

        @Override
        public void dump(FileDescriptor fd, String[] args) throws RemoteException {

        }

        @Override
        public void dumpAsync(FileDescriptor fd, String[] args) throws RemoteException {

        }

        @Override
        public boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return false;
        }

        @Override
        public void linkToDeath(DeathRecipient recipient, int flags) throws RemoteException {

        }

        @Override
        public boolean unlinkToDeath(DeathRecipient recipient, int flags) {
            return false;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mThread.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThread.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Bundle bundle = intent.getExtras();
        tasks = ((ArrayList<Task>) bundle.get("Task"));
        
        if(!tasks.isEmpty()) {
            for (Task t : tasks) {
                Log.d(TAG, "onBind: Task: " + t.toString());
            }
        } else {
            Log.d(TAG, "onBind: Tasks empty");
        }

        return mBinder;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        //mBinder = service;
        mThread.start();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mThread.stop();
    }

    class ReminderThread extends Thread {
        List<Task> remindOfTheseTasks = new ArrayList<Task>();
        Date currentDate;
        String datePattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);

        public ReminderThread() {}

        @Override
        public synchronized void start() {
            super.start();
        }

        @Override
        public void run() {
            currentDate = new Date();

            if(!tasks.isEmpty()) {
                for (Task t : tasks) {
                    Log.d(TAG, "run: Task: " + t.toString());
                }
            } else {
                Log.d(TAG, "run: Tasks empty");
            }

            
            while(true) {
                Log.d(TAG, "run: Checking tasks");
                //Check which tasks need to be reminded of
                for(Task t : tasks) {
                    Date taskDate = new Date();
                    String taskTime = t.getTime();
                    String dateAndTime[] = taskTime.split(" ");
                    String justTheDate[] = dateAndTime[0].split("/");
                    String hourAndMin[] = dateAndTime[1].split(":");

                    int hour = Integer.parseInt(hourAndMin[0]);
                    int minute = Integer.parseInt(hourAndMin[1]);

                    try {
                        taskDate = simpleDateFormat.parse(justTheDate[0]+"/"+justTheDate[1]+"/"+justTheDate[2]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(taskDate.getYear() == currentDate.getYear()) {
                        if(taskDate.getMonth() == currentDate.getMonth()) {
                            if(taskDate.getDay() == currentDate.getDay()) {
                                if(hour == currentDate.getHours()) {
                                    if(minute - currentDate.getMinutes() <= 15) {
                                        if(t.isReminder()) {
                                            remindOfTheseTasks.add(t);
                                            t.setReminder(false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //Create a notification
                Log.d(TAG, "run: Creating notifications");
                for(Task t : remindOfTheseTasks) {
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(ReminderService.this)
                                    .setContentTitle("TaskManager")
                                    .setContentText(t.getName())
                                    .setSmallIcon(R.drawable.notification);


                    Intent resultIntent = new Intent(ReminderService.this, MainActivity.class);
                    PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(
                                    ReminderService.this,
                                    0,
                                    resultIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );

                    mBuilder.setContentIntent(resultPendingIntent);

                    int mNotificationId = 001;
                    NotificationManager mNotifyMgr =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());

                    remindOfTheseTasks.remove(t);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
