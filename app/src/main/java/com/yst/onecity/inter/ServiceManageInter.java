package com.yst.onecity.inter;

/**
 * 服务管理的操作接口
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/24
 */

public interface ServiceManageInter {
    /**
     * 下架
     *
     * @param strId 服务id
     *
     */
    void xiaJia(String strId);
    /**
     * 上架
     *
     * @param strId 商品index
     */
    void shangJia(String strId);

    /**
     * 编辑
     *
     * @param position 商品index
     */
    void edit(int position);

    /**
     * 删除
     *
     * @param strId 商品index
     */
    void delete(String strId);

    /**
     * 是否成功
     * @param isSuccess 操作成功
     */
    void isClick(boolean isSuccess);
}
