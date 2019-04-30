package com.yst.onecity.view.viewpagerindicator.titleview.badge;

/**
 * 角标的定位规则
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */
public class BadgeRule {
    private BadgeAnchor mAnchor;
    private int mOffset;

    public BadgeRule(BadgeAnchor anchor, int offset) {
        mAnchor = anchor;
        mOffset = offset;
    }

    public BadgeAnchor getAnchor() {
        return mAnchor;
    }

    public void setAnchor(BadgeAnchor anchor) {
        mAnchor = anchor;
    }

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }
}
