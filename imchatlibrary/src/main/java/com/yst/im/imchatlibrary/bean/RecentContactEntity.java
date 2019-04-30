package com.yst.im.imchatlibrary.bean;

import com.yst.im.imsdk.bean.ContactsEntity;

import java.util.List;

/**
 * 最近联系人
 *
 * @author qinchaoshuai
 * @version 1.0.1
 * @date 2018/4/16.
 */

public class RecentContactEntity {

    /**
     * code : 0
     * msg : 查询最近联系人成功
     * content : [{"id":790,"type":0,"event":3,"version":"a0ef515cff6041a5bd756a9207f827b0","senderId":"F36AE2459B5F4A0EB8C2AA505D76E0F7","accepteId":"62","password":null,"content":"1111111111111111111111111111111111111111111111","requestSourceSystem":"im","nickName":"这是一个很大的群","occureTime":1524539471000,"groupId":"62","portrait":"[\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180416/20180416165729751558374.jpeg\"]","msgStatus":null,"isRead":null,"count":0,"isStick":0,"isShield":1,"stickTime":1524470698000,"groupChat":false},{"id":16,"type":-1,"event":3,"version":null,"senderId":"5ABE847004384589B2B81C57604038CB","accepteId":"8","password":null,"content":"不上班撒娇","requestSourceSystem":"im","nickName":"嗨嗨  敌敌畏","occureTime":1523936739000,"groupId":"8","portrait":"[\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180416/20180416165729751558374.jpeg\",\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180423/20180423151217338479266.png\",\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180413/20180413102937033124470.jpeg\",\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180420/20180420171335146257580.png\"]","msgStatus":null,"isRead":0,"count":0,"isStick":0,"isShield":1,"stickTime":1524042248000,"groupChat":false},{"id":793,"type":0,"event":1,"version":"F36AE2459B5F4A0EB8C2AA505D76E0F71524539483135","senderId":"F36AE2459B5F4A0EB8C2AA505D76E0F7","accepteId":"F36AE2459B5F4A0EB8C2AA505D76E0F7","password":null,"content":"1111111111111111111111111111111111111111111111","requestSourceSystem":"im","nickName":"二二","occureTime":1524539471000,"groupId":null,"portrait":"[\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180416/20180416165729751558374.jpeg\"]","msgStatus":null,"isRead":null,"count":0,"isStick":1,"isShield":null,"stickTime":null,"groupChat":false},{"id":785,"type":0,"event":1,"version":"F36AE2459B5F4A0EB8C2AA505D76E0F71524538327818","senderId":"F36AE2459B5F4A0EB8C2AA505D76E0F7","accepteId":"553E047F64BC41CC82DD2DB82EC18AD8","password":null,"content":"凄凄切切","requestSourceSystem":"im","nickName":"Sas","occureTime":1524537786000,"groupId":null,"portrait":"[\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105155211045029.png\"]","msgStatus":null,"isRead":null,"count":0,"isStick":1,"isShield":null,"stickTime":null,"groupChat":false},{"id":776,"type":0,"event":1,"version":"d19a6717b71e48168f0ae0e37efea746","senderId":"F36AE2459B5F4A0EB8C2AA505D76E0F7","accepteId":"05CAFD292E30449D8E2A44D65C4885CB","password":null,"content":"个","requestSourceSystem":"im","nickName":"杨戬","occureTime":1524537679000,"groupId":null,"portrait":"[\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180416/20180416140150346990688.jpeg\"]","msgStatus":null,"isRead":null,"count":0,"isStick":1,"isShield":null,"stickTime":null,"groupChat":false},{"id":535,"type":2,"event":1,"version":"AACE855E-E0AF-4434-B4B6-DAB940D","senderId":"4845CCDC711F462083A0E19002E0AB99","accepteId":"F36AE2459B5F4A0EB8C2AA505D76E0F7","password":"","content":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180420/20180420165946306707222.png","requestSourceSystem":"im","nickName":"哈是谁和谁打电话还行","occureTime":1524214787000,"groupId":null,"portrait":"[\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180420/20180420171335146257580.png\"]","msgStatus":null,"isRead":null,"count":0,"isStick":1,"isShield":1,"stickTime":null,"groupChat":false}]
     */

    private int code;
    private String msg;
    private List<ContactsEntity> content;

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

    public List<ContactsEntity> getContent() {
        return content;
    }

    public void setContent(List<ContactsEntity> content) {
        this.content = content;
    }
}
