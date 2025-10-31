package com.shastino.controlProductos.service;

import com.shastino.controlProductos.entity.Item;
import com.shastino.controlProductos.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> listarItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> obtenerItemPorNombre(String nombre) {
        return itemRepository.findByNombre(nombre);
    }

    public Item guardarItem(Item item) {
        return itemRepository.save(item);
    }

    public Item actualizarItem(String nombre, Item itemActualizado) {
        Item item = itemRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        item.setNombre(itemActualizado.getNombre());
        item.setCosto(itemActualizado.getCosto());
        return itemRepository.save(item);
    }

    public void eliminarItemPorNombre(String nombre) {
        itemRepository.deleteByNombre(nombre);
    }
}
