package com.lyy.hitogether.datebase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table USER_INFOS.
 */
public class UserInfosDao extends AbstractDao<UserInfos, Long> {

	public static final String TABLENAME = "USER_INFOS";

	/**
	 * Properties of entity UserInfos.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties {
		public final static Property Id = new Property(0, Long.class, "id",
				true, "_id");
		public final static Property Userid = new Property(1, String.class,
				"userid", false, "USERID");
		public final static Property Username = new Property(2, String.class,
				"username", false, "USERNAME");
		public final static Property Portrait = new Property(3, String.class,
				"portrait", false, "PORTRAIT");
		public final static Property Status = new Property(4, String.class,
				"status", false, "STATUS");
	};

	public UserInfosDao(DaoConfig config) {
		super(config);
	}

	public UserInfosDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
	}

	/** Creates the underlying database table. */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "'USER_INFOS' (" + //
				"'_id' INTEGER PRIMARY KEY ," + // 0: id
				"'USERID' TEXT NOT NULL UNIQUE ," + // 1: userid
				"'USERNAME' TEXT NOT NULL ," + // 2: username
				"'PORTRAIT' TEXT," + // 3: portrait
				"'STATUS' TEXT NOT NULL );"); // 4: status
	}

	/** Drops the underlying database table. */
	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "")
				+ "'USER_INFOS'";
		db.execSQL(sql);
	}

	/** @inheritdoc */
	@Override
	protected void bindValues(SQLiteStatement stmt, UserInfos entity) {
		stmt.clearBindings();

		Long id = entity.getId();
		if (id != null) {
			stmt.bindLong(1, id);
		}
		stmt.bindString(2, entity.getUserid());
		stmt.bindString(3, entity.getUsername());

		String portrait = entity.getPortrait();
		if (portrait != null) {
			stmt.bindString(4, portrait);
		}
		stmt.bindString(5, entity.getStatus());
	}

	/** @inheritdoc */
	@Override
	public Long readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
	}

	/** @inheritdoc */
	@Override
	public UserInfos readEntity(Cursor cursor, int offset) {
		UserInfos entity = new UserInfos(
				//
				cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
				cursor.getString(offset + 1), // userid
				cursor.getString(offset + 2), // username
				cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // portrait
				cursor.getString(offset + 4) // status
		);
		return entity;
	}

	/** @inheritdoc */
	@Override
	public void readEntity(Cursor cursor, UserInfos entity, int offset) {
		entity.setId(cursor.isNull(offset + 0) ? null : cursor
				.getLong(offset + 0));
		entity.setUserid(cursor.getString(offset + 1));
		entity.setUsername(cursor.getString(offset + 2));
		entity.setPortrait(cursor.isNull(offset + 3) ? null : cursor
				.getString(offset + 3));
		entity.setStatus(cursor.getString(offset + 4));
	}

	/** @inheritdoc */
	@Override
	protected Long updateKeyAfterInsert(UserInfos entity, long rowId) {
		entity.setId(rowId);
		return rowId;
	}

	/** @inheritdoc */
	@Override
	public Long getKey(UserInfos entity) {
		if (entity != null) {
			return entity.getId();
		} else {
			return null;
		}
	}

	/** @inheritdoc */
	@Override
	protected boolean isEntityUpdateable() {
		return true;
	}

}
