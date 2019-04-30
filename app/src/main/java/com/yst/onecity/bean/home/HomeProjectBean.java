package com.yst.onecity.bean.home;

import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 首页课题分类
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/24
 */
public class HomeProjectBean extends MsgBean {
    private String id;
    private String title;
    private String content;
    private String topicClassifyId;
    private String hunterId;
    private String readNum;
    private String priseNum;
    private String imageAddress;
    private String serviceTeamNum;
    private List<HomeProjectNewsBean> topicConsultationList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopicClassifyId() {
        return topicClassifyId;
    }

    public void setTopicClassifyId(String topicClassifyId) {
        this.topicClassifyId = topicClassifyId;
    }

    public String getHunterId() {
        return hunterId;
    }

    public void setHunterId(String hunterId) {
        this.hunterId = hunterId;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getPriseNum() {
        return priseNum;
    }

    public void setPriseNum(String priseNum) {
        this.priseNum = priseNum;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getServiceTeamNum() {
        return serviceTeamNum;
    }

    public void setServiceTeamNum(String serviceTeamNum) {
        this.serviceTeamNum = serviceTeamNum;
    }

    public List<HomeProjectNewsBean> getTopicConsultationList() {
        return topicConsultationList;
    }

    public void setTopicConsultationList(List<HomeProjectNewsBean> topicConsultationList) {
        this.topicConsultationList = topicConsultationList;
    }
}
