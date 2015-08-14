package com.activeandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.Build;

import com.activeandroid.util.Log;

public final class ActiveAndroid
{
	public static final boolean LOGGING_ENABLED = false;

	public static void initialize(Context context)
	{
		initialize(new Configuration.Builder(context).create());
	}

	public static void initialize(Configuration configuration)
	{
		initialize(configuration, false);
	}

	public static boolean isSetup()
	{
		return Cache.isSetup() && Cache.isInitialized();
	}

	public static void initialize(Context context, boolean loggingEnabled)
	{
		initialize(new Configuration.Builder(context).create(),
			loggingEnabled);
	}

	public static void initialize(Configuration configuration,
		boolean loggingEnabled)
	{
		// Set logging enabled first
		setLoggingEnabled(LOGGING_ENABLED);
		Cache.initialize(configuration);

		if (Build.VERSION.SDK_INT > 10)
		{
			Cache.openDatabase().enableWriteAheadLogging();
		}
	}

	public static void clearCache()
	{
		Cache.clear();
	}

	public static void dispose()
	{
		Cache.dispose();
	}

	public static void setLoggingEnabled(boolean enabled)
	{
		Log.setEnabled(enabled);
	}

	public static SQLiteDatabase getDatabase()
	{
		return Cache.openDatabase();
	}

	@SuppressLint("NewApi")
	public static void beginTransaction()
	{
		if (Build.VERSION.SDK_INT > 10)
		{

			Cache.openDatabase().beginTransactionNonExclusive();
		} else
		{
			Cache.openDatabase().beginTransaction();

		}
	}
	
	
	
	public static void beginExclusiveTransaction()
	{

		Cache.openDatabase().beginTransaction();
	}

	public static void beginTransactionWithListener(
		SQLiteTransactionListener transactionListener)
	{
		if (Build.VERSION.SDK_INT > 10)
		{

			Cache.openDatabase().beginTransactionWithListenerNonExclusive(
				transactionListener);
		} else
		{
			Cache.openDatabase().beginTransactionWithListener(
				transactionListener);

		}
	}

	/*public void beginTransactionWithListener(SQLiteTransactionListener transactionListener) {
	        beginTransaction(transactionListener, true);
	    }*/

	public static void endTransaction()
	{
		Cache.openDatabase().endTransaction();
	}

	public static void setTransactionSuccessful()
	{
		Cache.openDatabase().setTransactionSuccessful();
	}

	public static boolean inTransaction()
	{
		return Cache.openDatabase().inTransaction();
	}

	public static void execSQL(String sql)
	{
		Cache.openDatabase().execSQL(sql);
	}

	public static void execSQL(String sql, Object[] bindArgs)
	{
		Cache.openDatabase().execSQL(sql, bindArgs);
	}

}
