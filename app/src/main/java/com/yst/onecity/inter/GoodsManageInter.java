package com.yst.onecity.inter;

/**
 * 商品管理的操作接口
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/24
 */

public interface GoodsManageInter {
    /**
     * 下架
     *
     * @param position 商品index
     */
    void xiaJia(int position);

    /**
     * 编辑
     *
     * @param position 商品index
     */
    void edit(int position);

    /**
     * 删除
     *
     * @param position 商品index
     */
    void delete(int position);

    /**
     * 是否成功
     * @param isSuccess 操作成功
     */
    void isClick(boolean isSuccess);
}
