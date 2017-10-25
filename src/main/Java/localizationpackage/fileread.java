package localizationpackage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by KAMO on 10/25/2017.
 */
public class fileread {


    //Getting Xpath from Json File
    public List GetXpath() throws IOException, ParseException {

        ArrayList<String> pathValues = new ArrayList<String>();
        FileReader reader = new FileReader("C:\\Webelementdata.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONArray lang = (JSONArray) jsonObject.get("Elements");
        Iterator i = lang.iterator();

        while (i.hasNext()) {

            JSONObject innerObj = (JSONObject) i.next();
            String listvalue = (String) innerObj.get("xpath");
            pathValues.add(listvalue);
        }
        return pathValues;
    }


    //Getting key from Json File

    public ArrayList<String> GetKey() throws IOException, ParseException {

        ArrayList<String> Json_keys = new ArrayList<String>();
        FileReader reader = new FileReader("C:\\Webelementdata.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONArray lang = (JSONArray) jsonObject.get("Elements");

        for (int i=0;i<lang.size();i++){
            JSONObject j_object= (JSONObject) lang.get(i);
            String keys = (String) j_object.get("key");
            Json_keys.add(keys);
        }
        return Json_keys;
    }

}
