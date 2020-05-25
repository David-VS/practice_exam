package com.example.exam.controller;

import com.example.exam.model.Item;
import com.example.exam.model.ItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/items")
public class ItemController {

    @Autowired
    private ItemDAO dao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Item> getAllItems() {
        return dao.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Item getItemById(@PathVariable(value = "id") int id) {
        //id instanceof Integer -> type nakijken, meestal binnen controle zoals een if

        Optional<Item> found = dao.findById(id);
        if(found.isPresent())
            return found.get();
        else
            return null;
    }

    @RequestMapping(value = "/cat", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Item> getItemsByCategory(@RequestParam(value = "category") String category) {
        return dao.findByCategory(category);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public void createItem(@RequestParam(value = "name") String name,
                           @RequestParam(value = "unitPrice") double unitPrice,
                           @RequestParam(value = "amount") int amount,
                           @RequestParam(value = "category") String category) {

        Item item = new Item(name, unitPrice, amount, category);
        dao.save(item);
    }

    //*
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public void updateItem(@RequestParam(value = "id") int id,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "unitPrice") double price,
                           @RequestParam(value = "amount") int amount,
                           @RequestParam(value = "category") String category) {

        Item item = new Item(name, price, amount, category);
        item.setId(id);
        dao.save(item); //save is zowel voor een insert als een update (check op id if aanwezig)
    }
    //*/

    @RequestMapping(value = "/total", method = RequestMethod.GET)
    @ResponseBody
    public double getTotal() {
        Iterable<Item> items = dao.findAll();
        double subtotal = 0;

        for(Item i : items){
            subtotal += (i.getPrice() * i.getAmount());
        }

        return subtotal;
    }
}
