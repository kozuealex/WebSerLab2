package se.iths.springlab.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.iths.springlab.dtos.MonkeyDto;
import se.iths.springlab.entities.Monkey;
import se.iths.springlab.mappers.MonkeyMapper;
import se.iths.springlab.repositories.MonkeyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MonkeyService implements se.iths.springlab.services.Service {

    private final MonkeyMapper monkeyMapper;
    private MonkeyRepository monkeyRepository;

    public MonkeyService(MonkeyRepository monkeyRepository, MonkeyMapper monkeyMapper) {
        this.monkeyRepository = monkeyRepository;
        this.monkeyMapper = monkeyMapper;
    }

    @Override
    public List<MonkeyDto> getAllMonkeys() {
        return monkeyMapper.mapp(monkeyRepository.findAll());
    }

    @Override
    public Optional<MonkeyDto> getOne(Integer id) {
        return monkeyMapper.mapp(monkeyRepository.findById(id));
    }

    @Override
    public MonkeyDto createMonkey(MonkeyDto monkey) {
        if(monkey.getName().isEmpty() || monkey.getType().isEmpty()
                || monkey.getWeight() < 1 || monkey.getGender().isEmpty()) {
            throw new RuntimeException();
        }
        return monkeyMapper.mapp(monkeyRepository.save(monkeyMapper.mapp(monkey)));
    }

    @Override
    public void delete(Integer id) {
        Optional<Monkey> monkey = monkeyRepository.findById(id);
        if(monkey.isPresent()) {
            monkeyRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Id " + id + " not found.");
        }
    }

    @Override
    public MonkeyDto replace(Integer id, MonkeyDto monkeyDto) {
        Optional<Monkey> monkey = monkeyRepository.findById(id);
        if(monkey.isPresent()) {
            Monkey updatedMonkey = monkey.get();
            updatedMonkey.setName(monkeyDto.getName());
            updatedMonkey.setType(monkeyDto.getType());
            updatedMonkey.setWeight(monkeyDto.getWeight());
            updatedMonkey.setGender(monkeyDto.getGender());
            return monkeyMapper.mapp(monkeyRepository.save(updatedMonkey));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Id " + id + " not found.");
        }
    }

    @Override
    public MonkeyDto update(Integer id, MonkeyDto monkeyDto) {
        Optional<Monkey> monkey = monkeyRepository.findById(id);
        if(monkey.isPresent()) {
            Monkey updatedMonkey = monkey.get();
            if(monkeyDto.getName() != null) {
                updatedMonkey.setName(monkeyDto.getName());
            }
            if(monkeyDto.getType() != null) {
                updatedMonkey.setType(monkeyDto.getType());
            }
            if(monkeyDto.getWeight() > 0) {
                updatedMonkey.setWeight(monkeyDto.getWeight());
            }
            if(monkeyDto.getGender() != null) {
                updatedMonkey.setGender(monkeyDto.getGender());
            }
            return monkeyMapper.mapp(monkeyRepository.save(updatedMonkey));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Id " + id + " not found.");
        }
    }

    @Override
    public List<MonkeyDto> getMonkeyByName(String name) {
        List<Monkey> monkey = monkeyRepository.findAllByName(name);
        if(monkey.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Name " + name + " not found.");
        }
        return monkeyMapper.mapp(monkey);
    }

}
