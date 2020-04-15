package com.game.unique.service;

import com.game.persistence.service.IPersistenceService;
import com.game.unique.constant.UniqueConstant;
import com.game.unique.constant.UniqueType;
import com.game.unique.entity.UniqueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈唯一性服务类〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
@Component
public class UniqueServiceImpl implements IUniqueService {

    @Autowired
    private UniqueManager uniqueManager;
    @Autowired
    private IPersistenceService persistenceService;

    @Override
    public void doOpenServer() {
        List<Object> all = persistenceService.getAll(UniqueEntity.class);
        for(Object obj : all){
            UniqueEntity entity = (UniqueEntity)obj;
            uniqueManager.cacheUnique(entity);
        }
    }

    @Override
    public int getUniqueId(UniqueType type) {
        synchronized (type){
            int currentValue = uniqueManager.getCurrentValue(type);
            int newValue = currentValue + UniqueConstant.STOP;

            UniqueEntity entity = UniqueEntity.valueOf(type.getId(), newValue);

            if(currentValue == 0){
                persistenceService.save(entity);
            } else {
                persistenceService.update(entity);
            }
            uniqueManager.cacheUnique(entity);
            return newValue;
        }
    }
}