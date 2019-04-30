package com.yst.onecity.bean;


import java.util.List;

/**
 * 签到
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/5/24
 */
public class SignInBean {

    /**
     * code : 1
     * msg : 签到成功!
     * content : {"continuitySign":1,"newScoreCount":10,"signScore":[{"scoreCount":10,"signCount":1}]}
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
         * continuitySign : 1
         * newScoreCount : 10
         * signScore : [{"scoreCount":10,"signCount":1}]
         */

        private int continuitySign;
        private int newScoreCount;
        private List<SignScoreBean> signScore;

        public int getContinuitySign() {
            return continuitySign;
        }

        public void setContinuitySign(int continuitySign) {
            this.continuitySign = continuitySign;
        }

        public int getNewScoreCount() {
            return newScoreCount;
        }

        public void setNewScoreCount(int newScoreCount) {
            this.newScoreCount = newScoreCount;
        }

        public List<SignScoreBean> getSignScore() {
            return signScore;
        }

        public void setSignScore(List<SignScoreBean> signScore) {
            this.signScore = signScore;
        }

        public static class SignScoreBean {
            /**
             * scoreCount : 10
             * signCount : 1
             */

            private int scoreCount;
            private int signCount;

            public int getScoreCount() {
                return scoreCount;
            }

            public void setScoreCount(int scoreCount) {
                this.scoreCount = scoreCount;
            }

            public int getSignCount() {
                return signCount;
            }

            public void setSignCount(int signCount) {
                this.signCount = signCount;
            }
        }
    }
}
