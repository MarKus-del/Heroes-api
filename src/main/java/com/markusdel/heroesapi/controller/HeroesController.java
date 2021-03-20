package com.markusdel.heroesapi.controller;

import com.markusdel.heroesapi.document.Heroes;
import com.markusdel.heroesapi.repository.HeroesRepository;
import com.markusdel.heroesapi.service.HeroesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.markusdel.heroesapi.constants.HeroesConstant.HEROES_ENDPOINT_LOCAL;

@RestController
@Slf4j
public class HeroesController {

    @Autowired
    private HeroesRepository heroesRepository;

    @Autowired
    private HeroesService heroesService;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HeroesController.class);

    @GetMapping(HEROES_ENDPOINT_LOCAL+"/hello")
    public String helloAPI() {
        return "hello from your api";
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL)
    public Flux<Heroes> getAllItens() {
        log.info("requesting the list off all heroes");
        return heroesService.getAllHeroes();
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
    public Mono<ResponseEntity<Heroes>> getOneItem(@PathVariable String id) {
        log.info("requesting the hero with id {}", id);
        return heroesService.getOneHeroes(id)
                .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.FOUND));
    }

    @PostMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Heroes> createHero(@RequestBody Heroes hero){
        log.info("a new hero was created");
        return heroesService.saveHeroes(hero);
    }

    @DeleteMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Mono<HttpStatus> deleteHeroByID(@PathVariable String id){
        log.info("deleting a hero with id {}", id);
        heroesService.deleteHeroes(id);
        return  Mono.just(HttpStatus.NOT_FOUND);
    }
}
