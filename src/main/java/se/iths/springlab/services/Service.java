package se.iths.springlab.services;

import se.iths.springlab.dtos.MonkeyDto;

import java.util.List;
import java.util.Optional;

public interface Service {

    List<MonkeyDto> getAllMonkeys();

    Optional<MonkeyDto> getOne(Integer id);

    MonkeyDto createMonkey(MonkeyDto monkey);

    void delete(Integer id);

    MonkeyDto replace(Integer id, MonkeyDto monkeyDto);

    MonkeyDto update(Integer id, MonkeyDto monkeyDto);

    List<MonkeyDto> getMonkeyByName(String name);

}
