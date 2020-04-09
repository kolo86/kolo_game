package com.game.container.model;

import com.game.container.AbstractContainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈冷却容器〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
public class CoolDownContainer extends AbstractContainer {

    /** Map< 技能ID， 技能下次可以使用的时间戳 > */
    private Map<Integer, Long> coolDownMap = new ConcurrentHashMap<>();

    public static CoolDownContainer valueOf(){
        return new CoolDownContainer();
    }

    /**
     * 检查该技能当前是否可以使用
     *
     * @param skillId
     * @return
     */
    public boolean checkSkill(int skillId){
        Long cool = coolDownMap.getOrDefault(skillId, 0L);
        return System.currentTimeMillis() >= cool;
    }

    /**
     * 缓存技能使用时间
     *
     * @param skillId 技能ID
     * @param nextUseTime  下次可使用时间
     */
    public void cacheSkillCD(int skillId, long nextUseTime){
        coolDownMap.put(skillId, nextUseTime);
    }

    /**
     * 获取下次可以使用技能的时间
     *
     * @param skill
     * @return
     */
    public long getUseTime(int skill){
        return coolDownMap.getOrDefault(skill, 0L);
    }
}