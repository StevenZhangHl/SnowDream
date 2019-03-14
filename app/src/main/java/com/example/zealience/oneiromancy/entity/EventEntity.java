package com.example.zealience.oneiromancy.entity;

/**
 * @user steven
 * @createDate 2019/3/13 13:18
 * @description 事件实体
 */
public class EventEntity {
    private String event;

    public EventEntity(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
