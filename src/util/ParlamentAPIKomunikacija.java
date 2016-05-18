package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domen.Poslanik;

public class ParlamentAPIKomunikacija {
	
	private static final String URL = "http://147.91.128.71:9090/parlament/api/members";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	
	public List<Poslanik> vratiPoslanike() throws ParseException{
		try {
			String result = sendGet(URL);
			
			Gson gson = new GsonBuilder().create();
			JsonArray jArray = gson.fromJson(result, JsonArray.class);
			
			List<Poslanik> poslanici = new LinkedList<>();
			
			for (int i = 0; i < jArray.size(); i++) {
				JsonObject jsonPoslanik = (JsonObject) jArray.get(i);
				
				Poslanik p = new Poslanik();
				p.setId(jsonPoslanik.get("id").getAsInt());
				p.setIme(jsonPoslanik.get("ime").getAsString());
				p.setPrezime(jsonPoslanik.get("poslanik").getAsString());
				if(jsonPoslanik.get("datumRodjenja") != null){
					p.setDatumRodjenja(sdf.parse(jsonPoslanik.get("datumRodjenja").getAsString()));
				}
				
				poslanici.add(p);

			}
			return poslanici;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return null;
	}
	
	public static JsonArray vratiPoslanikeUJsonFormatu() throws Exception {
		Gson gson= new GsonBuilder().setPrettyPrinting().create();
		JsonArray jArray = gson.fromJson(sendGet(URL), JsonArray.class);
		return jArray;
	}

	private static String sendGet(String url) throws IOException{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		boolean endReading = false;
		String response = "";

		while (!endReading) {
			String s = in.readLine();

			if (s != null) {
				response += s;
			} else {
				endReading = true;
			}
		}
		in.close();

		return response.toString();
		
	}
	
	
}
