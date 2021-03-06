package com.tuesdayma.middleware.sharding.dal.entity;

import java.io.Serializable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table my_order
 */
public class MyOrder implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column my_order.id
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column my_order.order_no
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    private String orderNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column my_order.price
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    private Integer price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column my_order.user_id
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table my_order
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column my_order.id
     *
     * @return the value of my_order.id
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column my_order.id
     *
     * @param id the value for my_order.id
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column my_order.order_no
     *
     * @return the value of my_order.order_no
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column my_order.order_no
     *
     * @param orderNo the value for my_order.order_no
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column my_order.price
     *
     * @return the value of my_order.price
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column my_order.price
     *
     * @param price the value for my_order.price
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column my_order.user_id
     *
     * @return the value of my_order.user_id
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column my_order.user_id
     *
     * @param userId the value for my_order.user_id
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table my_order
     *
     * @mbg.generated Tue Mar 09 17:03:49 CST 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", price=").append(price);
        sb.append(", userId=").append(userId);
        sb.append("]");
        return sb.toString();
    }
}