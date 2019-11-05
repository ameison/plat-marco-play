package util;

import play.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static Date parseDateInicial(String fecha){
        //FORMATO anio/mes/dia
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return formatter.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateFinal(String fecha){
        //FORMATO anio/mes/dia
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
        try {
            return formatter.parse(fecha + " 23:59:59 PM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateTemp(String fecha){
        //FORMATO dia/mes/anio
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Logger.info("Se parseo la fecha recibida : " + fecha);
            return format.parse(fecha);
        }catch (ParseException pe){
            pe.printStackTrace();
        }
        return null;
    }

    public static String parseDate(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

    public static String parseDateTime(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(date);
    }

    public static String capitalize(final String line) {
        if (line.length() > 1){
            return Character.toUpperCase(line.charAt(0)) + line.substring(1);
        }else{
            return "";
        }
    }

}
