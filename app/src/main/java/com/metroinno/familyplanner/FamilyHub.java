package com.metroinno.familyplanner;

import com.firebase.client.Firebase;

/**
 * Created by anuj on 4/20/16.
 */
public class FamilyHub extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
