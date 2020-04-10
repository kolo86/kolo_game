package com.game.scene.command;

import com.frame.threadpool.scene.AbstractSceneCommand;
import com.game.account.entity.PlayerEntity;

/**
 * 〈一句话功能简述〉<br>
 * 〈退出场景命令〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public class QuitSceneCommand extends AbstractSceneCommand {

    private PlayerEntity player;

    private int oldMap;

    public static QuitSceneCommand valueOf(PlayerEntity player, int oldMap){
        QuitSceneCommand command = new QuitSceneCommand(oldMap);
        command.player = player;
        command.oldMap = oldMap;
        return command;
    }

    private QuitSceneCommand(int oldMap){
        super(oldMap);
    }

    @Override
    public void action() {

    }
}