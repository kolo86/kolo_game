package com.game.item.constant;

import com.frame.threadpool.account.AccountScheduleExecutor;
import com.game.account.entity.PlayerEntity;
import com.game.container.command.RecoverHpCommand;
import com.game.container.command.RecoverMpCommand;
import com.game.util.PacketUtils;
import com.netty.common.ProtocolEnum;
import com.netty.proto.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈恢复类型〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
public enum  RecoverType {

    /** 恢复血量 */
    HP("MAX_HP"){
        @Override
        public void useItem(PlayerEntity player, String[] args) {
            long recoverHp = Long.parseLong(args[1]);
            long invalidTime = System.currentTimeMillis() + Long.parseLong(args[2]) * 1000;
            RecoverHpCommand command = RecoverHpCommand.valueOf(player, 0, 1000, recoverHp, invalidTime);

            AccountScheduleExecutor.submit(command);

            Message.Sm_UseItem smUseItem = Message.Sm_UseItem.newBuilder().setResult("使用道具成功：恢复血量，每秒恢复【" + args[1] + "】，持续【" + args[2] + "】秒").build();
            PacketUtils.send(player, ProtocolEnum.Sm_UseItem.getId(),smUseItem.toByteArray());
        }
    },
    /** 恢复蓝量 */
    MP("MAX_MP"){
        @Override
        public void useItem(PlayerEntity player, String[] args) {
            long recoverMp = Long.parseLong(args[1]);
            long invalidTime = System.currentTimeMillis() + Long.parseLong(args[2]) * 1000;
            RecoverMpCommand command = RecoverMpCommand.valueOf(player, 0, 1000, recoverMp, invalidTime);

            AccountScheduleExecutor.submit(command);

            Message.Sm_UseItem smUseItem = Message.Sm_UseItem.newBuilder().setResult("使用道具成功：恢复蓝量，每秒恢复【" + args[1] + "】，持续【" + args[2] + "】秒").build();
            PacketUtils.send(player, ProtocolEnum.Sm_UseItem.getId(),smUseItem.toByteArray());
        }
    }

    ;
    private String name;
    /** Map<恢复字符串， 恢复类型></> */
    private static final Map<String, RecoverType> RECOVER_TYPE_MAP = new HashMap<>();

    static{
        for(RecoverType type : values()){
            RECOVER_TYPE_MAP.put(type.getName(), type);
        }
    }

    /**
     * 获取恢复类型
     *
     * @param name
     * @return
     */
    public static RecoverType getType(String name){
        return RECOVER_TYPE_MAP.get(name);
    }

    RecoverType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 玩家使用恢复类道具
     *
     * @param player
     * @param args
     */
    public abstract void useItem(PlayerEntity player, String[] args);
}