package com.example.ftmsnew.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.ftmsnew.R;
import com.example.ftmsnew.cloudhelper.ApplicationThread;
import com.example.ftmsnew.cloudhelper.DataSyncHelper;
import com.example.ftmsnew.common.CommonConstants;
import com.example.ftmsnew.common.CommonUtils;
import com.example.ftmsnew.database.DataAccessHandler;
import com.example.ftmsnew.database.FTMSDatabase;
import com.example.ftmsnew.database.Queries;
import com.example.ftmsnew.utils.PrefUtil;
import com.example.ftmsnew.utils.UiUtils;

import io.fabric.sdk.android.Fabric;


public class SplashScreen extends AppCompatActivity {
    public static final String LOG_TAG = SplashScreen.class.getName();

    private FTMSDatabase ftmsDatabase;
    private String[] PERMISSIONS_REQUIRED = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
    };
    private SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
      //  Fabric.with(this,new Crashlytics());

        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences("appprefs", MODE_PRIVATE);
        if (!CommonUtils.isNetworkAvailable(this)) {
            Toast.makeText(this,"Please check your network connection",Toast.LENGTH_LONG).show();
           // UiUtils.showCustomToastMessage("Please check your network connection", this, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !CommonUtils.areAllPermissionsAllowed(this, PERMISSIONS_REQUIRED)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, CommonUtils.PERMISSION_CODE);
        } else {
            try {
                ftmsDatabase = FTMSDatabase.getPFtmseDatabase(this);
                ftmsDatabase.createDataBase();
                dbUpgradeCall();
            } catch (Exception e) {
                e.getMessage();
            }
            startMasterSync();
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CommonUtils.PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(LOG_TAG, "permission granted");
                    try {
                        ftmsDatabase = FTMSDatabase.getPFtmseDatabase(this);
                        ftmsDatabase.createDataBase();
                        dbUpgradeCall();
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "@@@ Error while getting master data "+e.getMessage());
                    }
                    startMasterSync();
                }
                break;
        }
    }

    public void startMasterSync() {
        if (CommonUtils.isNetworkAvailable(this) && !sharedPreferences.getBoolean(CommonConstants.INSTANCE.getIS_MASTER_SYNC_SUCCESS(),false)) {
            DataSyncHelper.performMasterSync(this, PrefUtil.INSTANCE.getBool(this, CommonConstants.INSTANCE.getIS_MASTER_SYNC_SUCCESS()), new ApplicationThread.OnComplete() {
                @Override
                public void execute(boolean success, Object result, String msg) {
                    // ProgressBar.Companion.hideProgressBar();
                    if (success) {
                        //Toast.makeText(SplashScreen.this, "Data synced successfully", Toast.LENGTH_SHORT).show();

                        sharedPreferences.edit().putBoolean(CommonConstants.INSTANCE.getIS_MASTER_SYNC_SUCCESS(), true).apply();
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                        finish();
                    } else {
                        Log.v(LOG_TAG, "@@@ Master sync failed " + msg);
                        ApplicationThread.uiPost(LOG_TAG, "master sync message", new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SplashScreen.this,"Please check your network connection",Toast.LENGTH_LONG).show();
                               // UiUtils.INSTANCE.showCustomToastMessage("Data syncing failed", SplashScreen.this, 1);
                                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                                finish();
                            }
                        });
                    }
                }
            });
        } else {
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            finish();
        }
    }

    public void dbUpgradeCall() {
        DataAccessHandler dataAccessHandler = new DataAccessHandler(SplashScreen.this, false);
        String count = dataAccessHandler.getCountValue(Queries.Companion.getInstance().UpgradeCount());
        if (TextUtils.isEmpty(count) || Integer.parseInt(count) == 0) {
            SharedPreferences sharedPreferences = getSharedPreferences("appprefs", MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(CommonConstants.IS_FRESH_INSTALL, true).apply();
        }
    }

}
