package bgu.spl.a2.TestClass;

import bgu.spl.a2.Action;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.util.Map;

/**
 * Created by Stav on 12/21/2017.
 */
public class JsonTest {
    public static void main(String []args) throws FileNotFoundException {
        Gson gson = new Gson();


        FileReader reader = new FileReader(args[0]);

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(reader);// Returns Root element(
        // which is a JsonElement,
        // can be object,array, null
        // or primitive)

        if (element.isJsonObject()) {
            JsonObject info = element.getAsJsonObject();
            System.out.println(info.get("name").getAsString());//read as string
            System.out.println(info.get("model").getAsInt());  //read as integer
            System.out.println(info.get("price").getAsDouble());//read as double

            JsonArray arr = info.getAsJsonArray("colors");//read as array
            for (int i = 0; i < arr.size(); i++) {
                System.out.println(arr.get(i).getAsString());
            }
        }

    }

    //		Gson gson = new Gson();
//
//
//		FileReader reader = new FileReader(args[0]);
//
//		JsonParser parser = new JsonParser();
//		JsonElement element = parser.parse(reader);// Returns Root element(
//		// which is a JsonElement,
//		// can be object,array, null
//		// or primitive)
//
//		if (element.isJsonObject()) {
//			JsonObject info = element.getAsJsonObject();
//			//Not sure about that, might be forbidden
//			actorThreadPool = new ActorThreadPool(info.get("thread").getAsInt());
//			JsonArray computers = info.get("Computers").getAsJsonArray();  //read as array
//			Warehouse warehouse = new Warehouse();
//			for (int i = 0; i < computers.size(); i++) {
//				JsonObject obj = computers.get(i).getAsJsonObject();
//				warehouse.addComputer(obj.get("Type").getAsString(), obj.get("Sig Success").getAsLong(), obj.get("Sig Fail").getAsLong());
//			}
//			JsonArray phase1 = info.get("Phase 1").getAsJsonArray();  //read as array
//			for (int i = 0; i < phase1.size(); i++) {
//				JsonObject obj = phase1.get(i).getAsJsonObject();
//				if (obj.get("Action").getAsString() == "Open Course") {
//					JsonArray jsonArray = obj.get("Prerequisites").getAsJsonArray();
//					LinkedList<String> prerequisits = new LinkedList<>();
//					for (int j = 0; j < jsonArray.size(); j++) {
//						prerequisits.add(jsonArray.get(j).getAsString());
//					}
//					actorThreadPool.submit(new OpenNewCourse(obj.get("Course").toString(), obj.get("Space").getAsInt(), prerequisits), obj.get("Department").getAsString(), new DepartmentPrivateState());
//				}
//			}
//
//
//			System.out.println(info.get("model").getAsInt());  //read as integer
//			System.out.println(info.get("price").getAsDouble());//read as double
//
//			JsonArray arr = info.getAsJsonArray("colors");//read as array
//			for (int i = 0; i < arr.size(); i++) {
//				System.out.println(arr.get(i).getAsString());
//			}
//		}
//
//        String json2 = "[{\"name\":\"mkyong\"}, {\"name\":\"laplap\"}]";
//        //JsonObject jsonObject = new JsonParser().parse(json2).getAsJsonObject();
//        JsonArray jsonObject1 = new JsonParser().parse(json2).getAsJsonArray();
//
//        System.out.println(jsonObject1.get(0).getAsString());
//        jsonObject1.
//        System.out.println(jsonObject1.get("Sig Success").getAsString());
//        System.out.println(jsonObject1.get("Type").getAsString());

//        JsonElement json = null;
//        try {
            //json = gson.fromJson(new FileReader(args[0]), JsonElement.class);
//            String jsonInfo = "{\"name\":\"mkyong\", \"age\":33}";
//            JsonReader jsonReader = gson.newJsonReader(new FileReader(args[0]));
//            JsonReader jsonReader = gson.newJsonReader(new StringReader(jsonInfo));
//            //jsonReader.beginArray();
//            String bla = gson.fromJson(jsonReader, String.class);
//        String jsonInString = "{'name' : 'mkyong' , " +
//                "'action' : 'OpenCourse' }";
//        String json = "{\"name\":\"mkyong\", \"action\":33}";
//        String json2 = "{\n" +
//                "\"Type\":\"A\",\n" +
//                "\"Sig Success\": \"1234666\",\n" +
//                "\"Sig Fail\": \"999283\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"Type\":\"B\",\n" +
//                "\"Sig Success\": \"4424232\",\n" +
//                "\"Sig Fail\": \"5555353\"\n" +
//                "}";
//        Staff staff = gson.fromJson(json, Staff.class);
//        String[] ss = gson.fromJson(json2, String[].class);
    //    jsonReader.beginArray();

//        String json = "{\"name\":\"mkyong\", \"age\":33}, " +
//                "{\"name\":\"stav\", \"age\":25}";
//        JsonReader jsonReader = gson.newJsonReader(new StringReader(json));
//        try {
//            jsonReader.beginArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
//        map.forEach((x,y)-> System.out.println("key : " + x + " , value : " + y));

//            System.out.println(staff.getAction());
            //gson
//            if (jsonReader.nextString() == "Action:Open Course"){

//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String result = gson.toJson(json);
//        try {
//            staff = gson.fromJson(staff, new FileReader(args[0]));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            FileInputStream fin = new FileInputStream(args[0]);
//            ObjectInputStream ofin = new ObjectInputStream(fin);
//            JsonObject jsonObject = new JsonObject(new JsonToken(fin));
//            ofin.read();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        int num = gson.fromJson(args[0], int.class);


}
