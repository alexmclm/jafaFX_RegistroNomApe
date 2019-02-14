/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXML;

import BaseUsuarios.Usuarios;
import Conexion.conexion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;


public class FxmlUsuariosController implements Initializable {
    @FXML
    private TextField idUsuario;
    @FXML
    private TextField nombreUsuario;
    @FXML
    private TextField apellidoUsuario;
   
    
    @FXML
    private Button guardar;
    @FXML
    private Button nuevo;
    @FXML
    private Button eliminar;
    @FXML
    private Button actualizar;    

    @FXML // recordar que si o si debe ir el @FXML, por que es propio del GUI para inicializar the TABLEVIEW
    private TableView<Usuarios> tablaUsuarios; // para visualizar en la tableview  
    
    private ObservableList<Usuarios> listaUsuarios; 
    
    private conexion connec;

    @FXML
    private TableColumn<Usuarios, Integer> idUsuarioTable;
    @FXML
    private TableColumn<Usuarios, String> nombreUsuarioTable;
    @FXML
    private TableColumn<Usuarios, String> apellidoUsuarioTable;

 
    @Override // indispensable, debe ir OVERRIDE
    public void initialize(URL url, ResourceBundle rb) {
        connec = new conexion();
        connec.establecerConexion();
        listaUsuarios = FXCollections.observableArrayList();
        Usuarios.llenarInformacion(connec.getConnection(),listaUsuarios);
        tablaUsuarios.setItems(listaUsuarios);
        
        //visualizo la table view 
        //setCellValueFactory = funcion para que regrese los valores de la columna del tableVIEW
        // propertyValueFactory<CLASE, TIPO DATO>("ID_DE_LA_CLASE_DADA_EN_EL_CONSTRUCTOR(EN USUARIOS.JAVA)")
        // propertyValueFactory = extrae los valores de la tablaVIEW
        idUsuarioTable.setCellValueFactory(new PropertyValueFactory<Usuarios,Integer>("idUsuario"));
        nombreUsuarioTable.setCellValueFactory (new PropertyValueFactory<Usuarios,String>("nombreUsuario"));
        apellidoUsuarioTable.setCellValueFactory(new PropertyValueFactory<Usuarios,String>("apellidoUsuario"));

        gestionarLosEventos();
        
        connec.cerrarConexion();
    }    
    public void gestionarLosEventos(){
         tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Usuarios>(){
        
            @Override
            public void changed(ObservableValue<? extends Usuarios> obsevable, Usuarios oldValue, Usuarios newValue){
                if(newValue != null){
                    idUsuario.setText(String.valueOf(newValue.getIdUsuario()));
                    nombreUsuario.setText(newValue.getNombreUsuario());
                    apellidoUsuario.setText(newValue.getApellidoUsuario());
                    
                    guardar.setDisable(true);
                    actualizar.setDisable(false);
                    eliminar.setDisable(false);
                    
                     }
                }
            }
    
    );
}
    
    
    @FXML
    public void Guardar() {
        Usuarios usuario = new Usuarios(0,
                nombreUsuario.getText(),
                apellidoUsuario.getText()
        );
        connec.establecerConexion();
        int resultado = usuario.guardarRegistro(connec.getConnection());
        connec.cerrarConexion();
        if(resultado == 1){
            listaUsuarios.add(usuario);
            JOptionPane.showMessageDialog(null, "se ha guardado correctamente la base de datos");
        }
    }

    @FXML
    public void Actualizar() {
        Usuarios usuario = new Usuarios(
                Integer.valueOf(idUsuario.getText()),
                nombreUsuario.getText(),
                apellidoUsuario.getText()
        );
        connec.establecerConexion();
        int result = usuario.actualizarRegistro(connec.getConnection());
        connec.cerrarConexion();
        
        if (result == 1){
            // lo que pasa aca es que al ACTUALIZAR , debo enviar la modificacion a la TABLEVIEW de Innmediato
            listaUsuarios.set(tablaUsuarios.getSelectionModel().getSelectedIndex(), usuario);
            JOptionPane.showMessageDialog(null,"la base ha sido actualizada");
        }
    }

    @FXML
    public void Eliminar() {
        connec.establecerConexion();
                        // de la tabla de usuarios (de tipo tableCollum), lo que hago es seleccionar toda esa tabla
                        // y borrar el registro, pasandole la direccion para conectarse con BBDD
        int resultado = tablaUsuarios.getSelectionModel().getSelectedItem().borrarRegistro(connec.getConnection());
        connec.cerrarConexion();
        if(resultado ==1){
                            // y aca lo que borro es la visualizacion del GUI de la info de la tabla
            listaUsuarios.remove(tablaUsuarios.getSelectionModel().getSelectedIndex());
            JOptionPane.showMessageDialog(null, "la tabla ha sido eliminada");
        }
    }

    @FXML
    public void Nuevo() {
        idUsuario.setText(null);
        nombreUsuario.setText(null);
        apellidoUsuario.setText(null);
        
        guardar.setDisable(false);
        eliminar.setDisable(true);
        actualizar.setDisable(true);
        
    }
        

}
