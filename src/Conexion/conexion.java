
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class conexion {
    private Connection connection;
    private String url= "jdbc:mysql://localhost/usuariosRegistro";
    private String usuario= "root";
    private String pass= "";

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void establecerConexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,usuario,pass);
            
        }catch(ClassNotFoundException ex){
            ex.printStackTrace(); // algun problema con la libreria de sqlConnector 
            JOptionPane.showMessageDialog(null, "error en la libreria de mysqlConnector ");
        }catch(SQLException ex) {
            ex.printStackTrace(); // algun problema con la conexion con la base de datos - xampp
            JOptionPane.showMessageDialog(null," problema con la conexion con la base de datos");
        }
    }
    public void cerrarConexion(){
        try{
            connection.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
