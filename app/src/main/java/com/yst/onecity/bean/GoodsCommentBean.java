package com.yst.onecity.bean;

import java.util.List;

/**
 * 商品评价的bean
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/25
 */

public class GoodsCommentBean {

    /**
     * code : 1
     * msg : 服务商品评价列表成功！
     * content : {"countAll":6,"bigList":[{"returnContent":"呵呵呵呵呵呵呵呵","logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528366652000,"content":" ","productName":"hg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180607/20180607181724743395904.jpg,https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180607/20180607181724786842448.jpg","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":1,"readNum":4,"fabulousNum":0,"startNum":4,"nickname":"露娜猫","commentId":46,"createdTime":"2018-06-07 18:16:07"},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528450354000,"content":"vvvvvvvvvvv","productName":"hg","imgUrl":"","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":0,"readNum":0,"fabulousNum":0,"startNum":3,"nickname":"露娜猫","commentId":82,"createdTime":"2018-06-08 16:23:45"},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528450390000,"content":"果果","productName":"hg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180608/20180608173304541347376.jpg","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":1,"readNum":0,"fabulousNum":0,"startNum":4,"nickname":"露娜猫","commentId":85,"createdTime":"2018-06-08 16:23:14"},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528450457000,"content":"啊啦啦啦啦啦啦","productName":"hg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180608/20180608173400305039820.jpg","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":1,"readNum":0,"fabulousNum":0,"startNum":1,"nickname":"露娜猫","commentId":87,"createdTime":"2018-06-07 16:37:52"},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528450487000,"content":"古古怪怪古古怪怪古古怪怪","productName":"hg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180608/20180608173442066924837.jpg","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":1,"readNum":0,"fabulousNum":0,"startNum":5,"nickname":"露娜猫","commentId":88,"createdTime":"2018-06-07 16:33:39"},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528450521000,"content":"说的很对好的好的好的","productName":"hg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180608/20180608173517296563091.jpg","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":1,"readNum":0,"fabulousNum":0,"startNum":2,"nickname":"露娜猫","commentId":89,"createdTime":"2018-06-07 16:24:17"}],"countNo":5,"countLittle":5,"countBest":1}
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
         * countAll : 6
         * bigList : [{"returnContent":"呵呵呵呵呵呵呵呵","logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528366652000,"content":" ","productName":"hg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180607/20180607181724743395904.jpg,https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180607/20180607181724786842448.jpg","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":1,"readNum":4,"fabulousNum":0,"startNum":4,"nickname":"露娜猫","commentId":46,"createdTime":"2018-06-07 18:16:07"},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528450354000,"content":"vvvvvvvvvvv","productName":"hg","imgUrl":"","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":0,"readNum":0,"fabulousNum":0,"startNum":3,"nickname":"露娜猫","commentId":82,"createdTime":"2018-06-08 16:23:45"},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528450390000,"content":"果果","productName":"hg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180608/20180608173304541347376.jpg","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":1,"readNum":0,"fabulousNum":0,"startNum":4,"nickname":"露娜猫","commentId":85,"createdTime":"2018-06-08 16:23:14"},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528450457000,"content":"啊啦啦啦啦啦啦","productName":"hg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180608/20180608173400305039820.jpg","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":1,"readNum":0,"fabulousNum":0,"startNum":1,"nickname":"露娜猫","commentId":87,"createdTime":"2018-06-07 16:37:52"},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528450487000,"content":"古古怪怪古古怪怪古古怪怪","productName":"hg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180608/20180608173442066924837.jpg","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":1,"readNum":0,"fabulousNum":0,"startNum":5,"nickname":"露娜猫","commentId":88,"createdTime":"2018-06-07 16:33:39"},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png","commentTime":1528450521000,"content":"说的很对好的好的好的","productName":"hg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180608/20180608173517296563091.jpg","productSalePrice":0.01,"productSpecificationName":"白","commentMemberId":711,"isPicture":1,"readNum":0,"fabulousNum":0,"startNum":2,"nickname":"露娜猫","commentId":89,"createdTime":"2018-06-07 16:24:17"}]
         * countNo : 5
         * countLittle : 5
         * countBest : 1
         */

        private int countAll;
        private int countNo;
        private int countLittle;
        private int countBest;
        private List<BigListBean> bigList;

        public int getCountAll() {
            return countAll;
        }

        public void setCountAll(int countAll) {
            this.countAll = countAll;
        }

        public int getCountNo() {
            return countNo;
        }

        public void setCountNo(int countNo) {
            this.countNo = countNo;
        }

        public int getCountLittle() {
            return countLittle;
        }

        public void setCountLittle(int countLittle) {
            this.countLittle = countLittle;
        }

        public int getCountBest() {
            return countBest;
        }

        public void setCountBest(int countBest) {
            this.countBest = countBest;
        }

        public List<BigListBean> getBigList() {
            return bigList;
        }

        public void setBigList(List<BigListBean> bigList) {
            this.bigList = bigList;
        }

        public static class BigListBean {
            /**
             * returnContent : 呵呵呵呵呵呵呵呵
             * logoAttachmentAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180606/20180606113837823847015.png
             * commentTime : 1528366652000
             * content :
             * productName : hg
             * imgUrl : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180607/20180607181724743395904.jpg,https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180607/20180607181724786842448.jpg
             * productSalePrice : 0.01
             * productSpecificationName : 白
             * commentMemberId : 711
             * isPicture : 1
             * readNum : 4
             * fabulousNum : 0
             * startNum : 4
             * nickname : 露娜猫
             * commentId : 46
             * createdTime : 2018-06-07 18:16:07
             */

            private String returnContent;
            private String logoAttachmentAddress;
            private long commentTime;
            private String content;
            private String productName;
            private String imgUrl;
            private double productSalePrice;
            private String productSpecificationName;
            private int commentMemberId;
            private int isPicture;
            private int readNum;
            private int fabulousNum;
            private int startNum;
            private String nickname;
            private int commentId;
            private String createdTime;

            public String getReturnContent() {
                return returnContent;
            }

            public void setReturnContent(String returnContent) {
                this.returnContent = returnContent;
            }

            public String getLogoAttachmentAddress() {
                return logoAttachmentAddress;
            }

            public void setLogoAttachmentAddress(String logoAttachmentAddress) {
                this.logoAttachmentAddress = logoAttachmentAddress;
            }

            public long getCommentTime() {
                return commentTime;
            }

            public void setCommentTime(long commentTime) {
                this.commentTime = commentTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public double getProductSalePrice() {
                return productSalePrice;
            }

            public void setProductSalePrice(double productSalePrice) {
                this.productSalePrice = productSalePrice;
            }

            public String getProductSpecificationName() {
                return productSpecificationName;
            }

            public void setProductSpecificationName(String productSpecificationName) {
                this.productSpecificationName = productSpecificationName;
            }

            public int getCommentMemberId() {
                return commentMemberId;
            }

            public void setCommentMemberId(int commentMemberId) {
                this.commentMemberId = commentMemberId;
            }

            public int getIsPicture() {
                return isPicture;
            }

            public void setIsPicture(int isPicture) {
                this.isPicture = isPicture;
            }

            public int getReadNum() {
                return readNum;
            }

            public void setReadNum(int readNum) {
                this.readNum = readNum;
            }

            public int getFabulousNum() {
                return fabulousNum;
            }

            public void setFabulousNum(int fabulousNum) {
                this.fabulousNum = fabulousNum;
            }

            public int getStartNum() {
                return startNum;
            }

            public void setStartNum(int startNum) {
                this.startNum = startNum;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }
        }
    }
}
