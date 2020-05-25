package com.example.exam.model;

import org.springframework.data.repository.CrudRepository;

public interface ItemDAO extends CrudRepository<Item, Integer> {

    Iterable<Item> findByCategory(String category);

}
