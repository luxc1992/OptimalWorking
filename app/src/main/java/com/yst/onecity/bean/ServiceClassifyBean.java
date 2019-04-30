package com.yst.onecity.bean;

import java.util.List;

/**
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/24
 */

public class ServiceClassifyBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"firstName":"装修122","secondList":[{"id":2,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"name":"空调安装1","isDelete":null,"parentId":null,"commissionRate":null,"sort":null},{"id":3,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"name":"上门打扫卫生33","isDelete":null,"parentId":null,"commissionRate":null,"sort":null},{"id":8,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"name":"gggg","isDelete":null,"parentId":null,"commissionRate":null,"sort":null}]}
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
         * firstName : 装修122
         * secondList : [{"id":2,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"name":"空调安装1","isDelete":null,"parentId":null,"commissionRate":null,"sort":null},{"id":3,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"name":"上门打扫卫生33","isDelete":null,"parentId":null,"commissionRate":null,"sort":null},{"id":8,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"name":"gggg","isDelete":null,"parentId":null,"commissionRate":null,"sort":null}]
         */

        private String firstName;
        private List<SecondListBean> secondList;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public List<SecondListBean> getSecondList() {
            return secondList;
        }

        public void setSecondList(List<SecondListBean> secondList) {
            this.secondList = secondList;
        }

        public static class SecondListBean {
            /**
             * id : 2
             * name : 空调安装1
             */

            private int classId;
            private String name;
            private boolean flag;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getClassId() {
                return classId;
            }

            public void setClassId(int classId) {
                this.classId = classId;
            }
        }
    }
}
