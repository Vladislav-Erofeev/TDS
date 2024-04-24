package com.example.geodata.services;

import com.example.geodata.domain.entities.Item;
import com.example.geodata.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void save(Item item, Long personId) {
        Objects.requireNonNull(item.getCodeId(), "Code не может быть пустым");
        item.setCreationDate(new Date());
        item.setPersonId(personId);
        item.setChecked(Boolean.FALSE);
        itemRepository.save(item);
    }

    @Transactional
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public Item getById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        Objects.requireNonNull(optionalItem.orElse(null), "Объект не найден");
        return optionalItem.get();
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }
}
