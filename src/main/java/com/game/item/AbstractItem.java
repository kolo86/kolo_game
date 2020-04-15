package com.game.item;

import com.game.account.entity.PlayerEntity;
import com.game.common.SpringContext;
import com.game.item.constant.ItemType;
import com.game.item.resource.ItemResource;
import com.game.item.service.ItemManager;
import com.game.unique.constant.UniqueType;
import com.game.unique.service.UniqueServiceImpl;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象道具类〉 ， 这个地方暂时没有使用抽象类，因为在反序列化的地方，会用到这个类的构造方法
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
@Data
public class AbstractItem {

    private static final Logger logger = LoggerFactory.getLogger(AbstractItem.class);

    /** 物品唯一ID */
    private int objectOnlyId;
    /** 物品表ID */
    private int itemId;
    /** 物品类型 */
    private int type;
    /** 物品数量 */
    private int num;

    public AbstractItem(){}

    public AbstractItem(int itemId, int num){
        UniqueServiceImpl uniqueService = (UniqueServiceImpl) SpringContext.getBean(UniqueServiceImpl.class);
        this.objectOnlyId = uniqueService.getUniqueId(UniqueType.ITEM);
        this.itemId = itemId;
        this.num = num;
        this.type = getItemType().getId();
    }

    public AbstractItem copy(){ return null ;}

    /**
     * 获取道具类型
     *
     * @return
     */
    public ItemType getItemType() {
        return null;
    }

    /**
     * 对同一个道具增加持有数量
     *
     * @param num
     */
    public void addItem(int num){
        this.num += num;
    }

    /**
     * 减少同一个道具的持有数量
     *
     * @param num
     */
    public void reduceItem(int num){
        if(num > this.num){
            logger.info("当前扣除的道具数量比持有的道具数量多！");
            return;
        }
        this.num -= num;
    }

    /**
     * 使用道具
     *
     */
    public void useItem(PlayerEntity player){};

    @Override
    public String toString() {
        ItemResource itemResource = ItemManager.getResource(this.getItemId());
        return "【道具ID:" + this.getObjectOnlyId() + ", 道具名称:" + itemResource.getName() + ",道具数量:" + this.getNum() + "】\t";
    }
}