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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.ahmed.myapp.domain.Transaction} entity. This class is used
 * in {@link com.ahmed.myapp.web.rest.TransactionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter clientId;

    private StringFilter terminalId;

    private StringFilter tranDateTime;

    private LocalDateFilter date;

    private StringFilter time;

    private DoubleFilter tranAmount;

    private DoubleFilter tranFee;

    private StringFilter referenceNumber;

    private StringFilter responseStatus;

    private StringFilter serviceName;

    private StringFilter user;

    public TransactionCriteria() {
    }

    public TransactionCriteria(TransactionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.terminalId = other.terminalId == null ? null : other.terminalId.copy();
        this.tranDateTime = other.tranDateTime == null ? null : other.tranDateTime.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.time = other.time == null ? null : other.time.copy();
        this.tranAmount = other.tranAmount == null ? null : other.tranAmount.copy();
        this.tranFee = other.tranFee == null ? null : other.tranFee.copy();
        this.referenceNumber = other.referenceNumber == null ? null : other.referenceNumber.copy();
        this.responseStatus = other.responseStatus == null ? null : other.responseStatus.copy();
        this.serviceName = other.serviceName == null ? null : other.serviceName.copy();
        this.user = other.user == null ? null : other.user.copy();
    }

    @Override
    public TransactionCriteria copy() {
        return new TransactionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getClientId() {
        return clientId;
    }

    public void setClientId(StringFilter clientId) {
        this.clientId = clientId;
    }

    public StringFilter getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(StringFilter terminalId) {
        this.terminalId = terminalId;
    }

    public StringFilter getTranDateTime() {
        return tranDateTime;
    }

    public void setTranDateTime(StringFilter tranDateTime) {
        this.tranDateTime = tranDateTime;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public StringFilter getTime() {
        return time;
    }

    public void setTime(StringFilter time) {
        this.time = time;
    }

    public DoubleFilter getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(DoubleFilter tranAmount) {
        this.tranAmount = tranAmount;
    }

    public DoubleFilter getTranFee() {
        return tranFee;
    }

    public void setTranFee(DoubleFilter tranFee) {
        this.tranFee = tranFee;
    }

    public StringFilter getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(StringFilter referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public StringFilter getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(StringFilter responseStatus) {
        this.responseStatus = responseStatus;
    }

    public StringFilter getServiceName() {
        return serviceName;
    }

    public void setServiceName(StringFilter serviceName) {
        this.serviceName = serviceName;
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
        final TransactionCriteria that = (TransactionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(terminalId, that.terminalId) &&
            Objects.equals(tranDateTime, that.tranDateTime) &&
            Objects.equals(date, that.date) &&
            Objects.equals(time, that.time) &&
            Objects.equals(tranAmount, that.tranAmount) &&
            Objects.equals(tranFee, that.tranFee) &&
            Objects.equals(referenceNumber, that.referenceNumber) &&
            Objects.equals(responseStatus, that.responseStatus) &&
            Objects.equals(serviceName, that.serviceName) &&
            Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        clientId,
        terminalId,
        tranDateTime,
        date,
        time,
        tranAmount,
        tranFee,
        referenceNumber,
        responseStatus,
        serviceName,
        user
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (terminalId != null ? "terminalId=" + terminalId + ", " : "") +
                (tranDateTime != null ? "tranDateTime=" + tranDateTime + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (time != null ? "time=" + time + ", " : "") +
                (tranAmount != null ? "tranAmount=" + tranAmount + ", " : "") +
                (tranFee != null ? "tranFee=" + tranFee + ", " : "") +
                (referenceNumber != null ? "referenceNumber=" + referenceNumber + ", " : "") +
                (responseStatus != null ? "responseStatus=" + responseStatus + ", " : "") +
                (serviceName != null ? "serviceName=" + serviceName + ", " : "") +
                (user != null ? "user=" + user + ", " : "") +
            "}";
    }

}
