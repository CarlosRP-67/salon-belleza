package main.java.com.beauty.spa.salon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.com.beauty.spa.salon.model.Citas;
import main.java.com.beauty.spa.salon.repository.CitasRepository;
import main.java.com.beauty.spa.salon.util.SceneManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CitasController {

    @FXML private ComboBox<String> cmbServicio;
    @FXML private DatePicker dpFecha;
    @FXML private ComboBox<String> cmbHora;
    @FXML private ComboBox<String> cmbMinuto;
    @FXML private ListView<String> lvCitas;

    private final CitasRepository citasRepository = new CitasRepository();
    private final ObservableList<String> listaCitasVisual = FXCollections.observableArrayList();
    private final int ID_EMPLEADO_ACTUAL = 1;

    @FXML
    public void initialize() {
        cmbServicio.setItems(FXCollections.observableArrayList("Corte de Cabello", "Manicura", "Pedicura", "Masaje Relajante"));

        ObservableList<String> horas = FXCollections.observableArrayList();
        for (int i = 8; i <= 19; i++) horas.add(String.format("%02d", i));
        cmbHora.setItems(horas);
        cmbMinuto.setItems(FXCollections.observableArrayList("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"));

        lvCitas.setItems(listaCitasVisual);
        cargarCitasDesdeBD();
    }

    private void cargarCitasDesdeBD() {
        listaCitasVisual.clear();
        int idCliente = SceneManager.getIdClienteActual();
        listaCitasVisual.addAll(citasRepository.obtenerCitasPorCliente(idCliente));
    }

    @FXML
    private void handleAgregarCitaAction(ActionEvent event) {
        String servicioSeleccionado = cmbServicio.getValue();
        LocalDate fecha = dpFecha.getValue();
        String hora = cmbHora.getValue();
        String minuto = cmbMinuto.getValue();

        if (servicioSeleccionado == null || fecha == null || hora == null || minuto == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Complete todos los datos para apartar la cita.");
            return;
        }

        int idServicio = 1; 
        if ("Manicura".equals(servicioSeleccionado)) idServicio = 2;
        if ("Pedicura".equals(servicioSeleccionado)) idServicio = 3;
        if ("Masaje Relajante".equals(servicioSeleccionado)) idServicio = 4;

        LocalTime tiempo = LocalTime.of(Integer.parseInt(hora), Integer.parseInt(minuto));
        LocalDateTime fechaHoraCita = LocalDateTime.of(fecha, tiempo);

        int idCliente = SceneManager.getIdClienteActual();
        Citas nuevaCita = new Citas(0, idCliente, ID_EMPLEADO_ACTUAL, idServicio, fechaHoraCita, "Pendiente");

        if (citasRepository.guardarCita(nuevaCita)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "¡Cita programada correctamente!");
            limpiarCampos();
            cargarCitasDesdeBD();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo agendar la cita.");
        }
    }

    private void limpiarCampos() {
        cmbServicio.setValue(null);
        dpFecha.setValue(null);
        cmbHora.setValue(null);
        cmbMinuto.setValue(null);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}