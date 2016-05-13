package br.com.victor.tradr;

import android.app.Application;
import android.util.Log;

public class TradRApplication extends Application {
    private static final String TAG = "TradRApplication";
    private static TradRApplication instance = null;

    public static TradRApplication getInstance() {
        return instance; // Singleton
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "TradRApplication.onCreate()");
        // Salva a instância para termos acesso como Singleton
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "TradRApplication.onTerminate()");
    }
}
