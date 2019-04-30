package com.yst.onecity.bean;

import java.util.List;

/**
 * 我的  查询收货人列表
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/5
 */

public class ReapBean {


    /**
     * code : 1
     * msg : 查询用户列表成功
     * content : [{"created_time":"2018-03-07 14:16:45","city_name":"秦皇岛市","detail_address":"吧岁啊发生大V安抚v","user_name":"网页哦","mobile":"133****6716","id":160,"county_name":"北戴河区","is_default":0,"province_name":"河北省"},{"created_time":"2018-03-07 14:15:28","city_name":"秦皇岛市","detail_address":"大爱吃啥大V","user_name":"吓死","mobile":"133****5896","id":159,"county_name":"北戴河区","is_default":0,"province_name":"河北省"},{"created_time":"2018-03-07 14:12:09","city_name":"秦皇岛市","detail_address":"爱吃大V我把问问","user_name":"出生地词语","mobile":"133****6716","id":156,"county_name":"青龙满族自治县","is_default":0,"province_name":"河北省"},{"created_time":"2018-03-07 14:06:37","city_name":"秦皇岛市","detail_address":"是打大VADVSDV吧不舍得","user_name":"摔和","mobile":"133****3333","id":155,"county_name":"北戴河区","is_default":0,"province_name":"河北省"},{"created_time":"2018-03-06 17:05:16","city_name":"市辖区","detail_address":"qqqqqq","user_name":"李四","mobile":"132****0000","id":130,"county_name":"东城区","is_default":0,"province_name":"北京市"}]
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
         * created_time : 2018-03-07 14:16:45
         * city_name : 秦皇岛市
         * detail_address : 吧岁啊发生大V安抚v
         * user_name : 网页哦
         * mobile : 133****6716
         * id : 160
         * county_name : 北戴河区
         * is_default : 0
         * province_name : 河北省
         */

        private String created_time;
        private String city_name;
        private String detail_address;
        private String user_name;
        private String mobile;
        private int id;
        private String county_name;
        private int is_default;
        private String province_name;

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getDetail_address() {
            return detail_address;
        }

        public void setDetail_address(String detail_address) {
            this.detail_address = detail_address;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCounty_name() {
            return county_name;
        }

        public void setCounty_name(String county_name) {
            this.county_name = county_name;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }
    }
}
