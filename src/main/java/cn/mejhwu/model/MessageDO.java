package cn.mejhwu.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/21
 * Time:   18:44
 * Description:
 */
public class MessageDO {
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private Date createdDate;
    private int hasRead;
    private String conversationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public void setConversationId() {
        if (fromId < toId) {
            this.conversationId = fromId + "_" + toId;
        } else {
            this.conversationId = toId + "_" + fromId;
        }
    }

    @Override
    public String toString() {
        return "MessageDO{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", hasRead=" + hasRead +
                ", conversationId=" + conversationId +
                '}';
    }
}
