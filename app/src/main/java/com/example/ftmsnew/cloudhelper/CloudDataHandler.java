package com.example.ftmsnew.cloudhelper;

import android.util.Log;

import androidx.annotation.NonNull;


import com.example.ftmsnew.common.CommonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


public class CloudDataHandler {

    private static final String LOG_TAG = CloudDataHandler.class.getName();
    private static JSONObject parentMasterDataObject;
    private static JSONArray jsonElements;


    public static void getMasterData(final String url, final LinkedHashMap dataMap, @NonNull final ApplicationThread.OnComplete<HashMap<String, List>> onComplete) {
        ApplicationThread.bgndPost(CloudDataHandler.class.getName(), "getMasterData...", new Runnable() {
            @Override
            public void run() {
                HttpClient.post(url,dataMap, new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        if (success) {
                            try {
                                JSONObject parentMasterDataObject = new JSONObject(result);

                                Iterator keysToCopyIterator = parentMasterDataObject.keys();
                                List<String> keysList = new ArrayList<>();
                                while (keysToCopyIterator.hasNext()) {
                                    String key = (String) keysToCopyIterator.next();

                                    keysList.add(key);
                                }

                                Log.v(LOG_TAG, "@@@@ Tables Size " + keysList.size());
                                LinkedHashMap<String, List> masterDataMap = new LinkedHashMap<>();
                                for (String tableName : keysList) {
                                    try {
                                        masterDataMap.put(tableName, CommonUtils.toList(parentMasterDataObject.getJSONArray(tableName)));
                                    }
                                    catch (Exception ex)
                                    {

                                    }
                                }

                                Log.v(LOG_TAG, "@@@@ Tables Data " + masterDataMap.size());

                                onComplete.execute(success, masterDataMap, msg);

                            } catch (Exception e) {
                                e.printStackTrace();
                                onComplete.execute(success, null, msg);
                            }
                        } else
                            onComplete.execute(success, null, msg);
                    }
                });
            }
        });
    }




}
