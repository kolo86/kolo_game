package com.game.scene.command;

import com.frame.threadpool.scene.AbstractSceneCommand;
import com.game.account.entity.PlayerEntity;

/**
 * 〈一句话功能简述〉<br>
 * 〈进入地图命令〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public class EnterSceneCommand extends AbstractSceneCommand {

    private PlayerEntity player;

    private int newMapId;

    public static EnterSceneCommand valueOf(PlayerEntity player, int newMapId){
        EnterSceneCommand command = new EnterSceneCommand(newMapId);
        command.player = player;
        command.newMapId = newMapId;
        return command;
    }

    private EnterSceneCommand(int newMapId){
        super(newMapId);
    }

    @Override
    public void action() {



    }

}