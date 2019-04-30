package com.yst.onecity.bean.issue;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaiyanwu
 * @version v3.0.1
 * @date 2018/3/14.
 */
public class CommodityTwoClassifyBean  implements Serializable{

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"classifyName":"可乐","parentId":45,"attachmentId":45,"id":47,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180313/20180313144637407822701.jpg","porductTypeId":null}]
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

    public  class ContentBean implements Serializable{
        /**
         * classifyName : 可乐
         * parentId : 45
         * attachmentId : 45
         * id : 47
         * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180313/20180313144637407822701.jpg
         * porductTypeId : null
         */

        private String classifyName;
        private int parentId;
        private int attachmentId;
        private int id;
        private String address;
        private Object porductTypeId;

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(int attachmentId) {
            this.attachmentId = attachmentId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getPorductTypeId() {
            return porductTypeId;
        }

        public void setPorductTypeId(Object porductTypeId) {
            this.porductTypeId = porductTypeId;
        }
    }
}
