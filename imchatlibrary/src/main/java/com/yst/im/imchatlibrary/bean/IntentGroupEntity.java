package com.yst.im.imchatlibrary.bean;

import com.yst.im.imchatlibrary.enumclass.IntentGroupDetail;

import java.io.Serializable;

/**
 * 跳转群聊实体
 *
 * @author wangj
 * @date 2018/4/19
 */

public class IntentGroupEntity implements Serializable{
    private IntentGroupDetail intentGroupDetail;
    private String groupId;
    private String groupNum;

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public IntentGroupDetail getIntentGroupDetail() {
        return intentGroupDetail;
    }

    public void setIntentGroupDetail(IntentGroupDetail intentGroupDetail) {
        this.intentGroupDetail = intentGroupDetail;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
