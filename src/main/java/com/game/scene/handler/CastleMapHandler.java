/**
 * FileName: CastleMapHandler
 * Author:   坤龙
 * Date:     2020/4/2 17:55
 * Description: 城堡
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.scene.handler;

import com.game.scene.AbstractMapHandler;
import com.game.scene.constant.SceneType;

/**
 * 〈一句话功能简述〉<br> 
 * 〈城堡〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public class CastleMapHandler extends AbstractMapHandler {

    @Override
    public SceneType getSceneType() {
        return SceneType.CASTLE;
    }

}