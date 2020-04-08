/**
 * FileName: MainCityMapHandler
 * Author:   坤龙
 * Date:     2020/4/2 17:34
 * Description: 起始之地
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.scene.handler;

import com.game.account.entity.PlayerEntity;
import com.game.npc.constant.NpcEnum;
import com.game.scene.AbstractMapHandler;
import com.game.scene.constant.SceneType;

/**
 * 〈一句话功能简述〉<br> 
 * 〈起始之地〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public class MainCityMapHandler extends AbstractMapHandler {

    @Override
    public SceneType getSceneType() {
        return SceneType.MAINCITY;
    }

    /**
     * 构造函数
     *
     */
    public MainCityMapHandler(){
        super();
        getNpcSet().add(NpcEnum.VILLAGER.getId());
    }

    /**
     * 检查切图条件
     *
     * @param player
     * @return
     */
    @Override
    public boolean checkChangeMapCondition(PlayerEntity player, int oldMapId) {
        if(player.getMapId() == SceneType.NONE.getMapId()){
            return true;
        }
        return super.checkChangeMapCondition(player, oldMapId);
    }
}