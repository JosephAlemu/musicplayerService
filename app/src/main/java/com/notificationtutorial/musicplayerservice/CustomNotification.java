//package com.notificationtutorial.musicplayerservice;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.NotificationCompat;
//import android.widget.RemoteViews;
//
///**
// * Created by Admin on 1/31/2018.
// */
//
//public class CustomNotification {
//
//    public static final String NOTIFY_PREVIOUS = "previous";
//    public static final String NOTIFY_DELETE = "delete";
//    public static final String NOTIFY_PAUSE = "pause";
//    public static final String NOTIFY_PLAY = "play";
//    public static final String NOTIFY_NEXT = "next";
//
//    private static final int NOTIFICATION_ID_OPEN_ACTIVITY = 9;
//    private static final int NOTIFICATION_ID_CUSTOM_BIG = 9;
//
//
//    public static void customBigNotification(Context context) {
//
//
//        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
//
//        Notification.Builder nc = new Notification.Builder(context);
//        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent notifyIntent = new Intent(context, MainActivity.class);
//
//        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        nc.setContentIntent(pendingIntent);
//        nc.setSmallIcon(R.drawable.play);
//        nc.setAutoCancel(true);
//        nc.setCustomBigContentView(expandedView);
//        nc.setContentTitle("Music Player");
//        nc.setContentText("Control Audio");
//        nc.getBigContentView().setTextViewText(R.id.textSongName, "Adele");
//
//        setListeners(expandedView, context);
//
//        nm.notify(NOTIFICATION_ID_CUSTOM_BIG, nc.build());
//
//
//
//
//
//
//        //Create Notification using NotificationCompat.Builder
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                // Set Icon
//                .setSmallIcon(R.mipmap.ic_launcher)
//                // Set Ticker Message
//                .setTicker(getString(R.string.notification_ticker))
//                // Set Title
//                .setContentTitle(getString(R.string.notification_title))
//                // Set Text
//                .setContentText(getString(R.string.notification_customized_text))
//                // Set Big Text
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.notification_customized_big_text)))
//                // Add an Action Button below Notification
//                .addAction(R.drawable.ic_android, getString(R.string.notification_action_button), pIntent)
//                // Set PendingIntent into Notification
//                .setContentIntent(pIntent)
//                // Dismiss Notification
//                .setAutoCancel(true);
//
//        // Build Notification with Notification Manager
//        mNotificationManager.notify(0, builder.build());
//
//
//
//    }
//
//    private static void setListeners(RemoteViews view, Context context) {
//        Intent previous = new Intent(NOTIFY_PREVIOUS);
//        Intent delete = new Intent(NOTIFY_DELETE);
//        Intent pause = new Intent(NOTIFY_PAUSE);
//        Intent next = new Intent(NOTIFY_NEXT);
//        Intent play = new Intent(NOTIFY_PLAY);
//
//        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
//        view.setOnClickPendingIntent(R.id.btnPrevious, pPrevious);
//
//
//        PendingIntent pDelete = PendingIntent.getBroadcast(context, 0, delete, PendingIntent.FLAG_UPDATE_CURRENT);
//        view.setOnClickPendingIntent(R.id.btnDelete, pDelete);
//
//
//        PendingIntent pPause = PendingIntent.getBroadcast(context, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
//        view.setOnClickPendingIntent(R.id.btnPause, pPause);
//
//
//        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
//        view.setOnClickPendingIntent(R.id.btnNext, pNext);
//
//
//        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
//        view.setOnClickPendingIntent(R.id.btnPlay, pPlay);
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
