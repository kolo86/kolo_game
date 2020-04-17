package com.game.battle.service;

import com.game.account.entity.PlayerEntity;
import com.game.battle.util.FightUtils;
import com.game.common.constant.I18nId;
import com.game.monster.model.Monster;
import com.game.scene.AbstractMapHandler;
import com.game.scene.constant.SceneType;
import com.game.util.PacketUtils;
import org.springframework.stereotype.Component;


/**
 * 〈一句话功能简述〉<br>
 * 〈攻击服务实现类〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
@Component
public class BattleServiceImpl implements IBattleService {

    @Override
    public void battleMonster(PlayerEntity player, int monsterId) {
        SceneType scene = SceneType.getSceneById(player.getMapId());
        AbstractMapHandler handler = scene.getHandler();
        boolean checkMonsterExist = handler.checkMonsterExist(monsterId);

        if(!checkMonsterExist){
            PacketUtils.sendResponse(player, I18nId.MONSTER_DOES_NOT_EXIST);
        } else {
            Monster monster = handler.getMonsterById(monsterId);
            FightUtils.fightMonster(player, monster);
        }
    }
}