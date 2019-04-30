package com.yst.onecity.bean;

/**
 *(从我的进入)获取个人信息Bean
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/10
 */

public class PersonBean {


    /**
     * code : 1
     * msg : 获取成功
     * content : {"city_name":"长春市","logo_attachment_address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180309/20180309113313707555228.png","nickname":"李lcf","id":58,"content":"F","province_name":"${87654321-12345678}"}
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
         * city_name : 长春市
         * logo_attachment_address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180309/20180309113313707555228.png
         * nickname : 李lcf
         * id : 58
         * content : F
         * province_name : ${87654321-12345678}
         */

        private String city_name;
        private String logo_attachment_address;
        private String nickname;
        private int id;
        private String content;
        private String province_name;
        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getLogo_attachment_address() {
            return logo_attachment_address;
        }

        public void setLogo_attachment_address(String logo_attachment_address) {
            this.logo_attachment_address = logo_attachment_address;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }
    }
}
