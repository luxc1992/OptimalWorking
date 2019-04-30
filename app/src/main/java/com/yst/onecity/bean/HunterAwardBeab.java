package com.yst.onecity.bean;

import java.util.List;

/**
 * 奖励明细
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/6
 */

public class HunterAwardBeab {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"makeHeadimgReward":{"title":"设置头像","score":20,"detail":"设置头像文案是啥"},"readedConReward":{"title":"文章被阅读奖励","score":2,"detail":"发布的文章（包括产品计划，项目计划，资讯）每被一个用户阅读奖励2猎豆。"},"readReward":{"title":"阅读奖励","score":2,"detail":"每阅读一篇文章（包括产品计划，项目计划，资讯），奖励2猎豆。通过此方式每天最多可获得10猎豆。"},"newPersonReward":{"title":"新人注册","score":100,"detail":"新人注册登录后可获得100猎豆奖励"},"shareConReward":{"title":"分享有奖","score":5,"detail":"每分享1篇文章（包括产品计划，项目计划，资讯）到第三方平台，可获得5猎豆，通过此方式每天最多获取21个猎豆。"},"apprenticeReadReward":{"title":"徒弟阅读文章奖励","score":2,"detail":"徒弟每阅读1篇文章（包括产品计划，项目计划，资讯），会奖励你2个猎豆，通过此方式每天最多可获取10猎豆。"},"getApprenticeReward":{"title":"收徒奖励","score":5,"detail":"每成功收1名徒弟奖励5个猎豆。"},"apprenticeShareReward":{"title":"徒弟文章被阅读奖励","score":3,"detail":"徒弟每分享1篇文章（包括产品计划，项目计划，资讯）到第三方平台，会奖励你3个猎豆，通过此方式每天最多可获取21猎豆。"},"sign":{"score":5,"detailList":[{"num":5,"day":1},{"num":10,"day":2},{"num":15,"day":3},{"num":20,"day":4},{"num":25,"day":5},{"num":30,"day":6},{"num":35,"day":7}],"detail":"首次签到起7天内，连续签到可获得相应猎豆奖励。若中断，则重新按照第一天登陆计算。","title":"每日签到"},"makeNickNameReward":{"title":"设置昵称","score":20,"detail":"设置昵称文案是啥"}}
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
         * makeHeadimgReward : {"title":"设置头像","score":20,"detail":"设置头像文案是啥"}
         * readedConReward : {"title":"文章被阅读奖励","score":2,"detail":"发布的文章（包括产品计划，项目计划，资讯）每被一个用户阅读奖励2猎豆。"}
         * readReward : {"title":"阅读奖励","score":2,"detail":"每阅读一篇文章（包括产品计划，项目计划，资讯），奖励2猎豆。通过此方式每天最多可获得10猎豆。"}
         * newPersonReward : {"title":"新人注册","score":100,"detail":"新人注册登录后可获得100猎豆奖励"}
         * shareConReward : {"title":"分享有奖","score":5,"detail":"每分享1篇文章（包括产品计划，项目计划，资讯）到第三方平台，可获得5猎豆，通过此方式每天最多获取21个猎豆。"}
         * apprenticeReadReward : {"title":"徒弟阅读文章奖励","score":2,"detail":"徒弟每阅读1篇文章（包括产品计划，项目计划，资讯），会奖励你2个猎豆，通过此方式每天最多可获取10猎豆。"}
         * getApprenticeReward : {"title":"收徒奖励","score":5,"detail":"每成功收1名徒弟奖励5个猎豆。"}
         * apprenticeShareReward : {"title":"徒弟文章被阅读奖励","score":3,"detail":"徒弟每分享1篇文章（包括产品计划，项目计划，资讯）到第三方平台，会奖励你3个猎豆，通过此方式每天最多可获取21猎豆。"}
         * sign : {"score":5,"detailList":[{"num":5,"day":1},{"num":10,"day":2},{"num":15,"day":3},{"num":20,"day":4},{"num":25,"day":5},{"num":30,"day":6},{"num":35,"day":7}],"detail":"首次签到起7天内，连续签到可获得相应猎豆奖励。若中断，则重新按照第一天登陆计算。","title":"每日签到"}
         * makeNickNameReward : {"title":"设置昵称","score":20,"detail":"设置昵称文案是啥"}
         */

        private MakeHeadimgRewardBean makeHeadimgReward;
        private ReadedConRewardBean readedConReward;
        private ReadRewardBean readReward;
        private NewPersonRewardBean newPersonReward;
        private ShareConRewardBean shareConReward;
        private ApprenticeReadRewardBean apprenticeReadReward;
        private GetApprenticeRewardBean getApprenticeReward;
        private ApprenticeShareRewardBean apprenticeShareReward;
        private SignBean sign;
        private MakeNickNameRewardBean makeNickNameReward;

        public MakeHeadimgRewardBean getMakeHeadimgReward() {
            return makeHeadimgReward;
        }

        public void setMakeHeadimgReward(MakeHeadimgRewardBean makeHeadimgReward) {
            this.makeHeadimgReward = makeHeadimgReward;
        }

        public ReadedConRewardBean getReadedConReward() {
            return readedConReward;
        }

        public void setReadedConReward(ReadedConRewardBean readedConReward) {
            this.readedConReward = readedConReward;
        }

        public ReadRewardBean getReadReward() {
            return readReward;
        }

        public void setReadReward(ReadRewardBean readReward) {
            this.readReward = readReward;
        }

        public NewPersonRewardBean getNewPersonReward() {
            return newPersonReward;
        }

        public void setNewPersonReward(NewPersonRewardBean newPersonReward) {
            this.newPersonReward = newPersonReward;
        }

        public ShareConRewardBean getShareConReward() {
            return shareConReward;
        }

        public void setShareConReward(ShareConRewardBean shareConReward) {
            this.shareConReward = shareConReward;
        }

        public ApprenticeReadRewardBean getApprenticeReadReward() {
            return apprenticeReadReward;
        }

        public void setApprenticeReadReward(ApprenticeReadRewardBean apprenticeReadReward) {
            this.apprenticeReadReward = apprenticeReadReward;
        }

        public GetApprenticeRewardBean getGetApprenticeReward() {
            return getApprenticeReward;
        }

        public void setGetApprenticeReward(GetApprenticeRewardBean getApprenticeReward) {
            this.getApprenticeReward = getApprenticeReward;
        }

        public ApprenticeShareRewardBean getApprenticeShareReward() {
            return apprenticeShareReward;
        }

        public void setApprenticeShareReward(ApprenticeShareRewardBean apprenticeShareReward) {
            this.apprenticeShareReward = apprenticeShareReward;
        }

        public SignBean getSign() {
            return sign;
        }

        public void setSign(SignBean sign) {
            this.sign = sign;
        }

        public MakeNickNameRewardBean getMakeNickNameReward() {
            return makeNickNameReward;
        }

        public void setMakeNickNameReward(MakeNickNameRewardBean makeNickNameReward) {
            this.makeNickNameReward = makeNickNameReward;
        }

        public static class MakeHeadimgRewardBean {
            /**
             * title : 设置头像
             * score : 20
             * detail : 设置头像文案是啥
             */

            private String title;
            private int score;
            private String detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class ReadedConRewardBean {
            /**
             * title : 文章被阅读奖励
             * score : 2
             * detail : 发布的文章（包括产品计划，项目计划，资讯）每被一个用户阅读奖励2猎豆。
             */

            private String title;
            private int score;
            private String detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class ReadRewardBean {
            /**
             * title : 阅读奖励
             * score : 2
             * detail : 每阅读一篇文章（包括产品计划，项目计划，资讯），奖励2猎豆。通过此方式每天最多可获得10猎豆。
             */

            private String title;
            private int score;
            private String detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class NewPersonRewardBean {
            /**
             * title : 新人注册
             * score : 100
             * detail : 新人注册登录后可获得100猎豆奖励
             */

            private String title;
            private int score;
            private String detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class ShareConRewardBean {
            /**
             * title : 分享有奖
             * score : 5
             * detail : 每分享1篇文章（包括产品计划，项目计划，资讯）到第三方平台，可获得5猎豆，通过此方式每天最多获取21个猎豆。
             */

            private String title;
            private int score;
            private String detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class ApprenticeReadRewardBean {
            /**
             * title : 徒弟阅读文章奖励
             * score : 2
             * detail : 徒弟每阅读1篇文章（包括产品计划，项目计划，资讯），会奖励你2个猎豆，通过此方式每天最多可获取10猎豆。
             */

            private String title;
            private int score;
            private String detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class GetApprenticeRewardBean {
            /**
             * title : 收徒奖励
             * score : 5
             * detail : 每成功收1名徒弟奖励5个猎豆。
             */

            private String title;
            private int score;
            private String detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class ApprenticeShareRewardBean {
            /**
             * title : 徒弟文章被阅读奖励
             * score : 3
             * detail : 徒弟每分享1篇文章（包括产品计划，项目计划，资讯）到第三方平台，会奖励你3个猎豆，通过此方式每天最多可获取21猎豆。
             */

            private String title;
            private int score;
            private String detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class SignBean {
            /**
             * score : 5
             * detailList : [{"num":5,"day":1},{"num":10,"day":2},{"num":15,"day":3},{"num":20,"day":4},{"num":25,"day":5},{"num":30,"day":6},{"num":35,"day":7}]
             * detail : 首次签到起7天内，连续签到可获得相应猎豆奖励。若中断，则重新按照第一天登陆计算。
             * title : 每日签到
             */

            private int score;
            private String detail;
            private String title;
            private List<DetailListBean> detailList;

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<DetailListBean> getDetailList() {
                return detailList;
            }

            public void setDetailList(List<DetailListBean> detailList) {
                this.detailList = detailList;
            }

            public static class DetailListBean {
                /**
                 * num : 5
                 * day : 1
                 */

                private int num;
                private int day;

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }
            }
        }

        public static class MakeNickNameRewardBean {
            /**
             * title : 设置昵称
             * score : 20
             * detail : 设置昵称文案是啥
             */

            private String title;
            private int score;
            private String detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }
    }
}
