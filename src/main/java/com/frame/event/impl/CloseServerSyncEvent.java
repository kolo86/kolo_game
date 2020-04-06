package com.frame.event.impl;

import com.frame.event.IEvent;

/**
 * 关服同步事件
 *
 */
public class CloseServerSyncEvent implements IEvent {

    public static CloseServerSyncEvent valueOf(){
        return new CloseServerSyncEvent();
    }

}
