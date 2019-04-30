package com.yst.onecity.bean;

/**
 * 选择的省市区的bean
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/26
 */

public class AddressBean {
    private String province;
    private String city;
    private String district;
    private String proId;
    private String cityId;
    private String districtId;

    public AddressBean(String province, String city, String district) {
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public AddressBean() {
    }

    public AddressBean(String province, String city, String district, String proId, String cityId, String districtId) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.proId = proId;
        this.cityId = cityId;
        this.districtId = districtId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", proId='" + proId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", districtId='" + districtId + '\'' +
                '}';
    }
}
