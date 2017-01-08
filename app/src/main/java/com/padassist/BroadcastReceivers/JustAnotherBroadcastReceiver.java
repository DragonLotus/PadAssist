package com.padassist.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by DragonLotus on 1/2/2017.
 */

public class JustAnotherBroadcastReceiver extends BroadcastReceiver {

    private JustAnotherBroadcastReceiver.receiverMethods receiverMethods;

    public interface receiverMethods{
        void onReceiveMethod(Intent intent);
    }

    public JustAnotherBroadcastReceiver(JustAnotherBroadcastReceiver.receiverMethods receiverMethods) {
        this.receiverMethods = receiverMethods;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        receiverMethods.onReceiveMethod(intent);
    }
}
