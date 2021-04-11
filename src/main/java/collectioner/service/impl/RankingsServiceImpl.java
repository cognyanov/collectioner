package collectioner.service.impl;


import collectioner.model.entity.UserEntity;

import collectioner.repository.UserRepository;
import collectioner.service.RankingsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingsServiceImpl implements RankingsService {


    private final UserRepository userRepository;

    public RankingsServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override
    public List<UserEntity> top10ByAttack() {
    
        return userRepository.findAll()
                .stream()
                .sorted((a,b) -> Integer.compare(b.getHero().getAttack(), a.getHero().getAttack()))
                .limit(10)
                .collect(Collectors.toList());

    }

    @Override
    public List<UserEntity> top10ByDef() {
        return userRepository.findAll().stream().sorted((a,b) -> Integer.compare(b.getHero().getDefense(), a.getHero().getDefense())).limit(10).collect(Collectors.toList());

    }

    @Override
    public List<UserEntity> top10ByHP() {
        return userRepository.findAll().stream().sorted((a,b) -> Integer.compare(b.getHero().getHP(), a.getHero().getHP())).limit(10).collect(Collectors.toList());

    }
}
