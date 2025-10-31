package com.shastino.controlProductos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double porcentajeGanancia;
    private Double costoTotal;
    private Double gananciaGenerada;
    private LocalDate fechaCreacion = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    private List<Item> items;

    // 🔹 Recalcula costo total y ganancia al vuelo
    public void calcularCostos() {
        if (items != null && !items.isEmpty()) {
            this.costoTotal = items.stream().mapToDouble(Item::getCosto).sum();
        } else {
            this.costoTotal = 0.0;
        }
        this.gananciaGenerada = costoTotal * (porcentajeGanancia != null ? porcentajeGanancia : 0) / 100;
    }
}
