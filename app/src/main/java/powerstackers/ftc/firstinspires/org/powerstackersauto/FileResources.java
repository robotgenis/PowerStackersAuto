package powerstackers.ftc.firstinspires.org.powerstackersauto;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import powerstackers.ftc.firstinspires.org.powerstackersauto.Paths.ConstantsLoader;

public class FileResources {

    private static String getDir(String file){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/auto/" + file;
    }

    public static String getDataPath(){
        return getDir("data.txt");
    }

    public static String[] readData(){
       return readFile(getDataPath());
    }

    public static void saveData(String[] contents){
        saveFile(getDataPath(), contents);
    }

    public static String[] readSettings(){
        return readFile(ConstantsLoader.getPath());
    }

    public static void saveSettings(String[] contents){
        saveFile(ConstantsLoader.getPath(), contents);
    }

    private static String[] readFile(String path){
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String line = br.readLine();
            ArrayList<String> output = new ArrayList<>();
            while (line != null) {
                if(!line.equals("")) output.add(line);
                line = br.readLine();
            }
            return output.toArray(new String[]{});
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static void saveFile(String path, String[] contents){
        try {

            File f = new File(path);
            if(f.exists()) f.delete();
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            OutputStreamWriter out = new OutputStreamWriter(fOut);

            for(String line: contents){
                out.append(line);
                out.append("\r\n");
            }
            out.close();
            fOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
