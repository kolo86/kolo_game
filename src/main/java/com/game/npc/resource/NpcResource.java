/**
 * FileName: NpcResource
 * Author:   坤龙
 * Date:     2020/4/7 17:36
 * Description: Npc配置表
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.npc.resource;

import com.frame.resource.AbstractResource;
import com.frame.resource.anno.Resource;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Npc配置表〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
@Data
@Resource
public class NpcResource extends AbstractResource {

    /** npcId */
    private int id;
    /** npc名字 */
    private String name;
    /** npc提示语 */
    private String desc;

    @Override
    public int getResourceId() {
        return id;
    }
}