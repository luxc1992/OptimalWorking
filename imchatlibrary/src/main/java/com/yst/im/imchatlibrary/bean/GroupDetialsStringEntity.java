package com.yst.im.imchatlibrary.bean;

/**
 * 群聊详情实体类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/14.
 */
public class GroupDetialsStringEntity {

    /**
     * code : 0
     * msg : 查询成功
     * content : {"applyUser":[],"createTime":1523945399000,"groupName":"钱佳洁真好看钱佳洁真好看、你弟进攻哦、狗神","groupNumberByCurrent":4,"groups":[{"address":"辽宁 铁岭","id":25,"nickName":"狗神","phone":"13439223424","requestSourceSystem":"im","updateTime":1523869050000,"userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180416/20180416165729751558374.jpeg","userId":"F36AE2459B5F4A0EB8C2AA505D76E0F7","userType":"0"},{"address":"北京 崇文","id":38,"nickName":"钱佳洁真好看","phone":"17671234260","requestSourceSystem":"im","updateTime":1523586579000,"userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180413/20180413102937033124470.jpeg","userId":"5E69DF51A79F442CAED48BFF411FDABA","userType":"0"},{"address":"北京 东城","id":43,"nickName":"你弟进攻哦","phone":"13021217168","requestSourceSystem":"im","updateTime":1523863802000,"userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180416/20180416100159447083392.png","userId":"4845CCDC711F462083A0E19002E0AB99","userType":"0"}],"id":23,"imageUrl":"[\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180413/20180413102937033124470.jpeg\",\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180413/20180413102937033124470.jpeg\",\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180416/20180416100159447083392.png\",\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180416/20180416165729751558374.jpeg\"]","requestSourceSystem":"im","updateTime":1523945399000,"userId":38}
     */

    private int code;
    private String msg;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
