package com.game.myapplication01;

import static java.lang.Math.log;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class HomeActivity extends AppCompatActivity {

   private  static final int MY_REQ_CODE =100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);  // Ensure this is activity_home.xml

        checkForUpdates();
        Button goInsideButton = findViewById(R.id.goInsideButton);
        goInsideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AZListingActivity.class);
                startActivity(intent);
            }
        });

    }

    private void checkForUpdates() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Log.i("UpdateCheck", "Checking for updates...");

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            // Check if an update is available and if immediate updates are allowed
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            MY_REQ_CODE
                    );
                } catch (IntentSender.SendIntentException e) {
                    Log.e("UpdateCheck", "Error starting update flow", e);
                }
            } else {
                Log.i("UpdateCheck", "No updates available or immediate updates not allowed.");
            }
        }).addOnFailureListener(e -> {
            Log.e("UpdateCheck", "Failed to check for updates", e);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQ_CODE) {
            if (resultCode != RESULT_OK) {
                Log.w("HomeActivity", "Update flow failed! Result Code: " + resultCode);
            } else {
                Log.i("HomeActivity", "Update flow succeeded!");
            }
        }
    }


}
