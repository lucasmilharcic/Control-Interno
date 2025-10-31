package com.shastino.controlProductos.repository;

import com.shastino.controlProductos.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByNombre(String nombre);
    void deleteByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
