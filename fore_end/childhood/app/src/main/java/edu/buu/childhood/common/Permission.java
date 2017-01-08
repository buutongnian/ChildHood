package edu.buu.childhood.common;

/**
 * Created by Administrator on 2016/10/11.
 */
public class Permission {
    private int permissonId;
    private String userName;
    private String permissionName;
    private String permissionStatus;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(String permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

    public int getPermissonId() {
        return permissonId;
    }

    public void setPermissonId(int permissonId) {
        this.permissonId = permissonId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
