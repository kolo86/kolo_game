package com.game.item.constant;

import com.frame.threadpool.account.AccountScheduleExecutor;
import com.game.account.entity.PlayerEntity;
import com.game.container.command.RecoverHpCommand;
import com.game.container.command.RecoverMpCommand;
import com.game.util.PacketUtils;

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

            StringBuilder sb = new StringBuilder();
            sb.append("使用道具成功：恢复血量，每秒恢复【").append(args[1]).append("】，持续【").append(args[2]).append("】秒");
            PacketUtils.send(player, sb.toString());
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

            StringBuilder sb = new StringBuilder();
            sb.append("使用道具成功：恢复蓝量，每秒恢复【").append(args[1]).append("】，持续【").append(args[2]).append("】秒");
            PacketUtils.send(player, sb.toString());
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