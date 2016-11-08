package com.ionicsit.akfwalkhouston;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.internal.utils.L;

import java.util.List;
import java.util.UUID;

public class MyApplication extends Application {

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                Log.v("Beacon", region.getMajor().toString() + ":" + region.getMinor().toString());
                Log.v("HELLO", region.toString());
                if (region.getIdentifier() == "Entrance") {
                    showNotification(
                            "WALK ENTRANCE.",
                            "Welcome to the AKF WALK 2016");
                } else if (region.getIdentifier() == "Village") {
                    showNotification(
                            "VILLAGE IN ACTION",
                            "SEE WHAT IT TAKES");
                } else if (region.getIdentifier() == "Stage") {
                    showNotification(
                            "MAIN STAGE",
                            "It's about Entertainment");
                } else if (region.getIdentifier() == "Auction") {
                    showNotification(
                            "SILENT AUCTION",
                            "Did I hear Superbowl Tickets");
                } else if (region.getIdentifier() == "Start") {
                    showNotification(
                            "START LINE",
                            "WE ARE STONGER TOGETHER");
                } else if (region.getIdentifier() == "Half") {
                    showNotification(
                            "KEEP GOING",
                            "This is how far people have to walk to get clean drinking water");
                }
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "Entrance",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        5487, 46794));
                beaconManager.startMonitoring(new Region(
                        "Village",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        55711, 54610));
                beaconManager.startMonitoring(new Region(
                        "Stage",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        34518, 49191));
                beaconManager.startMonitoring(new Region(
                        "Auction",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        46518, 3735));
                beaconManager.startMonitoring(new Region(
                        "Start",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        23454, 15486));
                beaconManager.startMonitoring(new Region(
                        "Half",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        26187, 52412));
            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}