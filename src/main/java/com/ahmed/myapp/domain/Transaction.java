package com.ahmed.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "terminal_id")
    private String terminalId;

    @Column(name = "tran_date_time")
    private String tranDateTime;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private String time;

    @Column(name = "tran_amount")
    private Double tranAmount;

    @Column(name = "tran_fee")
    private Double tranFee;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "response_status")
    private String responseStatus;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "user")
    private String user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public Transaction clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public Transaction terminalId(String terminalId) {
        this.terminalId = terminalId;
        return this;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTranDateTime() {
        return tranDateTime;
    }

    public Transaction tranDateTime(String tranDateTime) {
        this.tranDateTime = tranDateTime;
        return this;
    }

    public void setTranDateTime(String tranDateTime) {
        this.tranDateTime = tranDateTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public Transaction date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public Transaction time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTranAmount() {
        return tranAmount;
    }

    public Transaction tranAmount(Double tranAmount) {
        this.tranAmount = tranAmount;
        return this;
    }

    public void setTranAmount(Double tranAmount) {
        this.tranAmount = tranAmount;
    }

    public Double getTranFee() {
        return tranFee;
    }

    public Transaction tranFee(Double tranFee) {
        this.tranFee = tranFee;
        return this;
    }

    public void setTranFee(Double tranFee) {
        this.tranFee = tranFee;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public Transaction referenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public Transaction responseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Transaction serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUser() {
        return user;
    }

    public Transaction user(String user) {
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
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", clientId='" + getClientId() + "'" +
            ", terminalId='" + getTerminalId() + "'" +
            ", tranDateTime='" + getTranDateTime() + "'" +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            ", tranAmount=" + getTranAmount() +
            ", tranFee=" + getTranFee() +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", responseStatus='" + getResponseStatus() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
}
