package bgu.spl.mics.application;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;

import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.LandoMicroservice;
import bgu.spl.mics.application.services.LeiaMicroservice;
import bgu.spl.mics.application.services.R2D2Microservice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) throws IOException {

		//Creating a JSONParser object
		JSONParser jsonParser = new JSONParser();
		String inputDirection = args[1];
		String contents = new String((Files.readAllBytes(Paths.get(inputDirection))));
		Attack[] attacks;
		Diary diary=Diary.getInstance();
		int place=0;
		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(contents);
			Long r2d2D=(Long) jsonObject.get("R2D2");
			Long landoD=(Long) jsonObject.get("Lando");
			Long ewoksN=(Long) jsonObject.get("Ewoks");
			JSONArray attacksArray = (JSONArray) jsonObject.get("attacks");
			attacks = new Attack[attacksArray.size()];
			Iterator<JSONObject> iterator = attacksArray.iterator(); //Iterating the contents of the array
			while (iterator.hasNext()) {
				JSONObject objec = (JSONObject) iterator.next();
				JSONArray ewoks = (JSONArray) objec.get("serials");
				LinkedList<Integer> serial = new LinkedList<>();
				for (int i = 0; i < ewoks.size(); i++) {
					Long temp = (Long) ewoks.get(i);
					serial.add(temp.intValue());
				}
				attacks[place] = new Attack(serial, ((Long) objec.get("duration")).intValue());
				place++;
			}
			Ewoks supply=Ewoks.getInstance();
			for(int i=0;i<ewoksN;i++){
				supply.addEwok();
			}
			LeiaMicroservice leia = new LeiaMicroservice(attacks);
			R2D2Microservice r2d2=new R2D2Microservice(r2d2D);
			LandoMicroservice lando=new LandoMicroservice(landoD);
			//initializing everyone else
			//  String outputName=args[2];
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}
}