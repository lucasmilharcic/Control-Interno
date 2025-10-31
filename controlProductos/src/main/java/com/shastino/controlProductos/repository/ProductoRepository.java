package com.shastino.controlProductos.repository;

import com.shastino.controlProductos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre(String nombre);
    void deleteByNombre(String nombre);
    boolean existsByNombre(String nombre);

    @Query("SELECT p FROM Producto p WHERE MONTH(p.fechaCreacion) = :mes AND YEAR(p.fechaCreacion) = :anio")
    List<Producto> findByMesYAnio(int mes, int anio);
}
