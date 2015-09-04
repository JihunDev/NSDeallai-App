package com.nsdeallai.ns.ns_deallai;

/**
 * Created by SangSang on 2015-08-26.
 */
public class Cart {
    int _id;
    int p_id;
    int m_id;
    String name;
    String image;
    int price;
    int quantity;
    int delivery;
    String options;

    public Cart() {
    }

    public Cart(int _id) {
        this._id = _id;
    }

    public Cart(int _id, int quantity, String options) {
        this._id = _id;
        this.quantity = quantity;
        this.options = options;
    }

    public Cart(int p_id, int m_id, String name, String image, int price, int quantity, int delivery, String options) {
        this.p_id = p_id;
        this.m_id = m_id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.delivery = delivery;
        this.options = options;
    }

    public Cart(int _id, int p_id, int m_id, String name, String image, int price, int quantity, int delivery, String options) {
        this._id = _id;
        this.p_id = p_id;
        this.m_id = m_id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.delivery = delivery;
        this.options = options;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "_id=" + _id +
                ", p_id=" + p_id +
                ", m_id=" + m_id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", delivery=" + delivery +
                ", options='" + options + '\'' +
                '}';
    }
}
