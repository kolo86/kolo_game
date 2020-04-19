package com.game.container.model;

import com.game.container.AbstractContainer;
import com.game.skill.resource.SkillResource;
import com.game.skill.service.SkillManager;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 〈一句话功能简述〉<br>
 * 〈生命容器〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
@Data
public class LifeContainer extends AbstractContainer {

    /** 当前血量 */
    private AtomicLong currentHp = new AtomicLong(0);
    /** 当前蓝量 */
    private AtomicLong currentMp = new AtomicLong(0);
    /** 最大血量 */
    private long maxHp = 0;
    /** 最大蓝量 */
    private long maxMp = 0;

    public static LifeContainer valueOf(long hp, long mp){
        LifeContainer lifeContainer = new LifeContainer();
        lifeContainer.currentHp = new AtomicLong(hp);
        lifeContainer.currentMp = new AtomicLong(mp);

        lifeContainer.maxHp = hp;
        lifeContainer.maxMp = mp;
        return lifeContainer;
    }

    /**
     * 改变HP的值
     *
     */
    public long changeHp(long changeHp){
        long hp = currentHp.get();

        long realChangeNum = changeHp;
        if(changeHp < 0 && hp + changeHp < 0){
            realChangeNum = -hp;
        } else if(changeHp > 0 && hp + changeHp > maxHp){
            realChangeNum = maxHp - hp;
        }

        currentHp.addAndGet(realChangeNum);
        return realChangeNum;
    }

    /**
     * 改变MP的值
     *
     */
    public long changeMp(long changeMp){
        long mp = currentMp.get();

        long realChangeNum = changeMp;
        if(changeMp < 0 &&  mp + changeMp < 0){
            realChangeNum = -mp;
        } else if(changeMp > 0 && mp + changeMp > maxMp){
            realChangeNum = maxMp - mp;
        }
        currentMp.addAndGet(realChangeNum);
        return realChangeNum;
    }

    /**
     * 当前实体是否已经死亡
     *
     */
    public boolean isDead(){
        return this.currentHp.get() <= 0 ;
    }

    /**
     * 检查玩家够不够蓝去释放该技能
     *
     */
    public boolean isMpEnough(int skillId){
        SkillResource skillResource = SkillManager.getSkillResource(skillId);
        return this.currentMp.get() >= skillResource.getConsumeMp();
    }

}