package com.yst.onecity.bean.mine;

import java.util.List;

/**
 * 我的收藏-产品计划
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/03/02
 */
public class MyCollectProductPlan {

    /**
     * code : 1
     * msg : 成功
     * content : [{"img":[{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141032586150518.png","video":null}],"address":"北京市","headImg":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321095245641010343.png","author":"爱心","description":"图图图图图图","updateTime":"2018-03-21 14:31:31","title":"图图图图图","type":1,"shareNum":0,"likeNum":0,"commentNum":1,"productVOList":[{"imageAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141100913010188.jpeg","address":"北京市","price":0.01,"minPrice":0.01,"name":"天天","description":"[{\"type\":\"0\",\"content\":\"哈哈哈\",\"sort\":\"0\"},{\"type\":\"1\",\"content\":\"https:\\/\\/yst-pjyc-server.oss-cn-beijing.aliyuncs.com\\/datas\\/image\\/20180321\\/20180321141112604191308.jpeg?width=750.000000&height=1332.000000\",\"sort\":\"1\"}]","id":2291,"maxPrice":0.01,"title":"拥有"}],"id":5112,"projectName":null,"status":0},{"img":[{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141308290649230.png","video":null}],"address":"北京市","headImg":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141226858513060.png","author":"男神^_^","description":"大熊","updateTime":"2018-03-21 14:59:38","title":"哆啦A梦","type":1,"shareNum":0,"likeNum":1,"commentNum":1,"productVOList":[],"daily":{"images":[{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321145052697845936.jpeg","video":null}],"headImg":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141226858513060.png","nickname":"男神^_^","createdTime":"2018.03.21","id":3437,"title":"2018-03-21","content":"计划日记内容是什么"},"id":5113,"projectName":null,"status":1}]
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
         * img : [{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141032586150518.png","video":null}]
         * address : 北京市
         * headImg : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321095245641010343.png
         * author : 爱心
         * description : 图图图图图图
         * updateTime : 2018-03-21 14:31:31
         * title : 图图图图图
         * type : 1
         * shareNum : 0
         * likeNum : 0
         * commentNum : 1
         * productVOList : [{"imageAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141100913010188.jpeg","address":"北京市","price":0.01,"minPrice":0.01,"name":"天天","description":"[{\"type\":\"0\",\"content\":\"哈哈哈\",\"sort\":\"0\"},{\"type\":\"1\",\"content\":\"https:\\/\\/yst-pjyc-server.oss-cn-beijing.aliyuncs.com\\/datas\\/image\\/20180321\\/20180321141112604191308.jpeg?width=750.000000&height=1332.000000\",\"sort\":\"1\"}]","id":2291,"maxPrice":0.01,"title":"拥有"}]
         * id : 5112
         * projectName : null
         * status : 0
         * daily : {"images":[{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321145052697845936.jpeg","video":null}],"headImg":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141226858513060.png","nickname":"男神^_^","createdTime":"2018.03.21","id":3437,"title":"2018-03-21","content":"计划日记内容是什么"}
         */

        private String address;
        private String headImg;
        private String author;
        private String description;
        private String updateTime;
        private String title;
        private int type;
        private int shareNum;
        private int likeNum;
        private int commentNum;
        private int id;
        private Object projectName;
        private int status;
        private DailyBean daily;
        private List<ImgBean> img;
        private List<ProductVOListBean> productVOList;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getProjectName() {
            return projectName;
        }

        public void setProjectName(Object projectName) {
            this.projectName = projectName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public DailyBean getDaily() {
            return daily;
        }

        public void setDaily(DailyBean daily) {
            this.daily = daily;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public List<ProductVOListBean> getProductVOList() {
            return productVOList;
        }

        public void setProductVOList(List<ProductVOListBean> productVOList) {
            this.productVOList = productVOList;
        }

        public static class DailyBean {
            /**
             * images : [{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321145052697845936.jpeg","video":null}]
             * headImg : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141226858513060.png
             * nickname : 男神^_^
             * createdTime : 2018.03.21
             * id : 3437
             * title : 2018-03-21
             * content : 计划日记内容是什么
             */

            private String headImg;
            private String nickname;
            private String createdTime;
            private int id;
            private String title;
            private String content;
            private List<ImagesBean> images;

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            public static class ImagesBean {
                /**
                 * img : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321145052697845936.jpeg
                 * video : null
                 */

                private String img;
                private Object video;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public Object getVideo() {
                    return video;
                }

                public void setVideo(Object video) {
                    this.video = video;
                }
            }
        }

        public static class ImgBean {
            /**
             * img : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141032586150518.png
             * video : null
             */

            private String img;
            private String video;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }
        }

        public static class ProductVOListBean {
            /**
             * imageAttachmentAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180321/20180321141100913010188.jpeg
             * address : 北京市
             * price : 0.01
             * minPrice : 0.01
             * name : 天天
             * description : [{"type":"0","content":"哈哈哈","sort":"0"},{"type":"1","content":"https:\/\/yst-pjyc-server.oss-cn-beijing.aliyuncs.com\/datas\/image\/20180321\/20180321141112604191308.jpeg?width=750.000000&height=1332.000000","sort":"1"}]
             * id : 2291
             * maxPrice : 0.01
             * title : 拥有
             */

            private String imageAttachmentAddress;
            private String address;
            private double price;
            private double minPrice;
            private String name;
            private String description;
            private int id;
            private double maxPrice;
            private String title;

            public String getImageAttachmentAddress() {
                return imageAttachmentAddress;
            }

            public void setImageAttachmentAddress(String imageAttachmentAddress) {
                this.imageAttachmentAddress = imageAttachmentAddress;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(double minPrice) {
                this.minPrice = minPrice;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public double getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(double maxPrice) {
                this.maxPrice = maxPrice;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
