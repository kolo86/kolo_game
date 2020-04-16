package com.game.common.resource;

import com.frame.resource.AbstractResource;
import com.frame.resource.anno.Resource;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈I18N提示语〉
 *
 * @author KOLO
 * @create 2020/4/16
 * @since 1.0.0
 */
@Data
@Resource
public class I18NResource extends AbstractResource {

    private int id;

    private String desc;

    @Override
    public int getResourceId() {
        return id;
    }
}