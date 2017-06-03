package ra57_2014.pnrs1.rtrk.taskmanager.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import ra57_2014.pnrs1.rtrk.taskmanager.newtask.Task;


/**
 * Created by ironm on 6/3/2017.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "taks.db";
    public final static int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "task";
    public static final String COLUMN_TASK_NAME = "TaskName";
    public static final String COLUMN_TASK_TIME = "TaskTime";
    public static final String COLUMN_REMINDER = "Reminder";
    public static final String COLUMN_TASK_DESC = "TaskDesc";
    public static final String COLUMN_TASK_PRIORITY = "TaskPriority";

    private SQLiteDatabase mDatabase = null;

    private static final String TAG = "TaskDbHelper";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_TASK_NAME + " TEXT, " +
            COLUMN_TASK_DESC + " TEXT, " +
            COLUMN_TASK_TIME + " TEXT, " +
            COLUMN_REMINDER  + " INTEGER, " +
            COLUMN_TASK_PRIORITY + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Task t) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME , t.getName());
        values.put(COLUMN_TASK_DESC , t.getDescription());
        values.put(COLUMN_TASK_TIME , t.getTime());
        values.put(COLUMN_REMINDER , t.isReminder() == true);
        values.put(COLUMN_TASK_PRIORITY , t.getPriority());

        db.insert(TABLE_NAME , null , values);
        close();
    }

    public void initializeDatabase(ArrayList<Task> tasks) {
        for(Task t : tasks) {
            insert(t);
        }
    }

    public ArrayList<Task> readTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME , null , null , null , null , null , null);
        ArrayList<Task> tasks = new ArrayList<>();

        if(cursor.getCount() <= 0) {
            Log.d(TAG, "readTask: Database empty");
            return null;
        }

        //Task[] tasks = new Task[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            tasks.add(createTask(cursor));
        }

        Log.d(TAG, "readTasks: Reading tasks");
        for(Task t : tasks) {
            Log.d(TAG, "readTasks: " + t.toString());
        }

        close();
        return  tasks;
    }

    public Task readTask(String taskName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME ,  null ,COLUMN_TASK_NAME + "=?" , new String[] {taskName} , null , null , null);

        cursor.moveToFirst();
        Task task = createTask(cursor);

        close();
        return task;
    }

    private Task createTask(Cursor cursor) {
        String taskName = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
        String taskTime = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_TIME));
        String taskDesc = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESC));
        int taskPriority = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_PRIORITY));
        boolean reminder = (cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER)) == 0) ? false : true;

        return new Task(taskName , taskDesc , taskTime , reminder , taskPriority);
    }

    public void deleteTask(String taskName) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME , COLUMN_TASK_NAME + "=?" , new String[] {taskName});
        close();
    }

    public void clearDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME , null , null);
    }

}
