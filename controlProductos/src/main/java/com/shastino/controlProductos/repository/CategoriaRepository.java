package com.shastino.controlProductos.repository;

import com.shastino.controlProductos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
    void deleteByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
