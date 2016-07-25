package com.padassist.Util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


import com.padassist.Constants;

public class SharedPreferencesUtil
{
	private static final String TAG = SharedPreferencesUtil.class.getSimpleName();
	private static final int MILLI_PER_HOUR = 3600000;

	public static void savePreferences(String key, Boolean value)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void savePreferences(String key, Float value)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		Editor editor = sharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static void savePreferences(String key, Integer value)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void savePreferences(String key, Long value)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static void savePreferences(String key, String value)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static Boolean loadPreferenceBoolean(String key)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		return sharedPreferences.getBoolean(key, false);
	}

	public static Float loadPreferenceFloat(String key)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		return sharedPreferences.getFloat(key, 0.0f);
	}

	public static Integer loadPreferenceInt(String key)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		return sharedPreferences.getInt(key, 0);
	}

	public static Long loadPreferenceLong(String key)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		return sharedPreferences.getLong(key, 0);
	}

	public static String loadPreferenceString(String key)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		return sharedPreferences.getString(key, "");
	}

	public static Boolean containsKey(String key)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		return sharedPreferences.contains(key);
	}

	public static void removePreference(String key)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		Editor editor = sharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}

	public static void initializePreferences()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
		Boolean initialized = sharedPreferences.getBoolean(Constants.KEY_INITIALIZED, false);
		if (initialized == false)
		{
			Editor editor = sharedPreferences.edit();
			editor.commit();
		}
	}




}
