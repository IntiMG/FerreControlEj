package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.validation.constraints.Min;

import org.openxava.annotations.*;

import com.tuempresa.FerreControl.modelo.Producto;
import com.tuempresa.FerreControl.modelo.Venta;

import lombok.*;

@Entity @Getter @Setter
@View(members="venta, producto, cantidad, precio, iva, subtotal; total")
public class DetalleVenta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Hidden
    private int idDetalleVenta; // Clave para la tabla detalle

    @ManyToOne(fetch = FetchType.LAZY) @Required
    @JoinColumn(name="VENTA_idVenta", nullable=false)
    private Venta venta; // Relacion N:1 con Venta (FK)

    @ManyToOne(fetch = FetchType.LAZY) @Required
    @DescriptionsList(descriptionProperties="nombre, precioVenta")
    private Producto producto;

    @Min(1)
    private int cantidad;

    @Stereotype("MONEY")
    private double precio; // Precio unitario al momento de la venta

    @Min(0)
    private double iva;

    // Subtotal : cantidad * precio
    @Stereotype("MONEY")
    @Depends("cantidad, precio")
    public double getSubtotal() {
        return cantidad * precio;
    }

    // Total : subtotal + iva
    @Stereotype("MONEY")
    @Depends("cantidad, precio, iva")
    public double getTotal() {
        return cantidad * precio + iva;
    }

}
