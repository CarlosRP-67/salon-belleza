package main.java.com.beauty.spa.salon.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.dto.request.LoginDTORequest;
import main.java.com.beauty.spa.salon.dto.response.LoginDTOResponse;

public class AuthRepository {
    public LoginDTOResponse findUserbyEmail(LoginDTORequest loginDTORequest){
        String sql = "select u.nombre, u.apellido, u.contrasena, r.id_rol " +
                     "from usuarios AS u " +
                     "inner join roles as r " +
                     "on u.id_rol = r.id_rol " +
                     "where u.correo = ?";
        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            pstm.setString(1, loginDTORequest.getEmail());
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new LoginDTOResponse(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("contrasena"),
                        rs.getString("id_rol") 
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar al usuario: " + e.getMessage());
        }
        return null;
    }
}