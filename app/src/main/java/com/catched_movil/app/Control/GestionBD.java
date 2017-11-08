package com.catched_movil.app.Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class GestionBD {

	private Bd_origen sqliteHelper;
	private SQLiteDatabase db;

	public GestionBD(Context context) {
		sqliteHelper = new Bd_origen(context);
	}

	public void open() {
		Log.i("SQLite", "Se abre conexion a la base de datos ");
		db = sqliteHelper.getWritableDatabase();
	}

	public void close() {
		Log.i("SQLite", "Se cierra conexion a la base de datos ");
		sqliteHelper.close();
	}

	public Cursor fn_cursor(String tabla, 
							String campos[],
							String condicion, 
							String orden
	                        ) {
		open();
		Log.i("SQLite", "query -> Consulta solo  ");
		// tabla
		// columnas ,
		// selection WHERE ,
		// selectionArgs , groupby, having,
		// orderby
		return db.query(tabla, campos, condicion, null, null, null, orden);
	}

	public void Query(String s){
		open();
		db.execSQL(s);
		close();
	}
	
	public boolean fn_Update(String tabla, ContentValues valores, String condicion) {
		Log.i("SQLite", "UPDATE: id=" + condicion);
		open();
		boolean r = db.update(tabla, valores,condicion, null) > 0;
		close();
		return r;
	}

	public boolean fn_Delete(String tabla, String dato, String campo) {
		Log.i("SQLite", "DELETE: id=" + dato);
		open();
		boolean r = db.delete(tabla, campo + " = " + dato, null) > 0;
		close();
		return r;
	}

	public boolean fn_Insert(String tabla, ContentValues valores) {
		Log.i("SQLite", "INSERT: " + tabla);
		open();
		boolean r = db.insert(tabla, null, valores) > 0;
		close();
		return r;
	}

	public ArrayList<HashMap<String, String>> fn_cursor_to_hasmap(Cursor cursor,
                                                                  String campos[]
	                                                               ) {
		ArrayList<HashMap<String, String>> lista;
		lista = new ArrayList<HashMap<String, String>>();
		while (cursor.moveToNext()) {
			HashMap<String, String> contact = new HashMap<String, String>();
			for (int i = 0; i < campos.length; i++) {
				contact.put(campos[i], cursor.getString(i));
			}
			lista.add(contact);
		}
		close();
		return lista;
	}



	
}
