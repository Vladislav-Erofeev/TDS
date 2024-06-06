package com.example.searchservice.services;

import com.example.searchservice.messages.Item;
import com.example.searchservice.repositories.EsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EsService {
    private final EsRepository esRepository;

    public void deleteById(Long id) throws IOException {
        esRepository.deleteById(String.valueOf(id));
    }

    public void save(Long id, Item item) throws IOException {
        esRepository.save(String.valueOf(id), item);
    }

    public void updateById(Long id, Item item) throws IOException {
        esRepository.updateById(String.valueOf(id), item);
    }

    public List<Item> search(String query) throws IOException {
        return esRepository.search(query);
    }

    public List<Item> searchByQueryAndCodesIn(String query, List<Long> codes) throws IOException {
        return esRepository.searchByQueryAndCodesIn(query, codes);
    }

    public Item getById(Long id) throws IOException {
        return esRepository.getById(String.valueOf(id));
    }

    public void validateItem(Long id, Item item) throws IOException {
        Item item1 = getById(id);
        if (item1 == null)
            save(id, item);
        else
            updateById(id, item);
    }
}
