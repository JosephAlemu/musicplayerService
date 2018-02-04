package com.notificationtutorial.musicplayerservice;

/**
 * Created by Admin on 2/3/2018.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "com.marothiatechs.customnotification.action.main";
        public static String INIT_ACTION = "com.marothiatechs.customnotification.action.init";
        public static String PREV_ACTION = "previous";
        public static String PLAY_ACTION = "play";
        public static String NEXT_ACTION = "next";
        public static String STARTFOREGROUND_ACTION = "startforeground";
        public static String STOPFOREGROUND_ACTION = "stopforeground";

    }



    public static final String NOTIFY_PREVIOUS = "previous";
    public static final String NOTIFY_DELETE = "delete";
    public static final String NOTIFY_MAIN = "mainactivity";

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

//    public static Bitmap getDefaultAlbumArt(Context context) {
//        Bitmap bm = null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        try {
//            bm = BitmapFactory.decodeResource(context.getResources(),
//                    R.drawable.default_album_art, options);
//        } catch (Error ee) {
//        } catch (Exception e) {
//        }
//        return bm;
//    }

}