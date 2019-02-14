
package BaseUsuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;


public class Usuarios {


    private IntegerProperty idUsuario;
    private StringProperty nombreUsuario;
    private StringProperty apellidoUsuario;

    public Usuarios(Integer idUsuario, String nombreUsuario, String apellidoUsuario) {
        this.idUsuario = new SimpleIntegerProperty(idUsuario);
        this.nombreUsuario = new SimpleStringProperty(nombreUsuario);
        this.apellidoUsuario = new SimpleStringProperty(apellidoUsuario);
    }
//getters
    public int getIdUsuario() {
        return idUsuario.get();
    }

    public String getNombreUsuario() {
        return nombreUsuario.get();
    }

    public String getApellidoUsuario() {
        return apellidoUsuario.get();
    }
// setters
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = new SimpleIntegerProperty(idUsuario);
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = new SimpleStringProperty(nombreUsuario);
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = new SimpleStringProperty(apellidoUsuario);
    }
// de los property
    public IntegerProperty idUserProperty(){
        return idUsuario;
    }
    public StringProperty nombreProperty(){
        return nombreUsuario;
    }
    public StringProperty apellidoProperty(){
        return apellidoUsuario;
    }
 
    
    public static void llenarInformacion(Connection connection, ObservableList<Usuarios> listaUsuarios) {
        try{
            Statement statemen = connection.createStatement();
            ResultSet resulset = statemen.executeQuery(
                    "SELECT users.idUsers, " +    
                    "users.nombreUser, " +
                    "users.apellidoUser " +
                    "FROM usuariosRegistro.users"        
            );
            while(resulset.next()){
                listaUsuarios.add(
                                new Usuarios(
                                        resulset.getInt("idUsers"),
                                        resulset.getString("nombreUser"),
                                        resulset.getString("apellidoUser")
                                        )          
                                );
        
                             
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"error en la base de datos en 'llenado de informacion '!");
        }
            
    }  
    public int guardarRegistro(Connection connec){
       
        try{
            PreparedStatement prepa = connec.prepareStatement(
                    "INSERT INTO usuariosRegistro.users (idUsers, nombreUser, apellidoUser) " + "VALUES (NULL,?,?)"
            );
                prepa.setString(1 , nombreUsuario.get()); //.toString()
                prepa.setString(2, apellidoUsuario.get()); //.toString()
                // no guardo el idUsuario del constructor, por que es incremental desde BBDD
                return prepa.executeUpdate();
        }catch(SQLException ex1){
            ex1.printStackTrace();
             JOptionPane.showMessageDialog(null,"error en la base de datos en 'guardarRegistro'!");
            return 0;
        }
    }
    
    public int actualizarRegistro (Connection connec){
        try{
            PreparedStatement prepa = connec.prepareStatement(
                    "UPDATE usuariosRegistro.users " +
                            "SET nombreUser =?, " +
                                "apellidoUser =? " +
                                "WHERE idUsers = ?"
            
            
            );
             prepa.setString(1, nombreUsuario.get());
             prepa.setString(2, apellidoUsuario.get());
             prepa.setInt(3,idUsuario.get());
             //si añado el idUsuario del constructor, por que ya existe y necesito updatear!
                    return prepa.executeUpdate();
        }catch(SQLException ex2){
            ex2.printStackTrace();
             JOptionPane.showMessageDialog(null,"error en actualizar registro'!");
            return 0;
        }
    }
    public int borrarRegistro (Connection connec){
        try{
            PreparedStatement prepa = connec.prepareStatement(
                        "DELETE FROM usuariosRegistro.users " +
                                " WHERE idUsers =? " 

            );
            prepa.setInt(1,idUsuario.get()); // no se por que "1" pero funciona y borra 
            
           return  prepa.executeUpdate();
        }catch(SQLException ex3){
            ex3.printStackTrace();
             JOptionPane.showMessageDialog(null,"error eliminar registro'!");
            return 0;
        }
    }
 @Override   
 public String toString(){
    return idUsuario.toString() + nombreUsuario.toString() + apellidoUsuario.get()  ;
    }
    
}
