package main.java.com.beauty.spa.salon.service;

import main.java.com.beauty.spa.salon.model.Factura;
import main.java.com.beauty.spa.salon.repository.FacturaRepository;
import java.util.List;

public class FacturaService {

    private final FacturaRepository facturaRepository = new FacturaRepository();

    public boolean registrarFactura(int idCliente, String fechaFactura, double total) {
        Factura factura = new Factura(idCliente, fechaFactura, total);
        return facturaRepository.registrar(factura);
    }

    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.listarTodas();
    }
}