package com.activeandroid;

import java.util.Collection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.LruCache;

import com.activeandroid.serializer.TypeSerializer;
import com.activeandroid.util.Log;

public final class Cache
{
	// ////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC CONSTANTS
	// ////////////////////////////////////////////////////////////////////////////////////

	public static final int DEFAULT_CACHE_SIZE = 1024;

	private static final String TAG = "Cache";

	// ////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE MEMBERS
	// ////////////////////////////////////////////////////////////////////////////////////

	private static Context sContext;

	private static ModelInfo sModelInfo;
	private static DatabaseHelper sDatabaseHelper;

	private static LruCache<String, Model> sEntities;

	private static boolean sIsInitialized = false;

	// ////////////////////////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ////////////////////////////////////////////////////////////////////////////////////

	private Cache()
	{
	}

	// ////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ////////////////////////////////////////////////////////////////////////////////////

	public static synchronized void initialize(Configuration configuration)
	{
		if (sIsInitialized)
		{
			Log.v("ActiveAndroid already initialized.");
			return;
		}

		sContext = configuration.getContext();
		sModelInfo = new ModelInfo(configuration);
		sDatabaseHelper = new DatabaseHelper(configuration);

		sEntities = new LruCache<String, Model>(configuration.getCacheSize());

		openDatabase();

		sIsInitialized = true;

		Log.v("ActiveAndroid initialized successfully.");
	}

	public static synchronized void clear()
	{
		sEntities.evictAll();
		Log.v("Cache cleared.");
	}

	public static synchronized void dispose()
	{
		closeDatabase();

		sEntities = null;
		sModelInfo = null;
		sDatabaseHelper = null;

		sIsInitialized = false;

		Log.v("ActiveAndroid disposed. Call initialize to use library.");
	}

	// Database access

	public static boolean isInitialized()
	{
		return sIsInitialized;
	}

	public static synchronized SQLiteDatabase openDatabase()
	{
		return sDatabaseHelper.getWritableDatabase();
	}

	public static synchronized void closeDatabase()
	{
		sDatabaseHelper.close();
	}

	public static boolean isSetup()
	{
		if (sDatabaseHelper != null)
		{
			return true;
		}

		return false;
	}

	// Context access

	public static Context getContext()
	{
		return sContext;
	}

	// Entity cache

	public static String getIdentifier(Class<? extends Model> type, Long id)
	{
		return getTableName(type) + "@" + id;
	}

	public static String getIdentifier(Model entity)
	{
		return getIdentifier(entity.getClass(), entity.getId());
	}

	public static synchronized void addEntity(Model entity)
	{
		sEntities.put(getIdentifier(entity), entity);
	}

	public static synchronized Model getEntity(Class<? extends Model> type, long id)
	{
		return sEntities.get(getIdentifier(type, id));
	}

	public static synchronized void removeEntity(Model entity)
	{
		sEntities.remove(getIdentifier(entity));
	}

	// Model cache

	public static synchronized Collection<TableInfo> getTableInfos()
	{
		return sModelInfo.getTableInfos();
	}

	public static synchronized TableInfo getTableInfo(Class<? extends Model> type)
	{
		return sModelInfo.getTableInfo(type);
	}

	public static synchronized TypeSerializer getParserForType(Class<?> type)
	{
		return sModelInfo.getTypeSerializer(type);
	}

	public static synchronized String getTableName(Class<? extends Model> type)
	{
		return sModelInfo.getTableInfo(type).getTableName();
	}
}
