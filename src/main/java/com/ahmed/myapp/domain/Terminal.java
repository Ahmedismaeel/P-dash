package com.ahmed.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Terminal.
 */
@Entity
@Table(name = "terminal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Terminal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "terminal_id", nullable = false, unique = true)
    private String terminalId;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "market_name")
    private String marketName;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "s_im_serial_no")
    private String sIMSerialNo;

    @Column(name = "p_os_serial_no")
    private String pOSSerialNo;

    @Column(name = "location")
    private String location;

    @Column(name = "user")
    private String user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public Terminal terminalId(String terminalId) {
        this.terminalId = terminalId;
        return this;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public Terminal merchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public Terminal merchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMarketName() {
        return marketName;
    }

    public Terminal marketName(String marketName) {
        this.marketName = marketName;
        return this;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public Terminal phoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getsIMSerialNo() {
        return sIMSerialNo;
    }

    public Terminal sIMSerialNo(String sIMSerialNo) {
        this.sIMSerialNo = sIMSerialNo;
        return this;
    }

    public void setsIMSerialNo(String sIMSerialNo) {
        this.sIMSerialNo = sIMSerialNo;
    }

    public String getpOSSerialNo() {
        return pOSSerialNo;
    }

    public Terminal pOSSerialNo(String pOSSerialNo) {
        this.pOSSerialNo = pOSSerialNo;
        return this;
    }

    public void setpOSSerialNo(String pOSSerialNo) {
        this.pOSSerialNo = pOSSerialNo;
    }

    public String getLocation() {
        return location;
    }

    public Terminal location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser() {
        return user;
    }

    public Terminal user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Terminal)) {
            return false;
        }
        return id != null && id.equals(((Terminal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Terminal{" +
            "id=" + getId() +
            ", terminalId='" + getTerminalId() + "'" +
            ", merchantId='" + getMerchantId() + "'" +
            ", merchantName='" + getMerchantName() + "'" +
            ", marketName='" + getMarketName() + "'" +
            ", phoneNo='" + getPhoneNo() + "'" +
            ", sIMSerialNo='" + getsIMSerialNo() + "'" +
            ", pOSSerialNo='" + getpOSSerialNo() + "'" +
            ", location='" + getLocation() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
}
