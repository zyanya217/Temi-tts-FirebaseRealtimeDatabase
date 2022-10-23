package tw.com.tts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        Robot.TtsListener {

    private Robot robot;
//    private static final String Speak = "123 哈囉 我是temi ";

    private static String Speak = null;

    protected void onStart() {
        super.onStart();
        Robot.getInstance().addTtsListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initViews();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        robot = Robot.getInstance(); // get an instance of the robot in order to begin using its features.

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("todolist");
//
//        myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
                Speak = value;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
//    public void initViews(){
//        etSpeak = findViewById(R.id.etSpeak);
//
//    }

      /*
        Have the robot speak while displaying what is being said.
     */

    public void speak(View view) {
        TtsRequest ttsRequest = TtsRequest.create(Speak,true);
        robot.speak(ttsRequest);
    }

    @Override
    public void onTtsStatusChanged(TtsRequest ttsRequest) {

        // Do whatever you like upon the status changing. after the robot finishes speaking
        // Toast.makeText(this, "speech: " + ttsRequest.getSpeech() + "\nstatus:" + ttsRequest.getStatus(), Toast.LENGTH_LONG).show();
    }



}
