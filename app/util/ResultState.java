package util;

import play.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResultState {

    static Map<String, Integer> rs = new HashMap<>();

    static {
        rs.put("GUARDADO", 1);
    }


    public static Integer getIndGuardado(){
        return rs.get("GUARDADO");
    }

    public static Date formatDate(String f){
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(f);
        }catch (ParseException pe){
            Logger.debug("NO SE PUDO DAR FORMATO A LA FECHA");
            return null;
        }
    }
}
