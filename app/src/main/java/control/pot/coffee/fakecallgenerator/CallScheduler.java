package control.pot.coffee.fakecallgenerator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.util.Log;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by loker on 7/8/2017.
 */

public class CallScheduler {
    private static final String TAG = "CallScheduler";

    private int delay;
    private int repeats;
    private int interval;

    private String name;
    private String number;
    private String photoUri;

    Context context;
    AlarmManager am;

    public CallScheduler() {
    }

    CallScheduler(Context context, int delay, int repeats, int interval,
                  String name, String number, String photoUri)  {
        this.context = context;
        this.delay = delay;
        this.repeats = repeats;
        this.interval = interval;
        this.name = name;
        this.number = number;
        this.photoUri = photoUri;
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Log.v(TAG,delay + " | " + repeats + " | " + interval + " | " + name + " | " + number + " | " + photoUri);
    }

    //This func should use array instead
    public void schedule()  {
        Intent firstIntent = new Intent(context, CallingActivity.class);
        Intent secondIntent = new Intent(context, CallingActivity.class);
        Intent thirdIntent = new Intent(context, CallingActivity.class);

        firstIntent.setAction(Constants.ACTION_FIRST_CALL);
        secondIntent.setAction(Constants.ACTION_SECOND_CALL);
        thirdIntent.setAction(Constants.ACTION_THIRD_CALL);

        firstIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        secondIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        thirdIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Bundle b = new Bundle();
        b.putString(Constants.EXTRA_KEY_NAME, name);
        b.putString(Constants.EXTRA_KEY_NUMBER, number);
        b.putString(Constants.EXTRA_KEY_PHOTO, photoUri);
        b.putInt(Constants.EXTRA_KEY_TOTAL_REPEATS, repeats);

        firstIntent.putExtras(new Bundle(b));
        secondIntent.putExtras(new Bundle(b));
        thirdIntent.putExtras(b);

        PendingIntent firstPendingIntent = PendingIntent.getActivity(context, 0, firstIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent secondPendingIntent = PendingIntent.getActivity(context, 0, secondIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent thirdPendingIntent = PendingIntent.getActivity(context, 0, thirdIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        long currentTime = SystemClock.elapsedRealtime();

        //Set Alarms
        switch(repeats) {
            case 3:
                am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, currentTime + delay * 1000 + 2 * interval * 1000, thirdPendingIntent);
                Log.v(TAG, "Scheduled First");
            case 2:
                am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, currentTime + delay * 1000 + 1 * interval * 1000, secondPendingIntent);
                Log.v(TAG, "Scheduled Second");
            case 1:
                am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, currentTime + delay * 1000 + 0 * interval * 1000, firstPendingIntent);
                Log.v(TAG, "Scheduled Third");
            default:
        }
    }
}
