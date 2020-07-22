package com.ahmed.myapp.web.rest;

import com.ahmed.myapp.PdashApp;
import com.ahmed.myapp.domain.Transaction;
import com.ahmed.myapp.repository.TransactionRepository;
import com.ahmed.myapp.service.TransactionService;
import com.ahmed.myapp.service.dto.TransactionCriteria;
import com.ahmed.myapp.service.TransactionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@SpringBootTest(classes = PdashApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionResourceIT {

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TERMINAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_TERMINAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRAN_DATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TRAN_DATE_TIME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final Double DEFAULT_TRAN_AMOUNT = 1D;
    private static final Double UPDATED_TRAN_AMOUNT = 2D;
    private static final Double SMALLER_TRAN_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_TRAN_FEE = 1D;
    private static final Double UPDATED_TRAN_FEE = 2D;
    private static final Double SMALLER_TRAN_FEE = 1D - 1D;

    private static final String DEFAULT_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionQueryService transactionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .clientId(DEFAULT_CLIENT_ID)
            .terminalId(DEFAULT_TERMINAL_ID)
            .tranDateTime(DEFAULT_TRAN_DATE_TIME)
            .date(DEFAULT_DATE)
            .time(DEFAULT_TIME)
            .tranAmount(DEFAULT_TRAN_AMOUNT)
            .tranFee(DEFAULT_TRAN_FEE)
            .referenceNumber(DEFAULT_REFERENCE_NUMBER)
            .responseStatus(DEFAULT_RESPONSE_STATUS)
            .serviceName(DEFAULT_SERVICE_NAME)
            .user(DEFAULT_USER);
        return transaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .clientId(UPDATED_CLIENT_ID)
            .terminalId(UPDATED_TERMINAL_ID)
            .tranDateTime(UPDATED_TRAN_DATE_TIME)
            .date(UPDATED_DATE)
            .time(UPDATED_TIME)
            .tranAmount(UPDATED_TRAN_AMOUNT)
            .tranFee(UPDATED_TRAN_FEE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .responseStatus(UPDATED_RESPONSE_STATUS)
            .serviceName(UPDATED_SERVICE_NAME)
            .user(UPDATED_USER);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();
        // Create the Transaction
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testTransaction.getTerminalId()).isEqualTo(DEFAULT_TERMINAL_ID);
        assertThat(testTransaction.getTranDateTime()).isEqualTo(DEFAULT_TRAN_DATE_TIME);
        assertThat(testTransaction.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTransaction.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testTransaction.getTranAmount()).isEqualTo(DEFAULT_TRAN_AMOUNT);
        assertThat(testTransaction.getTranFee()).isEqualTo(DEFAULT_TRAN_FEE);
        assertThat(testTransaction.getReferenceNumber()).isEqualTo(DEFAULT_REFERENCE_NUMBER);
        assertThat(testTransaction.getResponseStatus()).isEqualTo(DEFAULT_RESPONSE_STATUS);
        assertThat(testTransaction.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testTransaction.getUser()).isEqualTo(DEFAULT_USER);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].terminalId").value(hasItem(DEFAULT_TERMINAL_ID)))
            .andExpect(jsonPath("$.[*].tranDateTime").value(hasItem(DEFAULT_TRAN_DATE_TIME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].tranAmount").value(hasItem(DEFAULT_TRAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].tranFee").value(hasItem(DEFAULT_TRAN_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].responseStatus").value(hasItem(DEFAULT_RESPONSE_STATUS)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)));
    }
    
    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.terminalId").value(DEFAULT_TERMINAL_ID))
            .andExpect(jsonPath("$.tranDateTime").value(DEFAULT_TRAN_DATE_TIME))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.tranAmount").value(DEFAULT_TRAN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.tranFee").value(DEFAULT_TRAN_FEE.doubleValue()))
            .andExpect(jsonPath("$.referenceNumber").value(DEFAULT_REFERENCE_NUMBER))
            .andExpect(jsonPath("$.responseStatus").value(DEFAULT_RESPONSE_STATUS))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER));
    }


    @Test
    @Transactional
    public void getTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        Long id = transaction.getId();

        defaultTransactionShouldBeFound("id.equals=" + id);
        defaultTransactionShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransactionsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where clientId equals to DEFAULT_CLIENT_ID
        defaultTransactionShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the transactionList where clientId equals to UPDATED_CLIENT_ID
        defaultTransactionShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where clientId not equals to DEFAULT_CLIENT_ID
        defaultTransactionShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the transactionList where clientId not equals to UPDATED_CLIENT_ID
        defaultTransactionShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultTransactionShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the transactionList where clientId equals to UPDATED_CLIENT_ID
        defaultTransactionShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where clientId is not null
        defaultTransactionShouldBeFound("clientId.specified=true");

        // Get all the transactionList where clientId is null
        defaultTransactionShouldNotBeFound("clientId.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByClientIdContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where clientId contains DEFAULT_CLIENT_ID
        defaultTransactionShouldBeFound("clientId.contains=" + DEFAULT_CLIENT_ID);

        // Get all the transactionList where clientId contains UPDATED_CLIENT_ID
        defaultTransactionShouldNotBeFound("clientId.contains=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByClientIdNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where clientId does not contain DEFAULT_CLIENT_ID
        defaultTransactionShouldNotBeFound("clientId.doesNotContain=" + DEFAULT_CLIENT_ID);

        // Get all the transactionList where clientId does not contain UPDATED_CLIENT_ID
        defaultTransactionShouldBeFound("clientId.doesNotContain=" + UPDATED_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllTransactionsByTerminalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where terminalId equals to DEFAULT_TERMINAL_ID
        defaultTransactionShouldBeFound("terminalId.equals=" + DEFAULT_TERMINAL_ID);

        // Get all the transactionList where terminalId equals to UPDATED_TERMINAL_ID
        defaultTransactionShouldNotBeFound("terminalId.equals=" + UPDATED_TERMINAL_ID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTerminalIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where terminalId not equals to DEFAULT_TERMINAL_ID
        defaultTransactionShouldNotBeFound("terminalId.notEquals=" + DEFAULT_TERMINAL_ID);

        // Get all the transactionList where terminalId not equals to UPDATED_TERMINAL_ID
        defaultTransactionShouldBeFound("terminalId.notEquals=" + UPDATED_TERMINAL_ID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTerminalIdIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where terminalId in DEFAULT_TERMINAL_ID or UPDATED_TERMINAL_ID
        defaultTransactionShouldBeFound("terminalId.in=" + DEFAULT_TERMINAL_ID + "," + UPDATED_TERMINAL_ID);

        // Get all the transactionList where terminalId equals to UPDATED_TERMINAL_ID
        defaultTransactionShouldNotBeFound("terminalId.in=" + UPDATED_TERMINAL_ID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTerminalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where terminalId is not null
        defaultTransactionShouldBeFound("terminalId.specified=true");

        // Get all the transactionList where terminalId is null
        defaultTransactionShouldNotBeFound("terminalId.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByTerminalIdContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where terminalId contains DEFAULT_TERMINAL_ID
        defaultTransactionShouldBeFound("terminalId.contains=" + DEFAULT_TERMINAL_ID);

        // Get all the transactionList where terminalId contains UPDATED_TERMINAL_ID
        defaultTransactionShouldNotBeFound("terminalId.contains=" + UPDATED_TERMINAL_ID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTerminalIdNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where terminalId does not contain DEFAULT_TERMINAL_ID
        defaultTransactionShouldNotBeFound("terminalId.doesNotContain=" + DEFAULT_TERMINAL_ID);

        // Get all the transactionList where terminalId does not contain UPDATED_TERMINAL_ID
        defaultTransactionShouldBeFound("terminalId.doesNotContain=" + UPDATED_TERMINAL_ID);
    }


    @Test
    @Transactional
    public void getAllTransactionsByTranDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranDateTime equals to DEFAULT_TRAN_DATE_TIME
        defaultTransactionShouldBeFound("tranDateTime.equals=" + DEFAULT_TRAN_DATE_TIME);

        // Get all the transactionList where tranDateTime equals to UPDATED_TRAN_DATE_TIME
        defaultTransactionShouldNotBeFound("tranDateTime.equals=" + UPDATED_TRAN_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranDateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranDateTime not equals to DEFAULT_TRAN_DATE_TIME
        defaultTransactionShouldNotBeFound("tranDateTime.notEquals=" + DEFAULT_TRAN_DATE_TIME);

        // Get all the transactionList where tranDateTime not equals to UPDATED_TRAN_DATE_TIME
        defaultTransactionShouldBeFound("tranDateTime.notEquals=" + UPDATED_TRAN_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranDateTime in DEFAULT_TRAN_DATE_TIME or UPDATED_TRAN_DATE_TIME
        defaultTransactionShouldBeFound("tranDateTime.in=" + DEFAULT_TRAN_DATE_TIME + "," + UPDATED_TRAN_DATE_TIME);

        // Get all the transactionList where tranDateTime equals to UPDATED_TRAN_DATE_TIME
        defaultTransactionShouldNotBeFound("tranDateTime.in=" + UPDATED_TRAN_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranDateTime is not null
        defaultTransactionShouldBeFound("tranDateTime.specified=true");

        // Get all the transactionList where tranDateTime is null
        defaultTransactionShouldNotBeFound("tranDateTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByTranDateTimeContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranDateTime contains DEFAULT_TRAN_DATE_TIME
        defaultTransactionShouldBeFound("tranDateTime.contains=" + DEFAULT_TRAN_DATE_TIME);

        // Get all the transactionList where tranDateTime contains UPDATED_TRAN_DATE_TIME
        defaultTransactionShouldNotBeFound("tranDateTime.contains=" + UPDATED_TRAN_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranDateTimeNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranDateTime does not contain DEFAULT_TRAN_DATE_TIME
        defaultTransactionShouldNotBeFound("tranDateTime.doesNotContain=" + DEFAULT_TRAN_DATE_TIME);

        // Get all the transactionList where tranDateTime does not contain UPDATED_TRAN_DATE_TIME
        defaultTransactionShouldBeFound("tranDateTime.doesNotContain=" + UPDATED_TRAN_DATE_TIME);
    }


    @Test
    @Transactional
    public void getAllTransactionsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where date equals to DEFAULT_DATE
        defaultTransactionShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the transactionList where date equals to UPDATED_DATE
        defaultTransactionShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where date not equals to DEFAULT_DATE
        defaultTransactionShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the transactionList where date not equals to UPDATED_DATE
        defaultTransactionShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where date in DEFAULT_DATE or UPDATED_DATE
        defaultTransactionShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the transactionList where date equals to UPDATED_DATE
        defaultTransactionShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where date is not null
        defaultTransactionShouldBeFound("date.specified=true");

        // Get all the transactionList where date is null
        defaultTransactionShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where date is greater than or equal to DEFAULT_DATE
        defaultTransactionShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the transactionList where date is greater than or equal to UPDATED_DATE
        defaultTransactionShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where date is less than or equal to DEFAULT_DATE
        defaultTransactionShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the transactionList where date is less than or equal to SMALLER_DATE
        defaultTransactionShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where date is less than DEFAULT_DATE
        defaultTransactionShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the transactionList where date is less than UPDATED_DATE
        defaultTransactionShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where date is greater than DEFAULT_DATE
        defaultTransactionShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the transactionList where date is greater than SMALLER_DATE
        defaultTransactionShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllTransactionsByTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where time equals to DEFAULT_TIME
        defaultTransactionShouldBeFound("time.equals=" + DEFAULT_TIME);

        // Get all the transactionList where time equals to UPDATED_TIME
        defaultTransactionShouldNotBeFound("time.equals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where time not equals to DEFAULT_TIME
        defaultTransactionShouldNotBeFound("time.notEquals=" + DEFAULT_TIME);

        // Get all the transactionList where time not equals to UPDATED_TIME
        defaultTransactionShouldBeFound("time.notEquals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTimeIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where time in DEFAULT_TIME or UPDATED_TIME
        defaultTransactionShouldBeFound("time.in=" + DEFAULT_TIME + "," + UPDATED_TIME);

        // Get all the transactionList where time equals to UPDATED_TIME
        defaultTransactionShouldNotBeFound("time.in=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where time is not null
        defaultTransactionShouldBeFound("time.specified=true");

        // Get all the transactionList where time is null
        defaultTransactionShouldNotBeFound("time.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByTimeContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where time contains DEFAULT_TIME
        defaultTransactionShouldBeFound("time.contains=" + DEFAULT_TIME);

        // Get all the transactionList where time contains UPDATED_TIME
        defaultTransactionShouldNotBeFound("time.contains=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTimeNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where time does not contain DEFAULT_TIME
        defaultTransactionShouldNotBeFound("time.doesNotContain=" + DEFAULT_TIME);

        // Get all the transactionList where time does not contain UPDATED_TIME
        defaultTransactionShouldBeFound("time.doesNotContain=" + UPDATED_TIME);
    }


    @Test
    @Transactional
    public void getAllTransactionsByTranAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranAmount equals to DEFAULT_TRAN_AMOUNT
        defaultTransactionShouldBeFound("tranAmount.equals=" + DEFAULT_TRAN_AMOUNT);

        // Get all the transactionList where tranAmount equals to UPDATED_TRAN_AMOUNT
        defaultTransactionShouldNotBeFound("tranAmount.equals=" + UPDATED_TRAN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranAmount not equals to DEFAULT_TRAN_AMOUNT
        defaultTransactionShouldNotBeFound("tranAmount.notEquals=" + DEFAULT_TRAN_AMOUNT);

        // Get all the transactionList where tranAmount not equals to UPDATED_TRAN_AMOUNT
        defaultTransactionShouldBeFound("tranAmount.notEquals=" + UPDATED_TRAN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranAmountIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranAmount in DEFAULT_TRAN_AMOUNT or UPDATED_TRAN_AMOUNT
        defaultTransactionShouldBeFound("tranAmount.in=" + DEFAULT_TRAN_AMOUNT + "," + UPDATED_TRAN_AMOUNT);

        // Get all the transactionList where tranAmount equals to UPDATED_TRAN_AMOUNT
        defaultTransactionShouldNotBeFound("tranAmount.in=" + UPDATED_TRAN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranAmount is not null
        defaultTransactionShouldBeFound("tranAmount.specified=true");

        // Get all the transactionList where tranAmount is null
        defaultTransactionShouldNotBeFound("tranAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranAmount is greater than or equal to DEFAULT_TRAN_AMOUNT
        defaultTransactionShouldBeFound("tranAmount.greaterThanOrEqual=" + DEFAULT_TRAN_AMOUNT);

        // Get all the transactionList where tranAmount is greater than or equal to UPDATED_TRAN_AMOUNT
        defaultTransactionShouldNotBeFound("tranAmount.greaterThanOrEqual=" + UPDATED_TRAN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranAmount is less than or equal to DEFAULT_TRAN_AMOUNT
        defaultTransactionShouldBeFound("tranAmount.lessThanOrEqual=" + DEFAULT_TRAN_AMOUNT);

        // Get all the transactionList where tranAmount is less than or equal to SMALLER_TRAN_AMOUNT
        defaultTransactionShouldNotBeFound("tranAmount.lessThanOrEqual=" + SMALLER_TRAN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranAmount is less than DEFAULT_TRAN_AMOUNT
        defaultTransactionShouldNotBeFound("tranAmount.lessThan=" + DEFAULT_TRAN_AMOUNT);

        // Get all the transactionList where tranAmount is less than UPDATED_TRAN_AMOUNT
        defaultTransactionShouldBeFound("tranAmount.lessThan=" + UPDATED_TRAN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranAmount is greater than DEFAULT_TRAN_AMOUNT
        defaultTransactionShouldNotBeFound("tranAmount.greaterThan=" + DEFAULT_TRAN_AMOUNT);

        // Get all the transactionList where tranAmount is greater than SMALLER_TRAN_AMOUNT
        defaultTransactionShouldBeFound("tranAmount.greaterThan=" + SMALLER_TRAN_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllTransactionsByTranFeeIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranFee equals to DEFAULT_TRAN_FEE
        defaultTransactionShouldBeFound("tranFee.equals=" + DEFAULT_TRAN_FEE);

        // Get all the transactionList where tranFee equals to UPDATED_TRAN_FEE
        defaultTransactionShouldNotBeFound("tranFee.equals=" + UPDATED_TRAN_FEE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranFeeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranFee not equals to DEFAULT_TRAN_FEE
        defaultTransactionShouldNotBeFound("tranFee.notEquals=" + DEFAULT_TRAN_FEE);

        // Get all the transactionList where tranFee not equals to UPDATED_TRAN_FEE
        defaultTransactionShouldBeFound("tranFee.notEquals=" + UPDATED_TRAN_FEE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranFeeIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranFee in DEFAULT_TRAN_FEE or UPDATED_TRAN_FEE
        defaultTransactionShouldBeFound("tranFee.in=" + DEFAULT_TRAN_FEE + "," + UPDATED_TRAN_FEE);

        // Get all the transactionList where tranFee equals to UPDATED_TRAN_FEE
        defaultTransactionShouldNotBeFound("tranFee.in=" + UPDATED_TRAN_FEE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranFeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranFee is not null
        defaultTransactionShouldBeFound("tranFee.specified=true");

        // Get all the transactionList where tranFee is null
        defaultTransactionShouldNotBeFound("tranFee.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranFeeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranFee is greater than or equal to DEFAULT_TRAN_FEE
        defaultTransactionShouldBeFound("tranFee.greaterThanOrEqual=" + DEFAULT_TRAN_FEE);

        // Get all the transactionList where tranFee is greater than or equal to UPDATED_TRAN_FEE
        defaultTransactionShouldNotBeFound("tranFee.greaterThanOrEqual=" + UPDATED_TRAN_FEE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranFeeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranFee is less than or equal to DEFAULT_TRAN_FEE
        defaultTransactionShouldBeFound("tranFee.lessThanOrEqual=" + DEFAULT_TRAN_FEE);

        // Get all the transactionList where tranFee is less than or equal to SMALLER_TRAN_FEE
        defaultTransactionShouldNotBeFound("tranFee.lessThanOrEqual=" + SMALLER_TRAN_FEE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranFeeIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranFee is less than DEFAULT_TRAN_FEE
        defaultTransactionShouldNotBeFound("tranFee.lessThan=" + DEFAULT_TRAN_FEE);

        // Get all the transactionList where tranFee is less than UPDATED_TRAN_FEE
        defaultTransactionShouldBeFound("tranFee.lessThan=" + UPDATED_TRAN_FEE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTranFeeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where tranFee is greater than DEFAULT_TRAN_FEE
        defaultTransactionShouldNotBeFound("tranFee.greaterThan=" + DEFAULT_TRAN_FEE);

        // Get all the transactionList where tranFee is greater than SMALLER_TRAN_FEE
        defaultTransactionShouldBeFound("tranFee.greaterThan=" + SMALLER_TRAN_FEE);
    }


    @Test
    @Transactional
    public void getAllTransactionsByReferenceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where referenceNumber equals to DEFAULT_REFERENCE_NUMBER
        defaultTransactionShouldBeFound("referenceNumber.equals=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the transactionList where referenceNumber equals to UPDATED_REFERENCE_NUMBER
        defaultTransactionShouldNotBeFound("referenceNumber.equals=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionsByReferenceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where referenceNumber not equals to DEFAULT_REFERENCE_NUMBER
        defaultTransactionShouldNotBeFound("referenceNumber.notEquals=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the transactionList where referenceNumber not equals to UPDATED_REFERENCE_NUMBER
        defaultTransactionShouldBeFound("referenceNumber.notEquals=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionsByReferenceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where referenceNumber in DEFAULT_REFERENCE_NUMBER or UPDATED_REFERENCE_NUMBER
        defaultTransactionShouldBeFound("referenceNumber.in=" + DEFAULT_REFERENCE_NUMBER + "," + UPDATED_REFERENCE_NUMBER);

        // Get all the transactionList where referenceNumber equals to UPDATED_REFERENCE_NUMBER
        defaultTransactionShouldNotBeFound("referenceNumber.in=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionsByReferenceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where referenceNumber is not null
        defaultTransactionShouldBeFound("referenceNumber.specified=true");

        // Get all the transactionList where referenceNumber is null
        defaultTransactionShouldNotBeFound("referenceNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByReferenceNumberContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where referenceNumber contains DEFAULT_REFERENCE_NUMBER
        defaultTransactionShouldBeFound("referenceNumber.contains=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the transactionList where referenceNumber contains UPDATED_REFERENCE_NUMBER
        defaultTransactionShouldNotBeFound("referenceNumber.contains=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionsByReferenceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where referenceNumber does not contain DEFAULT_REFERENCE_NUMBER
        defaultTransactionShouldNotBeFound("referenceNumber.doesNotContain=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the transactionList where referenceNumber does not contain UPDATED_REFERENCE_NUMBER
        defaultTransactionShouldBeFound("referenceNumber.doesNotContain=" + UPDATED_REFERENCE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTransactionsByResponseStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where responseStatus equals to DEFAULT_RESPONSE_STATUS
        defaultTransactionShouldBeFound("responseStatus.equals=" + DEFAULT_RESPONSE_STATUS);

        // Get all the transactionList where responseStatus equals to UPDATED_RESPONSE_STATUS
        defaultTransactionShouldNotBeFound("responseStatus.equals=" + UPDATED_RESPONSE_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionsByResponseStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where responseStatus not equals to DEFAULT_RESPONSE_STATUS
        defaultTransactionShouldNotBeFound("responseStatus.notEquals=" + DEFAULT_RESPONSE_STATUS);

        // Get all the transactionList where responseStatus not equals to UPDATED_RESPONSE_STATUS
        defaultTransactionShouldBeFound("responseStatus.notEquals=" + UPDATED_RESPONSE_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionsByResponseStatusIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where responseStatus in DEFAULT_RESPONSE_STATUS or UPDATED_RESPONSE_STATUS
        defaultTransactionShouldBeFound("responseStatus.in=" + DEFAULT_RESPONSE_STATUS + "," + UPDATED_RESPONSE_STATUS);

        // Get all the transactionList where responseStatus equals to UPDATED_RESPONSE_STATUS
        defaultTransactionShouldNotBeFound("responseStatus.in=" + UPDATED_RESPONSE_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionsByResponseStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where responseStatus is not null
        defaultTransactionShouldBeFound("responseStatus.specified=true");

        // Get all the transactionList where responseStatus is null
        defaultTransactionShouldNotBeFound("responseStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByResponseStatusContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where responseStatus contains DEFAULT_RESPONSE_STATUS
        defaultTransactionShouldBeFound("responseStatus.contains=" + DEFAULT_RESPONSE_STATUS);

        // Get all the transactionList where responseStatus contains UPDATED_RESPONSE_STATUS
        defaultTransactionShouldNotBeFound("responseStatus.contains=" + UPDATED_RESPONSE_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionsByResponseStatusNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where responseStatus does not contain DEFAULT_RESPONSE_STATUS
        defaultTransactionShouldNotBeFound("responseStatus.doesNotContain=" + DEFAULT_RESPONSE_STATUS);

        // Get all the transactionList where responseStatus does not contain UPDATED_RESPONSE_STATUS
        defaultTransactionShouldBeFound("responseStatus.doesNotContain=" + UPDATED_RESPONSE_STATUS);
    }


    @Test
    @Transactional
    public void getAllTransactionsByServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where serviceName equals to DEFAULT_SERVICE_NAME
        defaultTransactionShouldBeFound("serviceName.equals=" + DEFAULT_SERVICE_NAME);

        // Get all the transactionList where serviceName equals to UPDATED_SERVICE_NAME
        defaultTransactionShouldNotBeFound("serviceName.equals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByServiceNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where serviceName not equals to DEFAULT_SERVICE_NAME
        defaultTransactionShouldNotBeFound("serviceName.notEquals=" + DEFAULT_SERVICE_NAME);

        // Get all the transactionList where serviceName not equals to UPDATED_SERVICE_NAME
        defaultTransactionShouldBeFound("serviceName.notEquals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where serviceName in DEFAULT_SERVICE_NAME or UPDATED_SERVICE_NAME
        defaultTransactionShouldBeFound("serviceName.in=" + DEFAULT_SERVICE_NAME + "," + UPDATED_SERVICE_NAME);

        // Get all the transactionList where serviceName equals to UPDATED_SERVICE_NAME
        defaultTransactionShouldNotBeFound("serviceName.in=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where serviceName is not null
        defaultTransactionShouldBeFound("serviceName.specified=true");

        // Get all the transactionList where serviceName is null
        defaultTransactionShouldNotBeFound("serviceName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByServiceNameContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where serviceName contains DEFAULT_SERVICE_NAME
        defaultTransactionShouldBeFound("serviceName.contains=" + DEFAULT_SERVICE_NAME);

        // Get all the transactionList where serviceName contains UPDATED_SERVICE_NAME
        defaultTransactionShouldNotBeFound("serviceName.contains=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByServiceNameNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where serviceName does not contain DEFAULT_SERVICE_NAME
        defaultTransactionShouldNotBeFound("serviceName.doesNotContain=" + DEFAULT_SERVICE_NAME);

        // Get all the transactionList where serviceName does not contain UPDATED_SERVICE_NAME
        defaultTransactionShouldBeFound("serviceName.doesNotContain=" + UPDATED_SERVICE_NAME);
    }


    @Test
    @Transactional
    public void getAllTransactionsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where user equals to DEFAULT_USER
        defaultTransactionShouldBeFound("user.equals=" + DEFAULT_USER);

        // Get all the transactionList where user equals to UPDATED_USER
        defaultTransactionShouldNotBeFound("user.equals=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllTransactionsByUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where user not equals to DEFAULT_USER
        defaultTransactionShouldNotBeFound("user.notEquals=" + DEFAULT_USER);

        // Get all the transactionList where user not equals to UPDATED_USER
        defaultTransactionShouldBeFound("user.notEquals=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllTransactionsByUserIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where user in DEFAULT_USER or UPDATED_USER
        defaultTransactionShouldBeFound("user.in=" + DEFAULT_USER + "," + UPDATED_USER);

        // Get all the transactionList where user equals to UPDATED_USER
        defaultTransactionShouldNotBeFound("user.in=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllTransactionsByUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where user is not null
        defaultTransactionShouldBeFound("user.specified=true");

        // Get all the transactionList where user is null
        defaultTransactionShouldNotBeFound("user.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByUserContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where user contains DEFAULT_USER
        defaultTransactionShouldBeFound("user.contains=" + DEFAULT_USER);

        // Get all the transactionList where user contains UPDATED_USER
        defaultTransactionShouldNotBeFound("user.contains=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllTransactionsByUserNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where user does not contain DEFAULT_USER
        defaultTransactionShouldNotBeFound("user.doesNotContain=" + DEFAULT_USER);

        // Get all the transactionList where user does not contain UPDATED_USER
        defaultTransactionShouldBeFound("user.doesNotContain=" + UPDATED_USER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionShouldBeFound(String filter) throws Exception {
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].terminalId").value(hasItem(DEFAULT_TERMINAL_ID)))
            .andExpect(jsonPath("$.[*].tranDateTime").value(hasItem(DEFAULT_TRAN_DATE_TIME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].tranAmount").value(hasItem(DEFAULT_TRAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].tranFee").value(hasItem(DEFAULT_TRAN_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].responseStatus").value(hasItem(DEFAULT_RESPONSE_STATUS)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)));

        // Check, that the count call also returns 1
        restTransactionMockMvc.perform(get("/api/transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionShouldNotBeFound(String filter) throws Exception {
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionMockMvc.perform(get("/api/transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionService.save(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .clientId(UPDATED_CLIENT_ID)
            .terminalId(UPDATED_TERMINAL_ID)
            .tranDateTime(UPDATED_TRAN_DATE_TIME)
            .date(UPDATED_DATE)
            .time(UPDATED_TIME)
            .tranAmount(UPDATED_TRAN_AMOUNT)
            .tranFee(UPDATED_TRAN_FEE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .responseStatus(UPDATED_RESPONSE_STATUS)
            .serviceName(UPDATED_SERVICE_NAME)
            .user(UPDATED_USER);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaction)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testTransaction.getTerminalId()).isEqualTo(UPDATED_TERMINAL_ID);
        assertThat(testTransaction.getTranDateTime()).isEqualTo(UPDATED_TRAN_DATE_TIME);
        assertThat(testTransaction.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTransaction.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testTransaction.getTranAmount()).isEqualTo(UPDATED_TRAN_AMOUNT);
        assertThat(testTransaction.getTranFee()).isEqualTo(UPDATED_TRAN_FEE);
        assertThat(testTransaction.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testTransaction.getResponseStatus()).isEqualTo(UPDATED_RESPONSE_STATUS);
        assertThat(testTransaction.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testTransaction.getUser()).isEqualTo(UPDATED_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionService.save(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
