package android.support.v7.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by DragonLotus on 8/26/2016.
 */

public class GridLayoutManagerMy extends GridLayoutManager {

    public static final String TAG = GridLayoutManagerMy.class.getSimpleName();

    public GridLayoutManagerMy(Context context, int spanCount) {
        super(context, spanCount);
    }

    public GridLayoutManagerMy(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    private static Method updateMeasurements;

    @Override
    public void setSpanCount(int spanCount) {
        super.setSpanCount(spanCount);

        if (updateMeasurements == null) {
            try {
                updateMeasurements = GridLayoutManager.class.getDeclaredMethod("updateMeasurements");
                updateMeasurements.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "Workaround broken. Can't find method. Fixme.", e);
            }
        }

        if (updateMeasurements != null) {
            try {
                updateMeasurements.invoke(this, (Object[]) null);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Something wrong with workaround", e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Something wrong with workaround", e);
            }
        }

        if (mSet != null && mSet.length != spanCount) {
            View[] newSet = new View[spanCount];
            System.arraycopy(mSet, 0, newSet, 0, Math.min(mSet.length, spanCount));
            mSet = newSet;
        }
    }
}
