package se.iths.springlab.mappers;

import org.springframework.stereotype.Component;
import se.iths.springlab.dtos.MonkeyDto;
import se.iths.springlab.entities.Monkey;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MonkeyMapper {
    public MonkeyMapper() {
    }

    public MonkeyDto mapp(Monkey monkey) {
        return new MonkeyDto(monkey.getId(), monkey.getName(), monkey.getType(),
                monkey.getWeight(), monkey.getGender());
    }

    public Monkey mapp(MonkeyDto monkeyDto) {
        return new Monkey(monkeyDto.getId(), monkeyDto.getName(), monkeyDto.getType(),
                monkeyDto.getWeight(), monkeyDto.getGender());
    }

    public Optional<MonkeyDto> mapp(Optional<Monkey> optionalMonkey) {
        if (optionalMonkey.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mapp(optionalMonkey.get()));
    }

    public List<MonkeyDto> mapp(List<Monkey> all) {
        return all
                .stream()
                .map(this::mapp)
                .collect(Collectors.toList());

//        List<MonkeyDto> monkeyDtoList = new ArrayList<>();
//        for(var monkey: all) {
//            monkeyDtoList.add(mapp(monkey));
//        }
//        return monkeyDtoList;
    }
}