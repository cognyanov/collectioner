package collectioner.service;

import collectioner.model.entity.HeroEntity;
import collectioner.model.entity.UserEntity;

import java.util.List;

public interface RankingsService {
    List<UserEntity> top10ByAttack();
    List<UserEntity> top10ByDef();
    List<UserEntity> top10ByHP();

}
