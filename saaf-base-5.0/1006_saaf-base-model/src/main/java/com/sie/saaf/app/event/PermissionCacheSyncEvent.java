package com.sie.saaf.app.event;

import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 *  权限缓存更新事件
 *  实时触发，仅在service层中使用了公共保存、删除方法时使用
 */
public class PermissionCacheSyncEvent extends ApplicationEvent {

    /**
     * 菜单id集合
     */
    private Set<String> menuIds;
    /**
     * 职责id集合
     */
    private Set<String> respIds;
    /**
     * 角色id集合
     */
    private Set<String> roleIds;
    /**
     * 资源id集合
     */
    private Set<String> resouceIds;

    /**
     * 用户id集合
     */
    private Set<Integer> userIds;

    private String timeStamp;

    public PermissionCacheSyncEvent(Object source, Set<String> menuIds, Set<String> respIds, Set<String> roleIds, Set<String> resouceIds, Set<Integer> userIds) {
        super(source);
        this.menuIds = menuIds;
        this.respIds = respIds;
        this.roleIds = roleIds;
        this.resouceIds = resouceIds;
        this.userIds = userIds;
    }

    public PermissionCacheSyncEvent(Object source, Set<String> menuIds, Set<String> respIds, Set<String> roleIds, Set<String> resouceIds) {
        super(source);
        this.menuIds = menuIds;
        this.respIds = respIds;
        this.roleIds = roleIds;
        this.resouceIds = resouceIds;
    }

    public PermissionCacheSyncEvent(Object source, Set<String> roleIds, Set<Integer> userIds) {
        super(source);
        this.roleIds = roleIds;
        this.userIds = userIds;
    }

    public PermissionCacheSyncEvent(Object source, Set<Integer> userIds) {
        super(source);
        this.userIds = userIds;
    }



    public Set<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Set<String> menuIds) {
        this.menuIds = menuIds;
    }

    public Set<String> getRespIds() {
        return respIds;
    }

    public void setRespIds(Set<String> respIds) {
        this.respIds = respIds;
    }

    public Set<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
    }

    public Set<String> getResouceIds() {
        return resouceIds;
    }

    public void setResouceIds(Set<String> resouceIds) {
        this.resouceIds = resouceIds;
    }

    public Set<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
