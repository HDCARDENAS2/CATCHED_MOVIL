package com.catched_movil.app.Control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Bd_origen extends SQLiteOpenHelper {

	private static final int VERSION_BASEDATOS    = 1;
	private static final String NOMBRE_BASEDATOS  = "CATCHED";
    public static final String PARAMETOS          = "PARAMETOS";
	public static final String ID_PARAMETRO       = "ID_PARAMETRO";
	public static final String VALOR_PARAMETRO    = "VALOR_PARAMETRO";
	public static final String[]P_PARAMETOS       =  {ID_PARAMETRO,VALOR_PARAMETRO};

	private static final String TB_PARAMETOS =
			   "CREATE TABLE "+PARAMETOS+" ("
			    + "  "+ID_PARAMETRO+"  INTEGER NOT NULL"
			    + " ,"+VALOR_PARAMETRO+"  TEXT    NOT NULL"
				+ " );";

	public Bd_origen(Context context) {
		super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TB_PARAMETOS);
		Log.e(this.getClass().toString(), "Base de datos creada");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP PARAMETOS IF EXISTS ALERTAS"  );
		onCreate(db);

	}

}
