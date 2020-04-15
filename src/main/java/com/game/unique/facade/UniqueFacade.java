package com.game.unique.facade;

import com.game.unique.service.IUniqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈唯一性门面类〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
@Component
public class UniqueFacade {

    @Autowired
    private IUniqueService uniqueService;

    /**
     * 执行开服事件
     * 这里先暂时取消，因为事件是不确定顺序的，但是部分模块会依赖当前这个模块，所以这里先在抛出开服事件的地方做一个临时处理
     * @param event
     */
   /* @EventAnno
    public void doOpenServer(OpenServerSyncEvent event){
        uniqueService.doOpenServer();
    }*/

}