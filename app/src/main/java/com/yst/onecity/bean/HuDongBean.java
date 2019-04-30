package com.yst.onecity.bean;

import java.util.List;

/**
 * 消息通知互动列表实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/3/1
 */

public class HuDongBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"member_id":36,"created_time":"2018-03-03 00:27:51","operation_object_id":42,"head_img":"beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png","object_name":"暂无","nickname":"猴子","id":157,"type":1,"content":"评论:哈哈哈"},{"member_id":36,"created_time":"2018-03-02 23:15:46","operation_object_id":42,"head_img":"beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png","object_name":"暂无","nickname":"猴子","id":156,"type":1,"content":"评论:v滚滚滚"},{"member_id":36,"created_time":"2018-03-02 23:15:34","operation_object_id":42,"head_img":"beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png","object_name":"暂无","nickname":"猴子","id":155,"type":1,"content":"评论:vvvv"},{"member_id":36,"created_time":"2018-03-02 23:04:45","operation_object_id":40,"head_img":"beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png","object_name":"暂无","nickname":"猴子","id":153,"type":1,"content":"评论:对对对"},{"member_id":36,"created_time":"2018-03-02 23:04:42","operation_object_id":40,"head_img":"beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png","object_name":"暂无","nickname":"猴子","id":152,"type":1,"content":"评论:不不不"},{"member_id":36,"created_time":"2018-03-02 23:04:39","operation_object_id":40,"head_img":"beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png","object_name":"暂无","nickname":"猴子","id":151,"type":1,"content":"评论:哈哈哈哈"},{"member_id":36,"created_time":"2018-03-02 23:04:35","operation_object_id":40,"head_img":"beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png","object_name":"暂无","nickname":"猴子","id":150,"type":1,"content":"评论:做贼心虚"},{"member_id":36,"created_time":"2018-03-02 23:04:32","operation_object_id":40,"head_img":"beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png","object_name":"暂无","nickname":"猴子","id":149,"type":1,"content":"评论:发发发"},{"member_id":36,"created_time":"2018-03-02 23:04:30","operation_object_id":40,"head_img":"beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png","object_name":"暂无","nickname":"猴子","id":148,"type":1,"content":"评论:v个过分"},{"member_id":36,"created_time":"2018-03-02 23:04:27","operation_object_id":40,"head_img":"beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png","object_name":"暂无","nickname":"猴子","id":147,"type":1,"content":"评论:更丰富"}]
     */

    private int code;
    private String msg;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * member_id : 36
         * created_time : 2018-03-03 00:27:51
         * operation_object_id : 42
         * head_img : beijing.aliyuncs.com/datas/image/20180228/20180228085914701739482.png
         * object_name : 暂无
         * nickname : 猴子
         * id : 157
         * type : 1
         * content : 评论:哈哈哈
         */

        private int member_id;
        private String created_time;
        private int operation_object_id;
        private String head_img;
        private String object_name;
        private String nickname;
        private int id;
        private int type;
        private String content;

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public int getOperation_object_id() {
            return operation_object_id;
        }

        public void setOperation_object_id(int operation_object_id) {
            this.operation_object_id = operation_object_id;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getObject_name() {
            return object_name;
        }

        public void setObject_name(String object_name) {
            this.object_name = object_name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
