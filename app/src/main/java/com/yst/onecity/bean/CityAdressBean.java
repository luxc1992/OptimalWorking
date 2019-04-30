package com.yst.onecity.bean;

import java.util.List;

/**
 * 我的  设置城市Bean类
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/5
 */

public class CityAdressBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"name":"太原市","id":140100000000},{"name":"大同市","id":140200000000},{"name":"阳泉市","id":140300000000},{"name":"长治市","id":140400000000},{"name":"晋城市","id":140500000000},{"name":"朔州市","id":140600000000},{"name":"晋中市","id":140700000000},{"name":"运城市","id":140800000000},{"name":"忻州市","id":140900000000},{"name":"临汾市","id":141000000000},{"name":"吕梁市","id":141100000000}]
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
         * name : 太原市
         * id : 140100000000
         */

        private String name;
        private long id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
