package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioRepository {

    private static final String SELECT_ALL =
            "SELECT id_servicio, nombre_servicio, descripcion, precio, duracion_minutos "
            + "FROM servicios ORDER BY id_servicio";

    public List<Servicio> findAll() throws SQLException {
        List<Servicio> servicios = new ArrayList<>();

        try (Connection con = DataBaseConnection.getDataBaseConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                servicios.add(new Servicio(
                        rs.getInt("id_servicio"),
                        rs.getString("nombre_servicio"),
                        rs.getString("descripcion"),
                        rs.getBigDecimal("precio"),
                        rs.getInt("duracion_minutos")
                ));
            }
        }
        return servicios;
    }
}