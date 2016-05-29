package util;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domen.Poslanik;

public class PoslanikJsonUtility {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");

	public static JsonArray serialize(LinkedList<Poslanik> poslanici) {
		JsonArray poslaniciNiz = new JsonArray();

		for (int i = 0; i < poslanici.size(); i++) {
			Poslanik p = poslanici.get(i);

			JsonObject poslanikJson = new JsonObject();
			poslanikJson.addProperty("id", p.getId());
			poslanikJson.addProperty("ime", p.getIme());
			poslanikJson.addProperty("prezime", p.getPrezime());
			poslanikJson.addProperty("datumRodjenja", sdf.format(p.getDatumRodjenja()));

			poslaniciNiz.add(poslanikJson);
		}

		return poslaniciNiz;
	}

	public static List<Poslanik> deserialize() throws Exception {
		List<Poslanik> poslanici = new LinkedList<>();

		FileReader in = new FileReader("data/serviceMembers.json");

		JsonArray jArray = new GsonBuilder().create().fromJson(in, JsonArray.class);

		in.close();
		
		JsonObject jObj = null;
		Poslanik p = null;

		for (int i = 0; i < jArray.size(); i++) {

			jObj = (JsonObject) jArray.get(i);

			p = new Poslanik();

			p.setId(jObj.get("id").getAsInt());
			p.setIme(jObj.get("name").getAsString());
			p.setPrezime(jObj.get("lastName").getAsString());
			if (jObj.get("birthDate") != null) {
				try {
					p.setDatumRodjenja(sdf.parse(jObj.get("birthDate").getAsString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
			poslanici.add(p);
		}
		return poslanici;
	}

}
