package com.shastino.controlProductos.controller;

import com.shastino.controlProductos.entity.Item;
import com.shastino.controlProductos.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> listarItems() {
        return itemService.listarItems();
    }

    @PostMapping
    public Item crearItem(@RequestBody Item item) {
        return itemService.guardarItem(item);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminarItem(@PathVariable String nombre) {
        itemService.eliminarItemPorNombre(nombre);
        return ResponseEntity.noContent().build();
    }
}
