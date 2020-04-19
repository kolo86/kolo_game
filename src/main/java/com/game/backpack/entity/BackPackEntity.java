package com.game.backpack.entity;

import com.game.item.AbstractItem;
import com.game.item.resource.ItemResource;
import com.game.item.service.ItemManager;
import com.game.persistence.AbstractEntity;
import com.game.persistence.util.ParseUtils;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈背包实体〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
@Data
@Entity(name = "backpack")
@Table(appliesTo = "backpack", comment = "背包")
public class BackPackEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int comment '背包ID'", nullable = false)
    private int id;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号ID' ")
    private String accountId;

    @Column(columnDefinition = "mediumblob comment '背包数据'")
    private String packMapJson;

    /**
     * 背包缓存。Map< 道具唯一标识， 道具 ></>
     */
    private transient Map<Integer, AbstractItem> packMap = new HashMap<>();

    @Override
    public void convert() {
        packMapJson = ParseUtils.convertToJson(packMap);
    }

    @Override
    public void parse() {
        packMap = ParseUtils.parseToMap(packMapJson, Integer.class, AbstractItem.class);
    }

    public static BackPackEntity valueOf(String accountId){
        BackPackEntity backPack = new BackPackEntity();
        backPack.accountId = accountId;
        return backPack;
    }

    /**
     * 增加一个道具进背包
     *
     */
    public void addItem(AbstractItem item){
        ItemResource resource = ItemManager.getResource(item.getItemId());
        Collection<AbstractItem> items = packMap.values();
        Iterator<AbstractItem> iterator = items.iterator();

        // 背包中已有该类型的道具
        boolean state = true;
        while(iterator.hasNext() && state ){
            AbstractItem next = iterator.next();
            if(item.equals(next)){
                int maxAddition = resource.getMaxAddition();
                if(next.getNum() >= maxAddition){
                    // 当前这个已有道具已经达到了最大叠加数量
                    continue;
                } else if(next.getNum() + item.getNum() >= maxAddition){
                    // 当前已有道具虽然未到达最大叠加数量，但还不能放完该道具
                    int canPutNumber = maxAddition - next.getNum();
                    next.addItem(canPutNumber);
                    item.reduceItem(canPutNumber);
                } else {
                    // 当前已有道具可以放完该道具
                    next.addItem(item.getNum());
                    item.reduceItem(item.getNum());
                    state = false;
                }
            }
        }

        // 背包中没有该类型的道具，或许已有道具还不能放完该道具
        if(item.getNum() > 0 ){
            packMap.put(item.getObjectOnlyId(), item);
        }
    }

    /**
     * 根据物品唯一标识获取道具
     *
     */
    public AbstractItem getItem(int itemOnlyId){
        return packMap.get(itemOnlyId);
    }

    /**
     * 减少道具
     *
     */
    public boolean reduceItem(AbstractItem item, int num){
        AbstractItem abstractItem = packMap.get(item.getObjectOnlyId());
        if(num > abstractItem.getNum()){
            return false;
        } else {
            abstractItem.reduceItem(num);
            checkAndRemoveItem(abstractItem);
            return true;
        }
    }

    /**
     * 检查是否需要移除道具
     *
     */
    private void checkAndRemoveItem(AbstractItem item){
        AbstractItem abstractItem = packMap.get(item.getObjectOnlyId());
        if(abstractItem.getNum() <= 0){
            packMap.remove(item.getObjectOnlyId());
        }
    }

}