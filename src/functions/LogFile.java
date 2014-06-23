package functions;
import functions.Variables;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * 
 * @author Filip Veronel Enculescu
 */
public class LogFile {

    
    //Metodo para actualizar el log general de todos los usuarios
    public static void updateLog(String op){
        File f = new File("LOG.txt");
        FileWriter fic;
        BufferedWriter br;

        Date d = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd.MM.yyyy 'at:' HH:mm:ss"); 
        String fecha = formateador.format(d);
        String msg   = "<span style=\"color:blue\">EXITO</span>   ----- "+op+" on date: "+fecha+" - User: "+Variables.activeUser;
        
        try{
            fic = new FileWriter(f, true);
            br = new BufferedWriter(fic);
            
            br.write(msg);
            br.write("<br>");
            
            br.close();
            fic.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    
    
    //Metodo para actualizar el log sobre ERRORES
    public static void updateLogError(String op, String sql){
        File f = new File("LOG.txt");
        String msg;
        FileWriter fic;
        BufferedWriter br;

        Date d = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd.MM.yyyy 'at:' HH:mm:ss"); 
        String fecha = formateador.format(d);

        if(sql.equals("")){
            msg   = "<span style=\"color:red\">ERROR</span> ----- "+op+" on date: "+fecha+" - User: "+Variables.activeUser;
        }else{
            msg   = "<span style=\"color:red\">ERROR</span> ----- "+op+" on date: "+fecha+" - User: "+Variables.activeUser+"\n SQL >>>> "+sql;
        }
        
        try{
            fic = new FileWriter(f, true);
            br = new BufferedWriter(fic);
            
            br.write(msg);
            br.write("<br>");
            
            br.close();
            fic.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    
    
    
    
    
    
    
    
}
