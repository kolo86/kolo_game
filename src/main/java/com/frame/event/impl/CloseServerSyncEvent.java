package com.frame.event.impl;

import com.frame.event.IEvent;

/**
 * 关服同步事件
 *
 * @author kolo
 */
public class CloseServerSyncEvent implements IEvent {

    @Override
    public int getKey() {
        return 0;
    }

    public static CloseServerSyncEvent valueOf(){
        return new CloseServerSyncEvent();
    }

}
