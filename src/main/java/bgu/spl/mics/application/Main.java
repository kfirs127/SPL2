package bgu.spl.mics.application;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;

import bgu.spl.mics.application.passiveObjects.Attack;
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
		String inputDirection = "C:\\Users\\barda\\Desktop\\input.json";
		String contents = new String((Files.readAllBytes(Paths.get(inputDirection))));
		Long a;
		LinkedList<Attack> attack=new LinkedList<>();
		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(contents);
			JSONArray jsonArray = (JSONArray) jsonObject.get("attacks");
			//Iterating the contents of the array
			Iterator<JSONObject> iterator = jsonArray.iterator();
			while(iterator.hasNext()) {
				JSONObject objec = (JSONObject) iterator.next();
				a = (Long) objec.get("duration");
				JSONArray ewoks = (JSONArray) objec.get("serials");
				LinkedList<Integer> serial = new LinkedList();
				for (int i = 0; i < ewoks.size(); i++) {
					Long temp=(long) ewoks.get(i);
					serial.add(temp.intValue());
					attack.add(new Attack(serial, a.intValue()));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//  String outputName=args[2];
	}
}
