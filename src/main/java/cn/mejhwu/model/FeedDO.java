package cn.mejhwu.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/27
 * Time:   19:45
 * Description:
 */
public class FeedDO {

    private int id;
    private int entityId;
    private int entityType;
    private int actorId;
    private int entityOwnerId;
    private Date createdDate;
    //JSON
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public void setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FeedDO{" +
                "id=" + id +
                ", entityId=" + entityId +
                ", entityType=" + entityType +
                ", actorId=" + actorId +
                ", entityOwnerId=" + entityOwnerId +
                ", createdDate=" + createdDate +
                ", data='" + data + '\'' +
                '}';
    }
}
