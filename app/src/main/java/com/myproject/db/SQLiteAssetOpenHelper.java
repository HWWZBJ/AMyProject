package com.myproject.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteAssetOpenHelper extends SQLiteOpenHelper {

	private final Context mContext;
	private final String mName;
	private final CursorFactory mCursorFactory;
	private final int mVersion;
	private static final String ASSET_DB_PATH = "databases";// 自带数据库存放目录
	private String mArchivePath;// 压缩文件路径
	private String databasePath;// 数据库地址
	private static final String DATABASE_NAME = "dictionary";// 数据库名称
	private static final int DATABASE_VERSION = 5;// 数据库版本
	private static final String TAG = SQLiteAssetOpenHelper.class
			.getSimpleName();
	private SQLiteDatabase mDatabase = null;
	private boolean mIsInitializing = false;// 是否初始化

	/**
	 * @param context
	 * @param storageDirectory
	 *            数据库文件路径
	 * @param name
	 *            数据库名称
	 * @param factory
	 * @param version
	 */
	public SQLiteAssetOpenHelper(Context context, String storageDirectory,
			String name, CursorFactory factory, int version) {
		super(context, name, factory, version);

		if (name == null)
			throw new IllegalStateException("数据库名称错误" + name);
		if (version == 0)
			throw new IllegalStateException("错误的数据库版本" + version);
		mContext = context.getApplicationContext();
		mName = name;
		mCursorFactory = factory;
		mVersion = version;
		mArchivePath = ASSET_DB_PATH + "/" + name + ".zip";// 自带数据库存放目录

		// 数据库地址
		if (databasePath != null) {
			databasePath = storageDirectory;
		} else {
			databasePath = mContext.getApplicationInfo().dataDir + "/databases";
		}
	}

	public SQLiteAssetOpenHelper(Context context, CursorFactory factory) {
		this(context, DATABASE_NAME, null, factory, DATABASE_VERSION);
	}

	public SQLiteAssetOpenHelper(Context context, String name,
			CursorFactory factory) {
		this(context, name, null, factory, DATABASE_VERSION);
	}

	/**
	 * 打开数据库
	 */
	private SQLiteDatabase createOrOpenDatabase(boolean isUpdate)
			throws SQLiteException {
		SQLiteDatabase db = returnDataBase();
		if (db == null) {// 拷贝数据库
			copyDatabaseFromAssets();
			db = returnDataBase();
			if (db != null) {
				db.setVersion(mVersion);
			}
		} else {
			// 数据库存在强制更新
			if (isUpdate) {
				if (db != null) {
					db.close();
				}
				Log.w(TAG, "强制更新数据库");
				copyDatabaseFromAssets();
				db = returnDataBase();
				if (db != null) {
					db.setVersion(mVersion);
				}
			}
		}
		return db;
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		if (mDatabase != null && mDatabase.isOpen()) {
			return mDatabase;
		}
		if (mIsInitializing)
			throw new IllegalStateException("数据库还未准备");

		try {
			return getWritableDatabase();// 进行数据库更新
		} catch (SQLiteException e) {
			e.printStackTrace();
		}

		SQLiteDatabase db = null;

		try {
			mIsInitializing = true;// 正在初始化
			String path = mContext.getDatabasePath(mName).getPath();// 获得数据库地址
			db = SQLiteDatabase.openDatabase(path, mCursorFactory,
					SQLiteDatabase.OPEN_READONLY);
			if (db.getVersion() != mVersion) {// 版本错误
				throw new SQLiteException(
						"Can't upgrade read-only database from version "
								+ db.getVersion() + " to " + mVersion + ": "
								+ path);
			}
			onOpen(db);
			Log.w(TAG, "Opened " + mName + " in read-only mode");
			mDatabase = db;
			return mDatabase;
		} finally {
			mIsInitializing = false;
			if (db != null && db != mDatabase)
				db.close();
		}
	}

	/**
	 * 获得一个可以写入的数据库对象
	 */
	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		// 数据库已经打开与数据库只能是读取形式
		if (mDatabase != null && mDatabase.isOpen() && !mDatabase.isReadOnly()) {
			return mDatabase;
		}

		if (mIsInitializing)
			throw new IllegalStateException(
					"getWritableDatabase called recursively");

		// 成功失败标记
		boolean success = false;
		SQLiteDatabase db = null;
		try {
			mIsInitializing = true;// 正在初始化标记

			db = createOrOpenDatabase(false);

			int version = db.getVersion();// 获取数据库版本

			// 如果当前的数据库版本不等于之前的则进行升级
			if (version != mVersion) {
				if (db != null)
					db.close();
				db = createOrOpenDatabase(true);
				db.setVersion(mVersion);
				version = db.getVersion();
			}

			// 不等于当前的版本需要进行数据
			if (version != mVersion) {
				db.beginTransaction();
				try {
					if (version == 0) {// 版本为0时候进行处理
						onCreate(db);
					} else {
						if (version > mVersion)
							;// 历史版本大于当前的版本
					}
					db.setVersion(mVersion);
					db.setTransactionSuccessful();
				} finally {
					db.endTransaction();
				}
			}

			onOpen(db);
			success = true;
			return db;
		} finally {
			mIsInitializing = false;
			if (success) {// 成功
				if (mDatabase != null) {
					try {
						mDatabase.close();
					} catch (Exception e) {
					}
				}
				mDatabase = db;
			} else {// 失败
				if (db != null)
					db.close();
			}
		}
	}

	@Override
	public synchronized void close() {

		super.close();
	}

	/**
	 * 返回数据库对象
	 */
	private SQLiteDatabase returnDataBase() {
		try {
			SQLiteDatabase database = SQLiteDatabase.openDatabase(databasePath
					+ "/" + mName, mCursorFactory,
					SQLiteDatabase.OPEN_READWRITE);
			Log.w(TAG, "数据库打开成功" + databasePath);
			return database;
		} catch (Exception e) {
			Log.w(TAG, "不能打开数据库" + mName + "-" + e.getMessage());
			return null;
		}

	}

	/**
	 * 拷贝数据库
	 */
	private void copyDatabaseFromAssets() throws SQLiteException {
		Log.w(TAG, "拷贝数据从assets");
		try {
			InputStream inputStream = mContext.getAssets().open(mArchivePath);
			File file = new File(databasePath + "/");
			if (!file.exists()) {
				file.mkdirs();
			}
			ZipInputStream zipInputStream = getFileForZip(inputStream);
			if (zipInputStream == null) {
				throw new SQLiteException("自带数据库文件未找到");
			}
			writeExtractedFileToDisk(zipInputStream, new FileOutputStream(
					databasePath + "/" + mName));
			Log.w(TAG, "拷贝数据完成");
		} catch (FileNotFoundException e) {
			SQLiteException sqLiteException = new SQLiteException("自带数据库文件未找到"
					+ mArchivePath);
			sqLiteException.setStackTrace(e.getStackTrace());
		}

		catch (IOException e) {
			e.printStackTrace();
			SQLiteException sqLiteException = new SQLiteException("自带数据库文件拷贝错误"
					+ mArchivePath);
			sqLiteException.setStackTrace(e.getStackTrace());
		}

	}

	/**
	 * 获得zip文件流
	 */
	private ZipInputStream getFileForZip(InputStream zipInputStream) {
		ZipInputStream zipInputStream2 = new ZipInputStream(zipInputStream);
		ZipEntry zipEntry;
		try {
			while ((zipEntry = zipInputStream2.getNextEntry()) != null) {
				Log.w(TAG, "压缩包文件: '" + zipEntry.getName() + "'...");
			}
			return zipInputStream2;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拷贝文件
	 */
	private void writeExtractedFileToDisk(ZipInputStream zin, OutputStream outs)
			throws IOException {
		byte[] buffer = new byte[1024 * 4];
		int length;
		while ((length = zin.read(buffer)) > 0) {
			outs.write(buffer, 0, length);
		}
		outs.flush();
		outs.close();
		zin.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
