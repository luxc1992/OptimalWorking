package com.yst.onecity.bean;

import java.util.List;

/**
 * 猎豆实体类
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/2
 */

public class HuntBean {


    /**
     * code : 1
     * msg : 成功
     * content : {"score":2,"count":9,"list":[{"num":2,"createdTime":"2018-3-12 01:46:22","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-12 04:00:07","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-12 05:36:47","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-12 06:11:22","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-13 11:27:25","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-13 11:37:30","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-13 02:45:07","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-13 02:45:36","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-14 09:23:38","type":3,"memberId":167}]}
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
         * score : 2
         * count : 9
         * list : [{"num":2,"createdTime":"2018-3-12 01:46:22","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-12 04:00:07","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-12 05:36:47","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-12 06:11:22","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-13 11:27:25","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-13 11:37:30","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-13 02:45:07","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-13 02:45:36","type":3,"memberId":167},{"num":2,"createdTime":"2018-3-14 09:23:38","type":3,"memberId":167}]
         */

        private int score;
        private int count;
        private List<ListBean> list;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

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
             * num : 2
             * createdTime : 2018-3-12 01:46:22
             * type : 3
             * memberId : 167
             */

            private int num;
            private String createdTime;
            private int type;
            private int memberId;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }
        }
    }
}
