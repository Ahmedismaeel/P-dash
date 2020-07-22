package com.ahmed.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.ahmed.myapp.domain.Terminal} entity. This class is used
 * in {@link com.ahmed.myapp.web.rest.TerminalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /terminals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TerminalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter terminalId;

    private StringFilter merchantId;

    private StringFilter merchantName;

    private StringFilter marketName;

    private StringFilter phoneNo;

    private StringFilter sIMSerialNo;

    private StringFilter pOSSerialNo;

    private StringFilter location;

    private StringFilter user;

    public TerminalCriteria() {
    }

    public TerminalCriteria(TerminalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.terminalId = other.terminalId == null ? null : other.terminalId.copy();
        this.merchantId = other.merchantId == null ? null : other.merchantId.copy();
        this.merchantName = other.merchantName == null ? null : other.merchantName.copy();
        this.marketName = other.marketName == null ? null : other.marketName.copy();
        this.phoneNo = other.phoneNo == null ? null : other.phoneNo.copy();
        this.sIMSerialNo = other.sIMSerialNo == null ? null : other.sIMSerialNo.copy();
        this.pOSSerialNo = other.pOSSerialNo == null ? null : other.pOSSerialNo.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.user = other.user == null ? null : other.user.copy();
    }

    @Override
    public TerminalCriteria copy() {
        return new TerminalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(StringFilter terminalId) {
        this.terminalId = terminalId;
    }

    public StringFilter getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(StringFilter merchantId) {
        this.merchantId = merchantId;
    }

    public StringFilter getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(StringFilter merchantName) {
        this.merchantName = merchantName;
    }

    public StringFilter getMarketName() {
        return marketName;
    }

    public void setMarketName(StringFilter marketName) {
        this.marketName = marketName;
    }

    public StringFilter getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(StringFilter phoneNo) {
        this.phoneNo = phoneNo;
    }

    public StringFilter getsIMSerialNo() {
        return sIMSerialNo;
    }

    public void setsIMSerialNo(StringFilter sIMSerialNo) {
        this.sIMSerialNo = sIMSerialNo;
    }

    public StringFilter getpOSSerialNo() {
        return pOSSerialNo;
    }

    public void setpOSSerialNo(StringFilter pOSSerialNo) {
        this.pOSSerialNo = pOSSerialNo;
    }

    public StringFilter getLocation() {
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public StringFilter getUser() {
        return user;
    }

    public void setUser(StringFilter user) {
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TerminalCriteria that = (TerminalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(terminalId, that.terminalId) &&
            Objects.equals(merchantId, that.merchantId) &&
            Objects.equals(merchantName, that.merchantName) &&
            Objects.equals(marketName, that.marketName) &&
            Objects.equals(phoneNo, that.phoneNo) &&
            Objects.equals(sIMSerialNo, that.sIMSerialNo) &&
            Objects.equals(pOSSerialNo, that.pOSSerialNo) &&
            Objects.equals(location, that.location) &&
            Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        terminalId,
        merchantId,
        merchantName,
        marketName,
        phoneNo,
        sIMSerialNo,
        pOSSerialNo,
        location,
        user
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TerminalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (terminalId != null ? "terminalId=" + terminalId + ", " : "") +
                (merchantId != null ? "merchantId=" + merchantId + ", " : "") +
                (merchantName != null ? "merchantName=" + merchantName + ", " : "") +
                (marketName != null ? "marketName=" + marketName + ", " : "") +
                (phoneNo != null ? "phoneNo=" + phoneNo + ", " : "") +
                (sIMSerialNo != null ? "sIMSerialNo=" + sIMSerialNo + ", " : "") +
                (pOSSerialNo != null ? "pOSSerialNo=" + pOSSerialNo + ", " : "") +
                (location != null ? "location=" + location + ", " : "") +
                (user != null ? "user=" + user + ", " : "") +
            "}";
    }

}
