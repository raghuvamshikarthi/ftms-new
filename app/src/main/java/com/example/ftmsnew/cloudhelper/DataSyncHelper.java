package com.example.ftmsnew.cloudhelper;

import android.content.Context;

import com.example.ftmsnew.database.DataAccessHandler;
import com.example.ftmsnew.database.Queries;
import com.example.ftmsnew.utils.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;



public class DataSyncHelper {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String LOG_TAG = DataSyncHelper.class.getName();
    public static int countCheck, transactionsCheck = 0;
    public static List<String> refreshtableNamesList = new ArrayList<>();
    public static LinkedHashMap<String, List> refreshtransactionsDataMap = new LinkedHashMap<>();



    public static synchronized void performMasterSync(final Context context, final boolean firstTimeInsertFinished, final ApplicationThread.OnComplete onComplete) {

        LinkedHashMap<String, String> syncDataMap = new LinkedHashMap<>();
        syncDataMap.put("UserId", "0");
        countCheck = 0;

        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        ProgressBar.Companion.showProgressBar(context, "Making data ready for you...");
        CloudDataHandler.getMasterData(Config.live_url + Config.masterSyncUrl, syncDataMap, new ApplicationThread.OnComplete<HashMap<String, List>>() {
            @Override
            public void execute(boolean success, final HashMap<String, List> masterData, String msg) {
                if (success) {
                    if (masterData != null && masterData.size() > 0) {
                        Log.INSTANCE.v(LOG_TAG, "@@@ Master sync is success and data size is " + masterData.size());
                        final Set<String> tableNames = masterData.keySet();
                        for (final String tableName : tableNames) {
                            Log.INSTANCE.v(LOG_TAG, "@@@ Delete Query " + String.format(Queries.Companion.getInstance().deleteTableData(), tableName));
                            ApplicationThread.dbPost("Master Data Sync..", "master data", new Runnable() {
                                @Override
                                public void run() {
                                    countCheck++;
                                    if (!firstTimeInsertFinished) {
                                        dataAccessHandler.deleteRow(tableName, null, null, false, new ApplicationThread.OnComplete<String>() {
                                            @Override
                                            public void execute(boolean success, String result, String msg) {
                                                if (success) {
                                                    dataAccessHandler.insertData(true, tableName, masterData.get(tableName), new ApplicationThread.OnComplete<String>() {
                                                        @Override
                                                        public void execute(boolean success, String result, String msg) {
                                                            if (success) {
                                                                Log.INSTANCE.v(LOG_TAG, "@@@ sync success for " + tableName);
                                                            } else {
                                                                Log.INSTANCE.v(LOG_TAG, "@@@ check 1 " + masterData.size() + "...pos " + countCheck);
                                                                Log.INSTANCE.v(LOG_TAG, "@@@ sync failed for " + tableName + " message " + msg);
                                                            }
                                                            if (countCheck == masterData.size()) {
                                                                Log.INSTANCE.v(LOG_TAG, "@@@ Done with master sync " + countCheck);


                                                                onComplete.execute(true, null, "Sync is success");

                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Log.INSTANCE.v(LOG_TAG, "@@@ Master table deletion failed for " + tableName);
                                                }
                                            }
                                        });
                                    } else {
                                        dataAccessHandler.insertData(tableName, masterData.get(tableName), new ApplicationThread.OnComplete<String>() {
                                            @Override
                                            public void execute(boolean success, String result, String msg) {
                                                if (success) {
                                                    Log.INSTANCE.v(LOG_TAG, "@@@ sync success for " + tableName);
                                                } else {
                                                    Log.INSTANCE.v(LOG_TAG, "@@@ check 2 " + masterData.size() + "...pos " + countCheck);
                                                    Log.INSTANCE.v(LOG_TAG, "@@@ sync failed for " + tableName + " message " + msg);
                                                }
                                                if (countCheck == masterData.size()) {
                                                    Log.INSTANCE.v(LOG_TAG, "@@@ Done with master sync " + countCheck);
                                                    ProgressBar.Companion.hideProgressBar();
                                                    onComplete.execute(true, null, "Sync is success");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    } else {
                        ProgressBar.Companion.hideProgressBar();
                        Log.INSTANCE.v(LOG_TAG, "@@@ Sync is up-to-date");
                        onComplete.execute(true, null, "Sync is up-to-date");
                    }
                } else {
                    ProgressBar.Companion.hideProgressBar();
                    onComplete.execute(false, null, "Master sync failed. Please try again");
                }
            }
        });
    }


    /*
    public static synchronized void  getUsersData(final Context context, final ApplicationThread.OnComplete onComplete){

        final Map<String, Object> syncDataMap = new LinkedHashMap<>();
        syncDataMap.put("UserId", "0");

        final int range= 25000;
        final int[] start = {1};
        final int[] end = {range};

        final Boolean[] state = {true};
        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);

        while (state[0]){

            HttpClient.post(Config.live_url + Config.userDetail + start[0] + end[0],syncDataMap, new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {
                    if (success) {
                        try {
                            if (!result.isEmpty()) {
                                final List masterDataMap1 = new ArrayList();
                                masterDataMap1.addAll(CommonUtils.toList(new JSONArray(result)));
                                if (masterDataMap1.isEmpty()){
                                    state[0] = false;
                                    ProgressBar.Companion.hideProgressBar();
                                    Log.INSTANCE.v(LOG_TAG, "@@@ sync falied for -"+start[0] + "Village");
                                    onComplete.execute(true, "Data inserted failed" + msg, "");
                                }else {
                                    dataAccessHandler.insertData(true, "Village", masterDataMap1, new ApplicationThread.OnComplete<String>() {
                                        @Override
                                        public void execute(boolean success, String result, String msg) {
                                            if (success) {
                                                Log.INSTANCE.v(LOG_TAG, "@@@ sync success for -"+start[0] + "Village");
                                                start[0] = end[0] + 1;
                                                end[0] = end[0] + range;

                                            } else {
                                                onComplete.execute(false, "Data is not inserted in DB" + msg, "");
                                                Log.INSTANCE.v(LOG_TAG, "@@@ check 1 " + masterDataMap1.size() + "...pos " + countCheck);
                                                Log.INSTANCE.v(LOG_TAG, "@@@ sync failed for Village message " + msg);
                                            }
                                        }
                                    });
                                }
                            }else {
                                state[0] = false;
                                Log.INSTANCE.v(LOG_TAG, "@@@ sync falied for -"+start[0] + "Village "+ msg+" -- "+result);
                                ProgressBar.Companion.hideProgressBar();
                                onComplete.execute(true, "Data inserted " + msg, "Sync is up-to-date");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        state[0] = false;
                        Log.INSTANCE.v(LOG_TAG, "@@@ sync falied for -"+start[0] + "Village "+ msg+" -- "+result);
                        onComplete.execute(false, "Data syn" + msg, "");
                    }

                }
            });

        }
        ProgressBar.Companion.hideProgressBar();
        onComplete.execute(true, "Data inserted sucess and loop exit", "");

    }


     */



}