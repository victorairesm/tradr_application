package br.com.victor.tradr;

import android.app.Application;
import android.util.Log;

import com.squareup.otto.Bus;

public class TradRApplication extends Application {
    private static final String TAG = "TradRApplication";
    private static TradRApplication instance = null;
    private Bus bus = new Bus();

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

    public Bus getBus() {
        return bus;
    }
}
