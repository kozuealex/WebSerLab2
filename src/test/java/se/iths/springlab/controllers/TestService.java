package se.iths.springlab.controllers;

import se.iths.springlab.dtos.MonkeyDto;
import se.iths.springlab.services.Service;

import java.util.List;
import java.util.Optional;

public class TestService implements Service {

    @Override
    public List<MonkeyDto> getAllMonkeys() {
        return null;
    }

    @Override
    public Optional<MonkeyDto> getOne(Integer id) {
        if(id == 1) {
            return Optional.of(new MonkeyDto(1, "Test Monkey", "Test Type",
                    30, "Test Gender"));
        }
        return Optional.empty();
    }

    @Override
    public MonkeyDto createMonkey(MonkeyDto monkey) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public MonkeyDto replace(Integer id, MonkeyDto monkeyDto) {
        return null;
    }

    @Override
    public MonkeyDto update(Integer id, MonkeyDto monkeyDto) {
        return null;
    }

    @Override
    public List<MonkeyDto> getMonkeyByName(String name) {
        return null;
    }
}
