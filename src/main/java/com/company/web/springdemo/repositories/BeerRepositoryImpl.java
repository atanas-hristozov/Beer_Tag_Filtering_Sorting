package com.company.web.springdemo.repositories;

import com.company.web.springdemo.exceptions.EntityNotFoundException;
import com.company.web.springdemo.models.Beer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class BeerRepositoryImpl implements BeerRepository {

    private final List<Beer> beers;

    public BeerRepositoryImpl() {
        beers = new ArrayList<>();
        beers.add(new Beer(1, "Glarus English Ale", 4.6));
        beers.add(new Beer(2, "Rhombus Porter", 5.0));
        beers.add(new Beer(3, "Opasen Char", 6.6));
    }

    @Override
    public List<Beer> get(String name, Double minAbv, Double maxAbv, String sortBy, String sortOrder) {
        List<Beer> result = new ArrayList<>(beers);
        if (name != null && !name.isBlank()) {
            result = result.stream()
                    .filter(beer -> beer.getName().contains(name))
                    .collect(Collectors.toList());
        }
        if (minAbv != null){
            result = result.stream()
                    .filter(beer -> beer.getAbv() >= minAbv).collect(Collectors.toList());
        }
        if (maxAbv != null){
            result = result.stream()
                    .filter(beer -> beer.getAbv() <= maxAbv).collect(Collectors.toList());
        }
        if(sortBy != null){
            if (sortBy.equals("name")){
                Stream<Beer> beerStream = result.stream();
            result = result.stream()
                    .sorted(Comparator.comparing(Beer::getName)).collect(Collectors.toList());
            }
            if (sortBy.equals("abv")){
                Stream<Beer> beerStream = result.stream();
                result = result.stream()
                        .sorted(Comparator.comparing(Beer::getAbv)).collect(Collectors.toList());
            }
        }
        return result;
    }

    @Override
    public Beer get(int id) {
        return beers.stream()
                .filter(beer -> beer.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Beer", id));
    }

    @Override
    public Beer get(String name) {
        return beers.stream()
                .filter(beer -> beer.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Beer", "name", name));
    }

    @Override
    public void create(Beer beer) {
        beers.add(beer);
    }

    @Override
    public void update(Beer beer) {
        Beer beerToUpdate = get(beer.getId());
        beerToUpdate.setName(beer.getName());
        beerToUpdate.setAbv(beer.getAbv());
    }

    @Override
    public void delete(int id) {
        Beer beerToDelete = get(id);
        beers.remove(beerToDelete);
    }

}
