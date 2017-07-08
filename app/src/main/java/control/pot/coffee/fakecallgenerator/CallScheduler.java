package control.pot.coffee.fakecallgenerator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by loker on 7/8/2017.
 */

public class CallScheduler {
    private static final String TAG = "CallScheduler";

    private int wait;
    private int repeats;
    private int interval;

    private String name;
    private String number;
    private String photoUri;

    Context context;
    AlarmManager am;
    Bundle b;

    public CallScheduler() {
    }

    CallScheduler(Context context, int wait, Bundle b)    {
        this.b = b;
    }

    CallScheduler(Context context, int wait, int repeats, int interval,
                  String name, String number, String photoUri)  {
        this.context = context;
        this.wait = wait;
        this.repeats = repeats;
        this.interval = interval;
        this.name = name;
        this.number = number;
        this.photoUri = photoUri;
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Log.v(TAG,wait + " | " + repeats + " | " + interval + " | " + name + " | " + number + " | " + photoUri);
    }

    //This func should use array instead
    public void schedule()  {
        Intent firstIntent = new Intent(context, CallingActivity.class);
        firstIntent.setAction(Constants.ACTION_CALL);

        firstIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        if (b == null) {
            b = new Bundle();
            b.putString(Constants.EXTRA_KEY_NAME, name);
            b.putString(Constants.EXTRA_KEY_NUMBER, number);
            b.putString(Constants.EXTRA_KEY_PHOTO, photoUri);
            b.putInt(Constants.EXTRA_KEY_INTERVAL, interval);
            b.putInt(Constants.EXTRA_KEY_REPEATS, repeats - 1);
        } else {
            b.putInt(Constants.EXTRA_KEY_REPEATS, b.getInt(Constants.EXTRA_KEY_REPEATS, 0) - 1);
        }

        firstIntent.putExtras(new Bundle(b));

        PendingIntent firstPendingIntent = PendingIntent.getActivity(context, 0, firstIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long currentTime = SystemClock.elapsedRealtime();
                am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, currentTime + wait * 1000, firstPendingIntent);
    }
}
