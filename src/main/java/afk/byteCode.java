package afk;

import arc.util.Log;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class byteCode {
    //code
    public static String make(String fileName, JSONObject object) {
        try {
            String userHomePath = System.getProperty("user.home");
            File file = new File(userHomePath+"/mind_db/"+fileName+".cn");
            File path = new File(userHomePath+"/mind_db/");
            if (!path.isDirectory()) {
                Log.err("404 - could not find directory "+userHomePath+"/mind_db/");
                return null;
            }
            if (!file.exists()) file.createNewFile();
            FileWriter out = new FileWriter(file, false);
            PrintWriter pw = new PrintWriter(out);
            pw.println(object.toString(4));
            out.close();
            return "Done";
        } catch (IOException i) {
            i.printStackTrace();
            return "error: \n```"+i.getMessage().toString()+"\n```";
        }
    }
    public static boolean mkdir(String dirName) {
        String userHomePath = System.getProperty("user.home");
        File path = new File(userHomePath+"/"+dirName);
        if (!path.isDirectory()) {
            if (path.mkdir()) return true;
            return false;
        }
        return true;
    }
    public static JSONObject get(String fileName) {
        try {
            String userHomePath = System.getProperty("user.home");
            File file = new File(userHomePath+"/mind_db/"+fileName+".cn");
            File path = new File(userHomePath+"/mind_db/");
            if (!path.isDirectory()) {
                Log.err("404 - could not find directory "+userHomePath+"/mind_db/");
                return null;
            }
            if (!file.exists()) {
                Log.err("404 - "+userHomePath+"/mind_db/"+fileName+".cn"+" not found");
                return null;
            }
            FileReader fr = new FileReader(file);
            StringBuilder builder = new StringBuilder();
            int i;
            while((i=fr.read())!=-1) {
                builder.append((char)i);
            }
            //return null;
            return new JSONObject(new JSONTokener(builder.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String putJObject(String fileName, String key, JSONObject object) {
        try {
            JSONObject data = get(fileName);
            if (data == null) return null;
            data.put(key, object);

            return save(fileName, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String putInt(String fileName, String key, float valueNumber) {
        try {
            JSONObject data = get(fileName);
            if (data == null) return null;
            data.put(key, valueNumber);

            return save(fileName, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String putStr(String fileName, String key, String value) {
        try {
            JSONObject data = get(fileName);
            if (data == null) return null;
            if (!value.equals("")) {
                data.put(key, value);
            } else {
                return "Error - value == \"\" and valueNumber == 0";
            }

            return save(fileName, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String remove(String fileName, String key) {
        try {
            JSONObject data = get(fileName);
            if (data == null) return null;
            data.remove(key);

            return save(fileName, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String save(String fileName, JSONObject object) {
        String userHomePath = System.getProperty("user.home");
        File file = new File(userHomePath+"/mind_db/"+fileName+".cn");
        File path = new File(userHomePath+"/mind_db/");
        if (!path.isDirectory()) {
            Log.err("404 - could not find directory "+userHomePath+"/mind_db/");
            return null;
        }
        if (!file.exists()) {
            Log.err("404 - "+userHomePath+"/mind_db/"+fileName+".cn"+" not found");
            return null;
        }
        try {
            FileWriter out = new FileWriter(file, false);
            PrintWriter pw = new PrintWriter(out);
            pw.println(object.toString(4));
            out.close();
            return "Done";
        } catch (IOException it) {
            it.printStackTrace();
            return "error: \n```"+it.getMessage().toString()+"\n```";
        }
    }
    public static Boolean has(String fileName) {
        String userHomePath = System.getProperty("user.home");
        File file = new File(userHomePath+"/mind_db/"+fileName+".cn");
        File path = new File(userHomePath+"/mind_db/");
        if (!path.isDirectory()) {
            Log.err("404 - could not find directory "+userHomePath+"/mind_db/");
            return false;
        }
        if (file.exists()) {
            return true;
        }
        return false;
    }
    public static Boolean hasDir(String dirName) {
        String userHomePath = System.getProperty("user.home");
        File path = new File(userHomePath+"/"+dirName+"/");
        if (path.isDirectory()) return true;
        return false;
    }
    public static Void assertCore(String filename) {
        JSONObject data = new JSONObject();
        if (!byteCode.hasDir("mind_db")) {
            byteCode.mkdir("mind_db");
        }
        if (!byteCode.has(filename)) {
            data = new JSONObject();
            data.put("messagesPM", 3);
            data.put("bbPM", 15);
            data.put("maxAfk", 5);
            byteCode.make(filename, data);
        }
        if (!data.has("messagesPM")) data.put("messagesPM", 3);
        if (!data.has("bbPM")) data.put("bbPM", 15);
        if (!data.has("maxAfk")) data.put("maxAfk", 5);

        return null;
    }
}