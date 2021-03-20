package com.markusdel.heroesapi.service;

import com.markusdel.heroesapi.document.Heroes;
import com.markusdel.heroesapi.repository.HeroesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroesService {

    private final HeroesRepository heroesRepository;

    public HeroesService(HeroesRepository heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public Flux<Heroes> getAllHeroes(){
        return Flux.fromIterable(heroesRepository.findAll());
    }

    public Mono<Heroes> getOneHeroes(String id){
        return Mono.justOrEmpty(heroesRepository.findById(id));
    }

    public Mono<Heroes> saveHeroes(Heroes hero){
        return Mono.justOrEmpty(heroesRepository.save(hero));
    }

    public Mono<Boolean> deleteHeroes(String id){
        heroesRepository.deleteById(id);
        return Mono.just(true);
    }
}
