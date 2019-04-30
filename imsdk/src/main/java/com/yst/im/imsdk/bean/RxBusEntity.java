package com.yst.im.imsdk.bean;

/**
 * rxbus发送提示
 *
 * @author Lierpeng
 * @date 2018/4/14
 * @version  1.0.0
 */
public class RxBusEntity {

    private String title;
    private String content;
    private String sureText;
    private String closeText;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public String getSureText() {
        return sureText;
    }

    public void setSureText(String sureText) {
        this.sureText = sureText;
    }

    public String getCloseText() {
        return closeText;
    }

    public void setCloseText(String closeText) {
        this.closeText = closeText;
    }

    @Override
    public String toString() {
        return "RxBusEntity{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sureText='" + sureText + '\'' +
                ", closeText='" + closeText + '\'' +
                ", code=" + code +
                '}';
    }
}
