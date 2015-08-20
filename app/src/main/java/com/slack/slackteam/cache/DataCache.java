package com.slack.slackteam.cache;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class used for caching data values. The member details fetched are cached immediately.
 *
 * The values populaed are taken from cache file.
 */
public class DataCache {

    private DataCache() {
    }


    /**
     * Method for cacheing data values.
     * @param context : context of activity.
     * @param key : Name of file.
     * @param object : data to be cached.
     * @throws IOException
     */
    public static void writeObject(Context context, String key, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    /**
     * Method for retrieving cached data values.
     * @param context : context of activity.
     * @param key : Name of file.
     * @throws IOException
     */
    public static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }
}
