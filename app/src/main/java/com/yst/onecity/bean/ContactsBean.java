package com.yst.onecity.bean;

/**
 * 联系人信息实体类
 * 
 * @author luxuchang
 * @date 2018/01/04
 * @version 1.0.1
 */
public class ContactsBean {

    private String contactName;
    private String phoneNumber;

    public String getContactName() {
	return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ContactsBean{" +
                "contactName='" + contactName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
