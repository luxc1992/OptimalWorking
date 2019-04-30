package com.yst.onecity.bean.issue;

/**
 * @author zhaiyanwu
 * @version v3.0.1
 * @date 2018/4/11.
 */
public class DiaryBean {


    /**
     * code : 1
     * msg : 发布成功，内容正在审核
     * id : 11491
     * createUser : null
     * createdTime : 2018-04-11 14:56:33
     * updateTime : 2018-04-11 14:56:33
     * createdIp : null
     * title : 2018 - 04 - 11
     * memberId : 474
     * productPlanId : 22369
     * isDeleted : 0
     * content : 请问
     * modelType : 1
     */

    private int code;
    private String msg;
    private int id;
    private Object createUser;
    private String createdTime;
    private String updateTime;
    private Object createdIp;
    private String title;
    private int memberId;
    private int productPlanId;
    private int isDeleted;
    private String content;
    private int modelType;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Object createUser) {
        this.createUser = createUser;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Object getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(Object createdIp) {
        this.createdIp = createdIp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getProductPlanId() {
        return productPlanId;
    }

    public void setProductPlanId(int productPlanId) {
        this.productPlanId = productPlanId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }
}
