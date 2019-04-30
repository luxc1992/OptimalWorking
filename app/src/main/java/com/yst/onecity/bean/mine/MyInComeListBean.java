package com.yst.onecity.bean.mine;

import java.util.List;

/**
 * 我的收入实体类
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/3/1
 */

public class MyInComeListBean {

    /**
     * code : 1
     * msg : 获取成功
     * content : {"startIndex":0,"money":20,"recordType":0,"endIndex":10,"list":[{"money":10,"createTime":1523344660000,"description":"提现","id":1149,"type":0},{"money":50000,"createTime":1523344660000,"description":"提现手续费","id":1149,"type":0},{"money":10,"createTime":1523344145000,"description":"提现","id":1148,"type":0},{"money":50000,"createTime":1523344145000,"description":"提现手续费","id":1148,"type":0},{"money":10,"createTime":1523339665000,"description":"提现","id":1146,"type":0},{"money":50000,"createTime":1523339665000,"description":"提现手续费","id":1146,"type":0}],"memberId":386}
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
         * startIndex : 0
         * money : 20.0
         * recordType : 0
         * endIndex : 10
         * list : [{"money":10,"createTime":1523344660000,"description":"提现","id":1149,"type":0},{"money":50000,"createTime":1523344660000,"description":"提现手续费","id":1149,"type":0},{"money":10,"createTime":1523344145000,"description":"提现","id":1148,"type":0},{"money":50000,"createTime":1523344145000,"description":"提现手续费","id":1148,"type":0},{"money":10,"createTime":1523339665000,"description":"提现","id":1146,"type":0},{"money":50000,"createTime":1523339665000,"description":"提现手续费","id":1146,"type":0}]
         * memberId : 386
         */

        private int startIndex;
        private double money;
        private int recordType;
        private int endIndex;
        private int memberId;
        private List<ListBean> list;

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getRecordType() {
            return recordType;
        }

        public void setRecordType(int recordType) {
            this.recordType = recordType;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * money : 10.0
             * createTime : 1523344660000
             * description : 提现
             * id : 1149
             * type : 0
             */

            private double money;
            private long createTime;
            private String description;
            private int id;
            private int type;

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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
        }
    }
}
