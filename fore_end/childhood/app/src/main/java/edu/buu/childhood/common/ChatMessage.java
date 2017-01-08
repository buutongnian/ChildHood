package edu.buu.childhood.common;

/**
 * Created by Administrator on 2016/9/3.
 */
public class ChatMessage {
    private int id;
    private int roomId;
    private String userName;
    private String fromName;
    private String messageContent;
    private int isRead;
    private int isRoomMessage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getIsRoomMessage() {
        return isRoomMessage;
    }

    public void setIsRoomMessage(int isRoomMessage) {
        this.isRoomMessage = isRoomMessage;
    }
}
