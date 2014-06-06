package gestor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;




public class BD {
    static Connection conexion;
    
    public static void Init(){
         //Cargar el driver
        try{
		   Class.forName("com.mysql.jdbc.Driver");
		   // Establecemos la conexion con la BD
		   conexion = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/GestorBD","root", "");   
          
	  }catch (ClassNotFoundException cn) {cn.printStackTrace();
          }catch (SQLException e) { e.printStackTrace();}
		  
    }
    
    //Metodo para verificar la existencia de un usuario y su contraseña
    public static boolean verifyUser(String us, String pas){
        String nombre="";
        String pass="";
        String sql="";
        boolean resp=false;
        
        try{
		   //Preparamos la consulta
                   sql= "SELECT * FROM Users WHERE name=?";
                   PreparedStatement sentencia = conexion.prepareStatement(sql);

                   sentencia.setString(1, us);
                   ResultSet resul = sentencia.executeQuery (); //solo para SELECT
                   
                   
		   // Recorremos el resultado para visualizar cada fila
		   // Se hace un bucle mientras haya registros, se van visualizando
		   while (resul.next()){
		     System.out.println (resul.getInt(1) + " " + resul.getString(2)+ " " +resul.getString(3));
                     nombre = resul.getString(2);
                     pass = resul.getString(3);
		   }
		 
                   if(us.equals(nombre) & pas.equals(pass)){
                       resp=true;
                       System.out.println("resp: "+resp);
                   }
                   
		   resul.close();// Cerrar ResultSet
		   sentencia.close();// Cerrar Statement
          }catch (SQLException e) { updateLogError("Verificación credenciales Usuario fallida", sql);} 
        return resp;
    }

    
    //Metodo para dar de alta nuevo usuario en la base de datos
    public static void nuevoUsuario(String us, String pas){
           try{	
                 //construir orden INSERT y preparamos la sentencia
		 String sql= "INSERT INTO Users VALUES ( ?, ?, ?)";
		 System.out.println(sql);
		 PreparedStatement sentencia = conexion.prepareStatement(sql);
                 
		 sentencia.setInt(1, 0);
		 sentencia.setString(2,us);
		 sentencia.setString(3,pas);
		 
		 int filas = sentencia.executeUpdate();  
		 System.out.println("Filas afectadas: "+filas); 
		 if(filas==0){
                     updateLogError("Inserción nuevo Usuario fallida", sql);
                 }
		
  	         // Cerrar Statement
		 sentencia.close();    	   
          }catch (SQLException e) {e.printStackTrace();} 
    }

    
    //Metodo para eliminar un usuario
    public static void eliminarUsuario(String us){
        String sql="";
          try{
                 //construir orden delete
		 sql= "DELETE FROM Users WHERE name=?";
		 System.out.println(sql);
		 // Preparamos la sentencia
		 PreparedStatement sentencia = conexion.prepareStatement(sql);
		 
		 sentencia.setString(1, us);
		 sentencia.executeUpdate();  
		 		 
		
  	         // Cerrar Statement
		 sentencia.close();    
          }catch (SQLException e) {updateLogError("Borrado Usuario fallida", sql);} 
    }
    
    
    //Metodo para rellenar la tabla con usuarios
    public static ArrayList llenarTablaUsuarios(){
        ArrayList ar = new ArrayList();    
        String sql="SELECT * FROM Users";
        try{
                    // Preparamos la consulta
		   Statement sentencia = conexion.createStatement();
		   ResultSet resul = sentencia.executeQuery (sql);  //executequerry solo para SELECT

                   
		   // Recorremos el resultado para visualizar cada fila
		   // Se hace un bucle mientras haya registros, se van visualizando
		   while (resul.next()){
                        System.out.println(resul.getInt(1)+" "+resul.getString(2)+ " / " +resul.getString(3));
                        ar.add(resul.getString(2)+ " / " +resul.getString(3));
		   }
                   
		   resul.close();// Cerrar ResultSet
		   sentencia.close();// Cerrar Statement
	  }catch (SQLException e) { updateLogError("Llenado tabla usuarios fallida", sql);}     
        return ar;
    }

    
    //Metodo para actualizar el log general de todos los usuarios
    public static void updateLog(String op){
        File f = new File("LOG.txt");
        FileWriter fic;
        BufferedWriter br;

        Date d = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd.MM.yyyy 'a las:' HH:mm:ss"); 
        String fecha = formateador.format(d);
        String msg   = "<span style=\"color:blue\">EXITO</span>   ----- "+op+" en fecha: "+fecha+" - Usuario: "+Variables.activeUser;
        
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
        SimpleDateFormat formateador = new SimpleDateFormat("dd.MM.yyyy 'a las:' HH:mm:ss"); 
        String fecha = formateador.format(d);

        if(sql.equals("")){
            msg   = "<span style=\"color:red\">ERROR</span> ----- "+op+" en fecha: "+fecha+" - Usuario: "+Variables.activeUser;
        }else{
            msg   = "<span style=\"color:red\">ERROR</span> ----- "+op+" en fecha: "+fecha+" - Usuario: "+Variables.activeUser+"\n SQL >>>> "+sql;
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


    
    
    //Metodo para listar todas la bases de datos
    public static ArrayList getListDB(){
           ArrayList bases  =  new ArrayList();    
 
           try{
                ResultSet rs = conexion.getMetaData().getCatalogs();
                while (rs.next()) {
                    bases.add(rs.getString("TABLE_CAT"));
                }
                
	  }catch (SQLException e){ 
              //updateLogError("Llenado tabla usuarios fallida", sql);
          }  
          return bases;
    }
    
    

    public static ArrayList getTablesDB(String tabla){       
           ArrayList a =  new ArrayList();
           Connection con;   
           
           try{    		   
                con = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/"+tabla,"root", "");  
                Statement sentencia = con.createStatement();
                
                DatabaseMetaData md = con.getMetaData();
                ResultSet rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
                   a.add(rs.getString(3));
                }

           }catch (SQLException e){ 
               e.printStackTrace();
           }
           return a;
    }


    
    // -------------  Falta Desarrollo --------------------------//
    //Metodo para ejecutar los comandos sql introducidos por el usuario
    public static String ejecutar_cmd(String sqli, String base){

        if(sqli.contains("SELECT")){      
            try {
                String sql = sqli;

                // Establecemos la conexion con la BD
                conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + base, "root", "");

                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resul = sentencia.executeQuery(); //solo para SELECT

                //String[] r = resul;
                //return resul;
                resul.close();// Cerrar ResultSet
                sentencia.close();// Cerrar Statement

                return "Bad Sql Command";
            } catch (SQLException e) {
                return e.toString(); //.printStackTrace();
            }         
        }
        
        
        if(sqli.contains("INSERT")){
            
            
            
        }
        
        
        if(sqli.contains("DELETE")){
            
            
            
        }
        
        if(sqli.contains("UPDATE")){
            
            
            
        }
        
        return "";
    }
    
    
    
    
    
    
    
    
    
    
}
