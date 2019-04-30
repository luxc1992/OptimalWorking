package com.yst.onecity.bean;

import java.util.List;

/**
 * 我的   设置地区Bean
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/5
 */

public class DistractAdressBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"name":"小店区","id":140105000000},{"name":"迎泽区","id":140106000000},{"name":"杏花岭区","id":140107000000},{"name":"尖草坪区","id":140108000000},{"name":"万柏林区","id":140109000000},{"name":"晋源区","id":140110000000},{"name":"清徐县","id":140121000000},{"name":"阳曲县","id":140122000000},{"name":"娄烦县","id":140123000000},{"name":"古交市","id":140181000000}]
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
         * name : 小店区
         * id : 140105000000
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
