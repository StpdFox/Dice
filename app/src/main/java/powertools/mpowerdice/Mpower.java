package powertools.mpowerdice;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Matthijs Vos on 31-10-2016.
 */
public class Mpower extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
