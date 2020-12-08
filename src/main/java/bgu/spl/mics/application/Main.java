package bgu.spl.mics.application;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.passiveObjects.Input;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.transform.Result;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) throws IOException {
		Gson gson=new Gson();
		BufferedReader reader=new BufferedReader(new FileReader(args[0]));
		Input attackobj= gson.fromJson(reader, Input.class);
		long ewoks=attackobj.getEwoks();
		long landoD=attackobj.getLando();
		long r2d2D=attackobj.getR2D2();
		Attack [] attacks=new Attack[attackobj.getAttacks().length];
		int i=0;
		for(Attack attack : attackobj.getAttacks()) {
			attacks[i]=new Attack(attack.getSerials(),attack.getDuration());
			i++;
		}
		Ewoks supply=Ewoks.getInstance();
		for(i=0;i<ewoks;i++){
			supply.addEwok();
		}
			C3POMicroservice c3po=new C3POMicroservice();
			HanSoloMicroservice hanSolo=new HanSoloMicroservice();
			LeiaMicroservice leia = new LeiaMicroservice(attacks);
			R2D2Microservice r2d2=new R2D2Microservice(r2d2D);
			LandoMicroservice lando=new LandoMicroservice(landoD);
			Thread t1=new Thread(hanSolo);
			Thread t2=new Thread(c3po);
			Thread t3=new Thread(leia);
			Thread t4=new Thread(r2d2);
			Thread t5=new Thread(lando);
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			try{
				t1.join();
				t2.join();
				t3.join();
				t4.join();
				t5.join();
			}
			catch (InterruptedException e){}

			//  String outputName=args[2];
	}
}