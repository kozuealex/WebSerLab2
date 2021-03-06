package se.iths.springlab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import se.iths.springlab.dtos.MonkeyDto;
import se.iths.springlab.services.Service;

import java.util.List;

@RestController
public class MonkeyController {

    private Service service;

    public MonkeyController(Service service) {
        this.service = service;
    }

    @GetMapping("/animals")
    public List<MonkeyDto> all() {
        return service.getAllMonkeys();
    }

    @GetMapping("/animals/{id}")
    public MonkeyDto one(@PathVariable Integer id) {
        return service.getOne(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Id " + id + " not found."));

//        var result = monkeyRepository.findById(id);
//        if(result.isPresent()) {
//            return result.get();
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + " not found.");
    }

    @PostMapping("/animals")
    @ResponseStatus(HttpStatus.CREATED)
    public MonkeyDto create(@RequestBody MonkeyDto monkey) {
        return service.createMonkey(monkey);
    }

    @DeleteMapping("/animals/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @PutMapping("/animals/{id}")
    @ResponseBody
    public MonkeyDto replace(@RequestBody MonkeyDto monkeyDto, @PathVariable Integer id) {
        return service.replace(id, monkeyDto);
    }

    @PatchMapping("/animals/{id}")
    @ResponseBody
    public MonkeyDto update(@RequestBody MonkeyDto monkeyDto, @PathVariable Integer id) {
        return service.update(id, monkeyDto);
    }

    @GetMapping(value = "/animals/search", params = "name")
    public List<MonkeyDto> oneName(@RequestParam String name) {
        return service.getMonkeyByName(name);
    }

}
