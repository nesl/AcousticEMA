package ucla.nesl.audiorecording.audiorecording;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PeriodicalSoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodical_sound);

        startService(new Intent(this, PeriodicalSoundService.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopService(new Intent(this, PeriodicalSoundService.class));
    }
}
