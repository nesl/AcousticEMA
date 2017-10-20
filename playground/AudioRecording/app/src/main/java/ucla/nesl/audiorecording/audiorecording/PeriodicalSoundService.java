package ucla.nesl.audiorecording.audiorecording;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class PeriodicalSoundService extends Service {

    private final IBinder mBinder = new MyBinder();

    private final int INTERVAL_MINUTES = 1;

    private final String voiceFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "ios_sms.mp3";

    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;

    private ScheduleSoundHandler soundHandler;


    @Override
    public void onCreate() {
        super.onCreate();

        soundHandler = new ScheduleSoundHandler();
        soundHandler.scheduleNextEvent();

        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
        wakeLock.acquire();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        soundHandler.terminate();
        wakeLock.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        PeriodicalSoundService getService() {
            return PeriodicalSoundService.this;
        }
    }

    // ---- audio related -----------------------------------------------------------------------
    private class ScheduleSoundHandler extends Handler {

        private final int MSG_WHAT = 0;

        @Override
        public void handleMessage(Message msg) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(voiceFilePath);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();

            scheduleNextEvent();
        }

        private void scheduleNextEvent() {
            Calendar currentTime = Calendar.getInstance();
            int minute = currentTime.get(Calendar.MINUTE);
            int second = currentTime.get(Calendar.SECOND);

            int divisor = INTERVAL_MINUTES * 60;
            int reminder = (minute * 60 + second) % divisor;
            Log.i("Debug", "Play in " + (divisor - reminder) + " seconds");

            sendEmptyMessageDelayed(MSG_WHAT, (divisor - reminder) * 1000L);
        }

        private void terminate() {
            this.removeMessages(MSG_WHAT);
        }

    }

}
