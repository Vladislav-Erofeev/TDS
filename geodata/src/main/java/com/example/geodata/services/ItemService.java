package com.example.geodata.services;

import com.example.geodata.domain.entities.Item;
import com.example.geodata.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public void save(Item item) {
        Objects.requireNonNull(item.getCodeId(), "Code не может быть пустым");
        item.setCreationDate(new Date());
        item.setChecked(Boolean.FALSE);
        itemRepository.save(item);
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }
}
