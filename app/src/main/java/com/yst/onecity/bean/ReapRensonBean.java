package com.yst.onecity.bean;

/**
 * 选择收货人信息回显
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/8
 */

public class ReapRensonBean {


    /**
     * code : 1
     * msg : 查询成功
     * content : {"member_id":1,"created_time":1519968998000,"district_area_id":110101000000,"code":"100000","detail_address":"北京市市辖区丰台区","user_name":"Zy","city_area_id":110100000000,"mobile":"18310197121","is_default":0,"update_time":1520302491000,"is_deleted":1,"cityName":"市辖区","id":36,"create_user":null,"pro_area_id":110,"provinceName":"北京市","created_ip":null,"countyName":"东城区"}
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
         * member_id : 1
         * created_time : 1519968998000
         * district_area_id : 110101000000
         * code : 100000
         * detail_address : 北京市市辖区丰台区
         * user_name : Zy
         * city_area_id : 110100000000
         * mobile : 18310197121
         * is_default : 0
         * update_time : 1520302491000
         * is_deleted : 1
         * cityName : 市辖区
         * id : 36
         * create_user : null
         * pro_area_id : 110
         * provinceName : 北京市
         * created_ip : null
         * countyName : 东城区
         */

        private int member_id;
        private long created_time;
        private long district_area_id;
        private String code;
        private String detail_address;
        private String user_name;
        private long city_area_id;
        private String mobile;
        private int is_default;
        private long update_time;
        private int is_deleted;
        private String cityName;
        private int id;
        private Object create_user;
        private int pro_area_id;
        private String provinceName;
        private Object created_ip;
        private String countyName;

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public long getCreated_time() {
            return created_time;
        }

        public void setCreated_time(long created_time) {
            this.created_time = created_time;
        }

        public long getDistrict_area_id() {
            return district_area_id;
        }

        public void setDistrict_area_id(long district_area_id) {
            this.district_area_id = district_area_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public long getCity_area_id() {
            return city_area_id;
        }

        public void setCity_area_id(long city_area_id) {
            this.city_area_id = city_area_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public int getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(int is_deleted) {
            this.is_deleted = is_deleted;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCreate_user() {
            return create_user;
        }

        public void setCreate_user(Object create_user) {
            this.create_user = create_user;
        }

        public int getPro_area_id() {
            return pro_area_id;
        }

        public void setPro_area_id(int pro_area_id) {
            this.pro_area_id = pro_area_id;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public Object getCreated_ip() {
            return created_ip;
        }

        public void setCreated_ip(Object created_ip) {
            this.created_ip = created_ip;
        }

        public String getCountyName() {
            return countyName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }
    }
}
