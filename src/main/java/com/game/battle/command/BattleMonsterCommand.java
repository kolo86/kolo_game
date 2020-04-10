package com.game.battle.command;

import com.frame.threadpool.battle.AbstractBattleMonsterCommand;
import com.game.account.entity.PlayerEntity;
import com.game.battle.service.BattleServiceImpl;
import com.game.common.SpringContext;

/**
 * 〈一句话功能简述〉<br>
 * 〈攻击怪物命令〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public class BattleMonsterCommand extends AbstractBattleMonsterCommand {

    private PlayerEntity player;

    private int monster;

    public static BattleMonsterCommand valueOf(PlayerEntity player, int monster){
        // 根据玩家当前所在的地图ID和怪物ID做标识
        int key = player.getMapId() + monster;
        BattleMonsterCommand command = new BattleMonsterCommand(key);
        command.player = player;
        command.monster = monster;
        return command;
    }

    private BattleMonsterCommand(int key){
        super(key);
    }

    @Override
    public void action() {
        BattleServiceImpl battleService = (BattleServiceImpl) SpringContext.getBean(BattleServiceImpl.class);
        battleService.battleMonster(player, monster);
    }
}