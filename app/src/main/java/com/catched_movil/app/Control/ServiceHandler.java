package com.catched_movil.app.Control;

import android.util.Log;

import com.catched_movil.app.Control.Constantes;
import com.catched_movil.app.Model.AjaxResultado;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceHandler {

	static String response = null;
	public final static int GET = 1;
	public final static int POST = 2;

	public ServiceHandler() {
	}

	/*
	 * Making service call
	 * @url - url to make request
	 * @method - http request method
	 * */
	public String makeServiceCall(String host,String url, int method) {
		return this.makeServiceCall(host,url, method, null);
	}

	/*
	 * Making service call
	 * @url - url to make request
	 * @method - http request method
	 * @params - http request params
	 * */
	public String makeServiceCall(String host,String url, int method,
                                  List<NameValuePair> params) {
		try {

			url = host+url;

			// http client
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpEntity httpEntity = null;
			HttpResponse httpResponse = null;
			
			// Checking http request method type
			if (method == POST) {
				HttpPost httpPost = new HttpPost(url);
				// adding post params
				if (params != null) {
					httpPost.setEntity(new UrlEncodedFormEntity(params));
				}

				httpResponse = httpClient.execute(httpPost);
		
			} else if (method == GET) {
				// appending params to url
				if (params != null) {
					String paramString = URLEncodedUtils
							.format(params, "utf-8");
					url += "?" + paramString;
				}
				HttpGet httpGet = new HttpGet(url);

				httpResponse = httpClient.execute(httpGet);

			}
			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public AjaxResultado fn_json_to_has_map(JSONObject jsonObj, String[] variables) throws JSONException {

		AjaxResultado resultado = new AjaxResultado();
		ArrayList<HashMap<String, String>> array_final = new ArrayList<HashMap<String, String>>();

		if(!jsonObj.get(Constantes.JSON_MENSAJE).toString().equals("null")){
			JSONArray datos = jsonObj.getJSONArray(Constantes.JSON_MENSAJE);
			String JSON_MENSAJE = "";
			for (int i = 0; i < datos.length(); i++) {
				JSONObject temp = datos.getJSONObject(i);
				JSON_MENSAJE   += JSON_MENSAJE != "" ? temp.getString(Constantes.MSN)+","+JSON_MENSAJE : temp.getString(Constantes.MSN);
			}
			resultado.setMensajes(JSON_MENSAJE);
		}

		if(!jsonObj.get(Constantes.JSON_ERROR).toString().equals("null")){
			JSONArray datos = jsonObj.getJSONArray(Constantes.JSON_ERROR);
			String JSON_ERROR = "";
			for (int i = 0; i < datos.length(); i++) {
				JSONObject temp = datos.getJSONObject(i);
				JSON_ERROR     += JSON_ERROR != "" ? temp.getString(Constantes.MSN)+","+JSON_ERROR : temp.getString(Constantes.MSN);
			}
			resultado.setErrores(JSON_ERROR);
		}

        if(!jsonObj.get(Constantes.JSON_RESULTADO).toString().equals("null")){
			if( variables != null){
				JSONArray datos = jsonObj.getJSONArray(Constantes.JSON_RESULTADO);
				for (int i = 0; i < datos.length(); i++) {
					JSONObject temp = datos.getJSONObject(i);
					HashMap<String, String> contact = new HashMap<String, String>();
					for (int j = 0; j < variables.length ; j++) {
						String dato = temp.getString(variables[j]);
						contact.put(variables[j], dato.equals("null") ? null : dato );
					}
					array_final.add(contact);
				}

				resultado.setResultado_array(array_final);

			}else{
				resultado.setResultado(jsonObj.getString(Constantes.JSON_RESULTADO));
			}

		}

		return resultado;
	}

}
