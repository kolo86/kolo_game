package com.game.drop.resource;

import com.frame.resource.AbstractResource;
import com.frame.resource.anno.Resource;
import com.frame.resource.constant.ResourceSeparator;
import com.game.drop.model.SelectItem;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈掉落资源〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
@Data
@Resource
public class DropResource extends AbstractResource {
    /** 掉落选择器ID */
    private int id;
    /** 掉落选择器类型 */
    private String type;

    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    private String item6;
    private String item7;
    private String item8;
    private String item9;
    private String item10;
    private String item11;
    private String item12;
    private String item13;
    private String item14;
    /** List< 掉落概率， 道具ID ></> */
    private transient List<SelectItem> itemList = new ArrayList<>();
    /** 配置的可掉落道具数量，如果修改了配置表的掉落数量，这里也需要修改 */
    private final int itemNum = 14;
    @Override
    public int getResourceId() {
        return id;
    }

    @Override
    public void init() {
        try {
            StringBuilder sb = new StringBuilder();
            Class<? extends DropResource> resourceClass = getClass();
            for (int i = 1; i <= itemNum; i++) {
                sb.append("item").append(i);
                Field field = resourceClass.getDeclaredField(sb.toString());
                field.setAccessible(true);
                String fieldObj = (String)field.get(this);

                if (!StringUtils.isEmpty(fieldObj) &&
                        !StringUtils.isEmpty(fieldObj.trim())) {
                    String itemStr = fieldObj.trim();
                    String[] strs = itemStr.split("\\s+");

                    String[] split = strs[0].split(ResourceSeparator.XIE_GANG);
                    Double prob = Double.parseDouble(split[0]) / Double.parseDouble(split[1]);
                    itemList.add(SelectItem.valueOf(prob, Integer.parseInt(strs[1])));
                }

                sb.delete(0, sb.length());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}