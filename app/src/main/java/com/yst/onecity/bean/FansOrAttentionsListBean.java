package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 我的粉丝关注列表实体类
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/27.
 */

public class FansOrAttentionsListBean implements Serializable {

    /**
     * code : 1
     * msg : 成功
     * content : {"list":[{"img":"http://img.zcool.cn/community/01690955496f930000019ae92f3a4e.jpg@2o.jpg","nickName":"王乐","id":2,"status":1},{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180227/20180227215221149874830.png","nickName":"qqq","id":3,"status":0}],"count":2}
     */

    private int code;
    private String msg;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * list : [{"img":"http://img.zcool.cn/community/01690955496f930000019ae92f3a4e.jpg@2o.jpg","nickName":"王乐","id":2,"status":1},{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180227/20180227215221149874830.png","nickName":"qqq","id":3,"status":0}]
         * count : 2
         */

        private int count;
        private List<ListBean> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * img : http://img.zcool.cn/community/01690955496f930000019ae92f3a4e.jpg@2o.jpg
             * nickName : 王乐
             * id : 2
             * status : 1
             * isHunter : 1  0/null 不是猎头 1是猎头
             * isMerchant : 1  0/null 不是猎头 1是猎头
             */

            private String img;
            private String nickName;
            private int id;
            private int status;
            private int isHunter;
            private String createdTime;
            private int advisorId;
            private int isMerchant;

            public int getAdvisorId() {
                return advisorId;
            }

            public void setAdvisorId(int advisorId) {
                this.advisorId = advisorId;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public int getIsMerchant() {
                return isMerchant;
            }

            public void setIsMerchant(int isMerchant) {
                this.isMerchant = isMerchant;
            }

            public int getIsHunter() {
                return isHunter;
            }

            public void setIsHunter(int isHunter) {
                this.isHunter = isHunter;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
