package com.yst.onecity.bean;

import java.util.List;

/**
 * 我的设置  地区省查询
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/5
 */

public class ProvinceAdressBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"name":"北京市","id":110},{"name":"天津市","id":120},{"name":"河北省","id":130},{"name":"山西省","id":140},{"name":"内蒙古自治区","id":150},{"name":"辽宁省","id":210},{"name":"吉林省","id":220},{"name":"黑龙江省","id":230},{"name":"上海市","id":310},{"name":"江苏省","id":320},{"name":"浙江省","id":330},{"name":"安徽省","id":340},{"name":"福建省","id":350},{"name":"江西省","id":360},{"name":"山东省","id":370},{"name":"河南省","id":410},{"name":"湖北省","id":420},{"name":"湖南省","id":430},{"name":"广东省","id":440},{"name":"广西壮族自治区","id":450},{"name":"海南省","id":460},{"name":"重庆市","id":500},{"name":"四川省","id":510},{"name":"贵州省","id":520},{"name":"云南省","id":530},{"name":"西藏自治区","id":540},{"name":"陕西省","id":610},{"name":"甘肃省","id":620},{"name":"青海省","id":630},{"name":"宁夏回族自治区","id":640},{"name":"新疆维吾尔自治区","id":650}]
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
         * name : 北京市
         * id : 110
         */

        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
