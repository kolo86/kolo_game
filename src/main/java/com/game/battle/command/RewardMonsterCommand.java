package com.game.battle.command;

import com.frame.threadpool.account.AbstractAccountCommand;
import com.game.account.entity.PlayerEntity;
import com.game.common.SpringContext;
import com.game.item.AbstractItem;
import com.game.packback.service.BackpackServiceImpl;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈发放怪物奖励的命令〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
public class RewardMonsterCommand extends AbstractAccountCommand {
    /** 玩家账号 */
    private PlayerEntity player;
    /** 奖励的道具 */
    private List<AbstractItem> list;

    RewardMonsterCommand(PlayerEntity player){
        super(player.getAccountId());
    }

    /**
     * 创建发放奖励命令
     *
     * @param player
     * @param list
     * @return
     */
    public static RewardMonsterCommand valueOf(PlayerEntity player, List<AbstractItem> list){
        RewardMonsterCommand command = new RewardMonsterCommand(player);
        command.player = player;
        command.list = list;
        return command;
    }

    @Override
    public void action() {
        BackpackServiceImpl backpackService = (BackpackServiceImpl)SpringContext.getBean(BackpackServiceImpl.class);
        backpackService.addItems(player, list);
    }


}