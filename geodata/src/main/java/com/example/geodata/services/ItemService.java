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
    public Item save(Item item, Long personId) {
        Objects.requireNonNull(item.getCodeId(), "Code не может быть пустым");
        item.setCreationDate(new Date());
        item.setPersonId(personId);
        item.setChecked(Boolean.FALSE);
        return itemRepository.save(item);
    }

    @Transactional
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public void setCheckedById(Long id) {
        Item item = getById(id);
        item.setChecked(true);
        itemRepository.save(item);
    }

    @Transactional
    public Item editById(Long id, Item item) {
        Item item1 = getById(id);
        item.setChecked(false);
        item.setCreationDate(item1.getCreationDate());
        item.setPersonId(item1.getPersonId());
        item.setId(id);
        return itemRepository.save(item);
    }

    public Item getById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        Objects.requireNonNull(optionalItem.orElse(null), "Объект не найден");
        return optionalItem.get();
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public List<Item> getAllByPersonId(Long personId) {
        return itemRepository.findAllByPersonIdOrderByCreationDateDesc(personId);
    }

    public List<Item> getAllByCheckedIsFalse() {
        return itemRepository.findAllByCheckedIsFalse();
    }
}
