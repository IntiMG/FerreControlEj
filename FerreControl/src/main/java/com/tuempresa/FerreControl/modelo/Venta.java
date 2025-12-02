package com.tuempresa.FerreControl.modelo;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;

import com.tuempresa.FerreControl.modelo.Cliente;
import com.tuempresa.FerreControl.modelo.DetalleVenta;

import lombok.*;

@Entity @Getter @Setter
@View(
    members =
    "fecha;" +
    "cliente;" +
    "tipoComprobante;" +
    "detalleVenta { detalleVenta }" +
    "total;"
)
public class Venta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVenta;

    @Required
    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY) @Required
    @DescriptionsList(descriptionProperties="nombre")
    private Cliente cliente;

    @Column(length=30)
    private String tipoComprobante;

    @OneToMany(mappedBy="venta", cascade=CascadeType.ALL)
    @ListProperties("producto.nombre, cantidad, precio, subtotal")
    private Collection<DetalleVenta> detalleVenta;

    @Calculation("sum(detalleVenta.subtotal)")
    @Stereotype("MONEY")
    private double total;
}
