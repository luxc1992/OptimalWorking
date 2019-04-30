package com.yst.onecity.bean;

/**
 * 商品规格实体类
 * @author zhaiyanwu
 * @version v1.0.1
 * @date 2018/3/3.
 */
public class CommoditySpecificationBean {

    private String specification;
    private String price;
    private String inventory;
    private String imagePath;

    public CommoditySpecificationBean(String specification, String price, String inventory, String imagePath) {
        this.specification = specification;
        this.price = price;
        this.inventory = inventory;
        this.imagePath = imagePath;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
