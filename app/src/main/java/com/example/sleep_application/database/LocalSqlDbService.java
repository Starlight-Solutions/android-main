package com.example.sleep_application.database;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LocalSqlDbService extends Service {
    public LocalSqlDbService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}