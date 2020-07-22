package com.ahmed.myapp.web.rest;

import com.ahmed.myapp.PdashApp;
import com.ahmed.myapp.domain.Terminal;
import com.ahmed.myapp.repository.TerminalRepository;
import com.ahmed.myapp.service.TerminalService;
import com.ahmed.myapp.service.dto.TerminalCriteria;
import com.ahmed.myapp.service.TerminalQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TerminalResource} REST controller.
 */
@SpringBootTest(classes = PdashApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TerminalResourceIT {

    private static final String DEFAULT_TERMINAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_TERMINAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MERCHANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_MERCHANT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MERCHANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MERCHANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MARKET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MARKET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_S_IM_SERIAL_NO = "AAAAAAAAAA";
    private static final String UPDATED_S_IM_SERIAL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_P_OS_SERIAL_NO = "AAAAAAAAAA";
    private static final String UPDATED_P_OS_SERIAL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private TerminalQueryService terminalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerminalMockMvc;

    private Terminal terminal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Terminal createEntity(EntityManager em) {
        Terminal terminal = new Terminal()
            .terminalId(DEFAULT_TERMINAL_ID)
            .merchantId(DEFAULT_MERCHANT_ID)
            .merchantName(DEFAULT_MERCHANT_NAME)
            .marketName(DEFAULT_MARKET_NAME)
            .phoneNo(DEFAULT_PHONE_NO)
            .sIMSerialNo(DEFAULT_S_IM_SERIAL_NO)
            .pOSSerialNo(DEFAULT_P_OS_SERIAL_NO)
            .location(DEFAULT_LOCATION)
            .user(DEFAULT_USER);
        return terminal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Terminal createUpdatedEntity(EntityManager em) {
        Terminal terminal = new Terminal()
            .terminalId(UPDATED_TERMINAL_ID)
            .merchantId(UPDATED_MERCHANT_ID)
            .merchantName(UPDATED_MERCHANT_NAME)
            .marketName(UPDATED_MARKET_NAME)
            .phoneNo(UPDATED_PHONE_NO)
            .sIMSerialNo(UPDATED_S_IM_SERIAL_NO)
            .pOSSerialNo(UPDATED_P_OS_SERIAL_NO)
            .location(UPDATED_LOCATION)
            .user(UPDATED_USER);
        return terminal;
    }

    @BeforeEach
    public void initTest() {
        terminal = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerminal() throws Exception {
        int databaseSizeBeforeCreate = terminalRepository.findAll().size();
        // Create the Terminal
        restTerminalMockMvc.perform(post("/api/terminals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminal)))
            .andExpect(status().isCreated());

        // Validate the Terminal in the database
        List<Terminal> terminalList = terminalRepository.findAll();
        assertThat(terminalList).hasSize(databaseSizeBeforeCreate + 1);
        Terminal testTerminal = terminalList.get(terminalList.size() - 1);
        assertThat(testTerminal.getTerminalId()).isEqualTo(DEFAULT_TERMINAL_ID);
        assertThat(testTerminal.getMerchantId()).isEqualTo(DEFAULT_MERCHANT_ID);
        assertThat(testTerminal.getMerchantName()).isEqualTo(DEFAULT_MERCHANT_NAME);
        assertThat(testTerminal.getMarketName()).isEqualTo(DEFAULT_MARKET_NAME);
        assertThat(testTerminal.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testTerminal.getsIMSerialNo()).isEqualTo(DEFAULT_S_IM_SERIAL_NO);
        assertThat(testTerminal.getpOSSerialNo()).isEqualTo(DEFAULT_P_OS_SERIAL_NO);
        assertThat(testTerminal.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testTerminal.getUser()).isEqualTo(DEFAULT_USER);
    }

    @Test
    @Transactional
    public void createTerminalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = terminalRepository.findAll().size();

        // Create the Terminal with an existing ID
        terminal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerminalMockMvc.perform(post("/api/terminals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminal)))
            .andExpect(status().isBadRequest());

        // Validate the Terminal in the database
        List<Terminal> terminalList = terminalRepository.findAll();
        assertThat(terminalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTerminalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = terminalRepository.findAll().size();
        // set the field null
        terminal.setTerminalId(null);

        // Create the Terminal, which fails.


        restTerminalMockMvc.perform(post("/api/terminals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminal)))
            .andExpect(status().isBadRequest());

        List<Terminal> terminalList = terminalRepository.findAll();
        assertThat(terminalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTerminals() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList
        restTerminalMockMvc.perform(get("/api/terminals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terminal.getId().intValue())))
            .andExpect(jsonPath("$.[*].terminalId").value(hasItem(DEFAULT_TERMINAL_ID)))
            .andExpect(jsonPath("$.[*].merchantId").value(hasItem(DEFAULT_MERCHANT_ID)))
            .andExpect(jsonPath("$.[*].merchantName").value(hasItem(DEFAULT_MERCHANT_NAME)))
            .andExpect(jsonPath("$.[*].marketName").value(hasItem(DEFAULT_MARKET_NAME)))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO)))
            .andExpect(jsonPath("$.[*].sIMSerialNo").value(hasItem(DEFAULT_S_IM_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].pOSSerialNo").value(hasItem(DEFAULT_P_OS_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)));
    }
    
    @Test
    @Transactional
    public void getTerminal() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get the terminal
        restTerminalMockMvc.perform(get("/api/terminals/{id}", terminal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(terminal.getId().intValue()))
            .andExpect(jsonPath("$.terminalId").value(DEFAULT_TERMINAL_ID))
            .andExpect(jsonPath("$.merchantId").value(DEFAULT_MERCHANT_ID))
            .andExpect(jsonPath("$.merchantName").value(DEFAULT_MERCHANT_NAME))
            .andExpect(jsonPath("$.marketName").value(DEFAULT_MARKET_NAME))
            .andExpect(jsonPath("$.phoneNo").value(DEFAULT_PHONE_NO))
            .andExpect(jsonPath("$.sIMSerialNo").value(DEFAULT_S_IM_SERIAL_NO))
            .andExpect(jsonPath("$.pOSSerialNo").value(DEFAULT_P_OS_SERIAL_NO))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER));
    }


    @Test
    @Transactional
    public void getTerminalsByIdFiltering() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        Long id = terminal.getId();

        defaultTerminalShouldBeFound("id.equals=" + id);
        defaultTerminalShouldNotBeFound("id.notEquals=" + id);

        defaultTerminalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTerminalShouldNotBeFound("id.greaterThan=" + id);

        defaultTerminalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTerminalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTerminalsByTerminalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where terminalId equals to DEFAULT_TERMINAL_ID
        defaultTerminalShouldBeFound("terminalId.equals=" + DEFAULT_TERMINAL_ID);

        // Get all the terminalList where terminalId equals to UPDATED_TERMINAL_ID
        defaultTerminalShouldNotBeFound("terminalId.equals=" + UPDATED_TERMINAL_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalsByTerminalIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where terminalId not equals to DEFAULT_TERMINAL_ID
        defaultTerminalShouldNotBeFound("terminalId.notEquals=" + DEFAULT_TERMINAL_ID);

        // Get all the terminalList where terminalId not equals to UPDATED_TERMINAL_ID
        defaultTerminalShouldBeFound("terminalId.notEquals=" + UPDATED_TERMINAL_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalsByTerminalIdIsInShouldWork() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where terminalId in DEFAULT_TERMINAL_ID or UPDATED_TERMINAL_ID
        defaultTerminalShouldBeFound("terminalId.in=" + DEFAULT_TERMINAL_ID + "," + UPDATED_TERMINAL_ID);

        // Get all the terminalList where terminalId equals to UPDATED_TERMINAL_ID
        defaultTerminalShouldNotBeFound("terminalId.in=" + UPDATED_TERMINAL_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalsByTerminalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where terminalId is not null
        defaultTerminalShouldBeFound("terminalId.specified=true");

        // Get all the terminalList where terminalId is null
        defaultTerminalShouldNotBeFound("terminalId.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalsByTerminalIdContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where terminalId contains DEFAULT_TERMINAL_ID
        defaultTerminalShouldBeFound("terminalId.contains=" + DEFAULT_TERMINAL_ID);

        // Get all the terminalList where terminalId contains UPDATED_TERMINAL_ID
        defaultTerminalShouldNotBeFound("terminalId.contains=" + UPDATED_TERMINAL_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalsByTerminalIdNotContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where terminalId does not contain DEFAULT_TERMINAL_ID
        defaultTerminalShouldNotBeFound("terminalId.doesNotContain=" + DEFAULT_TERMINAL_ID);

        // Get all the terminalList where terminalId does not contain UPDATED_TERMINAL_ID
        defaultTerminalShouldBeFound("terminalId.doesNotContain=" + UPDATED_TERMINAL_ID);
    }


    @Test
    @Transactional
    public void getAllTerminalsByMerchantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantId equals to DEFAULT_MERCHANT_ID
        defaultTerminalShouldBeFound("merchantId.equals=" + DEFAULT_MERCHANT_ID);

        // Get all the terminalList where merchantId equals to UPDATED_MERCHANT_ID
        defaultTerminalShouldNotBeFound("merchantId.equals=" + UPDATED_MERCHANT_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMerchantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantId not equals to DEFAULT_MERCHANT_ID
        defaultTerminalShouldNotBeFound("merchantId.notEquals=" + DEFAULT_MERCHANT_ID);

        // Get all the terminalList where merchantId not equals to UPDATED_MERCHANT_ID
        defaultTerminalShouldBeFound("merchantId.notEquals=" + UPDATED_MERCHANT_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMerchantIdIsInShouldWork() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantId in DEFAULT_MERCHANT_ID or UPDATED_MERCHANT_ID
        defaultTerminalShouldBeFound("merchantId.in=" + DEFAULT_MERCHANT_ID + "," + UPDATED_MERCHANT_ID);

        // Get all the terminalList where merchantId equals to UPDATED_MERCHANT_ID
        defaultTerminalShouldNotBeFound("merchantId.in=" + UPDATED_MERCHANT_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMerchantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantId is not null
        defaultTerminalShouldBeFound("merchantId.specified=true");

        // Get all the terminalList where merchantId is null
        defaultTerminalShouldNotBeFound("merchantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalsByMerchantIdContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantId contains DEFAULT_MERCHANT_ID
        defaultTerminalShouldBeFound("merchantId.contains=" + DEFAULT_MERCHANT_ID);

        // Get all the terminalList where merchantId contains UPDATED_MERCHANT_ID
        defaultTerminalShouldNotBeFound("merchantId.contains=" + UPDATED_MERCHANT_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMerchantIdNotContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantId does not contain DEFAULT_MERCHANT_ID
        defaultTerminalShouldNotBeFound("merchantId.doesNotContain=" + DEFAULT_MERCHANT_ID);

        // Get all the terminalList where merchantId does not contain UPDATED_MERCHANT_ID
        defaultTerminalShouldBeFound("merchantId.doesNotContain=" + UPDATED_MERCHANT_ID);
    }


    @Test
    @Transactional
    public void getAllTerminalsByMerchantNameIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantName equals to DEFAULT_MERCHANT_NAME
        defaultTerminalShouldBeFound("merchantName.equals=" + DEFAULT_MERCHANT_NAME);

        // Get all the terminalList where merchantName equals to UPDATED_MERCHANT_NAME
        defaultTerminalShouldNotBeFound("merchantName.equals=" + UPDATED_MERCHANT_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMerchantNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantName not equals to DEFAULT_MERCHANT_NAME
        defaultTerminalShouldNotBeFound("merchantName.notEquals=" + DEFAULT_MERCHANT_NAME);

        // Get all the terminalList where merchantName not equals to UPDATED_MERCHANT_NAME
        defaultTerminalShouldBeFound("merchantName.notEquals=" + UPDATED_MERCHANT_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMerchantNameIsInShouldWork() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantName in DEFAULT_MERCHANT_NAME or UPDATED_MERCHANT_NAME
        defaultTerminalShouldBeFound("merchantName.in=" + DEFAULT_MERCHANT_NAME + "," + UPDATED_MERCHANT_NAME);

        // Get all the terminalList where merchantName equals to UPDATED_MERCHANT_NAME
        defaultTerminalShouldNotBeFound("merchantName.in=" + UPDATED_MERCHANT_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMerchantNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantName is not null
        defaultTerminalShouldBeFound("merchantName.specified=true");

        // Get all the terminalList where merchantName is null
        defaultTerminalShouldNotBeFound("merchantName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalsByMerchantNameContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantName contains DEFAULT_MERCHANT_NAME
        defaultTerminalShouldBeFound("merchantName.contains=" + DEFAULT_MERCHANT_NAME);

        // Get all the terminalList where merchantName contains UPDATED_MERCHANT_NAME
        defaultTerminalShouldNotBeFound("merchantName.contains=" + UPDATED_MERCHANT_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMerchantNameNotContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where merchantName does not contain DEFAULT_MERCHANT_NAME
        defaultTerminalShouldNotBeFound("merchantName.doesNotContain=" + DEFAULT_MERCHANT_NAME);

        // Get all the terminalList where merchantName does not contain UPDATED_MERCHANT_NAME
        defaultTerminalShouldBeFound("merchantName.doesNotContain=" + UPDATED_MERCHANT_NAME);
    }


    @Test
    @Transactional
    public void getAllTerminalsByMarketNameIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where marketName equals to DEFAULT_MARKET_NAME
        defaultTerminalShouldBeFound("marketName.equals=" + DEFAULT_MARKET_NAME);

        // Get all the terminalList where marketName equals to UPDATED_MARKET_NAME
        defaultTerminalShouldNotBeFound("marketName.equals=" + UPDATED_MARKET_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMarketNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where marketName not equals to DEFAULT_MARKET_NAME
        defaultTerminalShouldNotBeFound("marketName.notEquals=" + DEFAULT_MARKET_NAME);

        // Get all the terminalList where marketName not equals to UPDATED_MARKET_NAME
        defaultTerminalShouldBeFound("marketName.notEquals=" + UPDATED_MARKET_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMarketNameIsInShouldWork() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where marketName in DEFAULT_MARKET_NAME or UPDATED_MARKET_NAME
        defaultTerminalShouldBeFound("marketName.in=" + DEFAULT_MARKET_NAME + "," + UPDATED_MARKET_NAME);

        // Get all the terminalList where marketName equals to UPDATED_MARKET_NAME
        defaultTerminalShouldNotBeFound("marketName.in=" + UPDATED_MARKET_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMarketNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where marketName is not null
        defaultTerminalShouldBeFound("marketName.specified=true");

        // Get all the terminalList where marketName is null
        defaultTerminalShouldNotBeFound("marketName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalsByMarketNameContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where marketName contains DEFAULT_MARKET_NAME
        defaultTerminalShouldBeFound("marketName.contains=" + DEFAULT_MARKET_NAME);

        // Get all the terminalList where marketName contains UPDATED_MARKET_NAME
        defaultTerminalShouldNotBeFound("marketName.contains=" + UPDATED_MARKET_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalsByMarketNameNotContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where marketName does not contain DEFAULT_MARKET_NAME
        defaultTerminalShouldNotBeFound("marketName.doesNotContain=" + DEFAULT_MARKET_NAME);

        // Get all the terminalList where marketName does not contain UPDATED_MARKET_NAME
        defaultTerminalShouldBeFound("marketName.doesNotContain=" + UPDATED_MARKET_NAME);
    }


    @Test
    @Transactional
    public void getAllTerminalsByPhoneNoIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where phoneNo equals to DEFAULT_PHONE_NO
        defaultTerminalShouldBeFound("phoneNo.equals=" + DEFAULT_PHONE_NO);

        // Get all the terminalList where phoneNo equals to UPDATED_PHONE_NO
        defaultTerminalShouldNotBeFound("phoneNo.equals=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsByPhoneNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where phoneNo not equals to DEFAULT_PHONE_NO
        defaultTerminalShouldNotBeFound("phoneNo.notEquals=" + DEFAULT_PHONE_NO);

        // Get all the terminalList where phoneNo not equals to UPDATED_PHONE_NO
        defaultTerminalShouldBeFound("phoneNo.notEquals=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsByPhoneNoIsInShouldWork() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where phoneNo in DEFAULT_PHONE_NO or UPDATED_PHONE_NO
        defaultTerminalShouldBeFound("phoneNo.in=" + DEFAULT_PHONE_NO + "," + UPDATED_PHONE_NO);

        // Get all the terminalList where phoneNo equals to UPDATED_PHONE_NO
        defaultTerminalShouldNotBeFound("phoneNo.in=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsByPhoneNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where phoneNo is not null
        defaultTerminalShouldBeFound("phoneNo.specified=true");

        // Get all the terminalList where phoneNo is null
        defaultTerminalShouldNotBeFound("phoneNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalsByPhoneNoContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where phoneNo contains DEFAULT_PHONE_NO
        defaultTerminalShouldBeFound("phoneNo.contains=" + DEFAULT_PHONE_NO);

        // Get all the terminalList where phoneNo contains UPDATED_PHONE_NO
        defaultTerminalShouldNotBeFound("phoneNo.contains=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsByPhoneNoNotContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where phoneNo does not contain DEFAULT_PHONE_NO
        defaultTerminalShouldNotBeFound("phoneNo.doesNotContain=" + DEFAULT_PHONE_NO);

        // Get all the terminalList where phoneNo does not contain UPDATED_PHONE_NO
        defaultTerminalShouldBeFound("phoneNo.doesNotContain=" + UPDATED_PHONE_NO);
    }


    @Test
    @Transactional
    public void getAllTerminalsBysIMSerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where sIMSerialNo equals to DEFAULT_S_IM_SERIAL_NO
        defaultTerminalShouldBeFound("sIMSerialNo.equals=" + DEFAULT_S_IM_SERIAL_NO);

        // Get all the terminalList where sIMSerialNo equals to UPDATED_S_IM_SERIAL_NO
        defaultTerminalShouldNotBeFound("sIMSerialNo.equals=" + UPDATED_S_IM_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsBysIMSerialNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where sIMSerialNo not equals to DEFAULT_S_IM_SERIAL_NO
        defaultTerminalShouldNotBeFound("sIMSerialNo.notEquals=" + DEFAULT_S_IM_SERIAL_NO);

        // Get all the terminalList where sIMSerialNo not equals to UPDATED_S_IM_SERIAL_NO
        defaultTerminalShouldBeFound("sIMSerialNo.notEquals=" + UPDATED_S_IM_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsBysIMSerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where sIMSerialNo in DEFAULT_S_IM_SERIAL_NO or UPDATED_S_IM_SERIAL_NO
        defaultTerminalShouldBeFound("sIMSerialNo.in=" + DEFAULT_S_IM_SERIAL_NO + "," + UPDATED_S_IM_SERIAL_NO);

        // Get all the terminalList where sIMSerialNo equals to UPDATED_S_IM_SERIAL_NO
        defaultTerminalShouldNotBeFound("sIMSerialNo.in=" + UPDATED_S_IM_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsBysIMSerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where sIMSerialNo is not null
        defaultTerminalShouldBeFound("sIMSerialNo.specified=true");

        // Get all the terminalList where sIMSerialNo is null
        defaultTerminalShouldNotBeFound("sIMSerialNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalsBysIMSerialNoContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where sIMSerialNo contains DEFAULT_S_IM_SERIAL_NO
        defaultTerminalShouldBeFound("sIMSerialNo.contains=" + DEFAULT_S_IM_SERIAL_NO);

        // Get all the terminalList where sIMSerialNo contains UPDATED_S_IM_SERIAL_NO
        defaultTerminalShouldNotBeFound("sIMSerialNo.contains=" + UPDATED_S_IM_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsBysIMSerialNoNotContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where sIMSerialNo does not contain DEFAULT_S_IM_SERIAL_NO
        defaultTerminalShouldNotBeFound("sIMSerialNo.doesNotContain=" + DEFAULT_S_IM_SERIAL_NO);

        // Get all the terminalList where sIMSerialNo does not contain UPDATED_S_IM_SERIAL_NO
        defaultTerminalShouldBeFound("sIMSerialNo.doesNotContain=" + UPDATED_S_IM_SERIAL_NO);
    }


    @Test
    @Transactional
    public void getAllTerminalsBypOSSerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where pOSSerialNo equals to DEFAULT_P_OS_SERIAL_NO
        defaultTerminalShouldBeFound("pOSSerialNo.equals=" + DEFAULT_P_OS_SERIAL_NO);

        // Get all the terminalList where pOSSerialNo equals to UPDATED_P_OS_SERIAL_NO
        defaultTerminalShouldNotBeFound("pOSSerialNo.equals=" + UPDATED_P_OS_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsBypOSSerialNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where pOSSerialNo not equals to DEFAULT_P_OS_SERIAL_NO
        defaultTerminalShouldNotBeFound("pOSSerialNo.notEquals=" + DEFAULT_P_OS_SERIAL_NO);

        // Get all the terminalList where pOSSerialNo not equals to UPDATED_P_OS_SERIAL_NO
        defaultTerminalShouldBeFound("pOSSerialNo.notEquals=" + UPDATED_P_OS_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsBypOSSerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where pOSSerialNo in DEFAULT_P_OS_SERIAL_NO or UPDATED_P_OS_SERIAL_NO
        defaultTerminalShouldBeFound("pOSSerialNo.in=" + DEFAULT_P_OS_SERIAL_NO + "," + UPDATED_P_OS_SERIAL_NO);

        // Get all the terminalList where pOSSerialNo equals to UPDATED_P_OS_SERIAL_NO
        defaultTerminalShouldNotBeFound("pOSSerialNo.in=" + UPDATED_P_OS_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsBypOSSerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where pOSSerialNo is not null
        defaultTerminalShouldBeFound("pOSSerialNo.specified=true");

        // Get all the terminalList where pOSSerialNo is null
        defaultTerminalShouldNotBeFound("pOSSerialNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalsBypOSSerialNoContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where pOSSerialNo contains DEFAULT_P_OS_SERIAL_NO
        defaultTerminalShouldBeFound("pOSSerialNo.contains=" + DEFAULT_P_OS_SERIAL_NO);

        // Get all the terminalList where pOSSerialNo contains UPDATED_P_OS_SERIAL_NO
        defaultTerminalShouldNotBeFound("pOSSerialNo.contains=" + UPDATED_P_OS_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllTerminalsBypOSSerialNoNotContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where pOSSerialNo does not contain DEFAULT_P_OS_SERIAL_NO
        defaultTerminalShouldNotBeFound("pOSSerialNo.doesNotContain=" + DEFAULT_P_OS_SERIAL_NO);

        // Get all the terminalList where pOSSerialNo does not contain UPDATED_P_OS_SERIAL_NO
        defaultTerminalShouldBeFound("pOSSerialNo.doesNotContain=" + UPDATED_P_OS_SERIAL_NO);
    }


    @Test
    @Transactional
    public void getAllTerminalsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where location equals to DEFAULT_LOCATION
        defaultTerminalShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the terminalList where location equals to UPDATED_LOCATION
        defaultTerminalShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllTerminalsByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where location not equals to DEFAULT_LOCATION
        defaultTerminalShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the terminalList where location not equals to UPDATED_LOCATION
        defaultTerminalShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllTerminalsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultTerminalShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the terminalList where location equals to UPDATED_LOCATION
        defaultTerminalShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllTerminalsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where location is not null
        defaultTerminalShouldBeFound("location.specified=true");

        // Get all the terminalList where location is null
        defaultTerminalShouldNotBeFound("location.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalsByLocationContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where location contains DEFAULT_LOCATION
        defaultTerminalShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the terminalList where location contains UPDATED_LOCATION
        defaultTerminalShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllTerminalsByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where location does not contain DEFAULT_LOCATION
        defaultTerminalShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the terminalList where location does not contain UPDATED_LOCATION
        defaultTerminalShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }


    @Test
    @Transactional
    public void getAllTerminalsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where user equals to DEFAULT_USER
        defaultTerminalShouldBeFound("user.equals=" + DEFAULT_USER);

        // Get all the terminalList where user equals to UPDATED_USER
        defaultTerminalShouldNotBeFound("user.equals=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllTerminalsByUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where user not equals to DEFAULT_USER
        defaultTerminalShouldNotBeFound("user.notEquals=" + DEFAULT_USER);

        // Get all the terminalList where user not equals to UPDATED_USER
        defaultTerminalShouldBeFound("user.notEquals=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllTerminalsByUserIsInShouldWork() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where user in DEFAULT_USER or UPDATED_USER
        defaultTerminalShouldBeFound("user.in=" + DEFAULT_USER + "," + UPDATED_USER);

        // Get all the terminalList where user equals to UPDATED_USER
        defaultTerminalShouldNotBeFound("user.in=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllTerminalsByUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where user is not null
        defaultTerminalShouldBeFound("user.specified=true");

        // Get all the terminalList where user is null
        defaultTerminalShouldNotBeFound("user.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalsByUserContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where user contains DEFAULT_USER
        defaultTerminalShouldBeFound("user.contains=" + DEFAULT_USER);

        // Get all the terminalList where user contains UPDATED_USER
        defaultTerminalShouldNotBeFound("user.contains=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllTerminalsByUserNotContainsSomething() throws Exception {
        // Initialize the database
        terminalRepository.saveAndFlush(terminal);

        // Get all the terminalList where user does not contain DEFAULT_USER
        defaultTerminalShouldNotBeFound("user.doesNotContain=" + DEFAULT_USER);

        // Get all the terminalList where user does not contain UPDATED_USER
        defaultTerminalShouldBeFound("user.doesNotContain=" + UPDATED_USER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTerminalShouldBeFound(String filter) throws Exception {
        restTerminalMockMvc.perform(get("/api/terminals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terminal.getId().intValue())))
            .andExpect(jsonPath("$.[*].terminalId").value(hasItem(DEFAULT_TERMINAL_ID)))
            .andExpect(jsonPath("$.[*].merchantId").value(hasItem(DEFAULT_MERCHANT_ID)))
            .andExpect(jsonPath("$.[*].merchantName").value(hasItem(DEFAULT_MERCHANT_NAME)))
            .andExpect(jsonPath("$.[*].marketName").value(hasItem(DEFAULT_MARKET_NAME)))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO)))
            .andExpect(jsonPath("$.[*].sIMSerialNo").value(hasItem(DEFAULT_S_IM_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].pOSSerialNo").value(hasItem(DEFAULT_P_OS_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)));

        // Check, that the count call also returns 1
        restTerminalMockMvc.perform(get("/api/terminals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTerminalShouldNotBeFound(String filter) throws Exception {
        restTerminalMockMvc.perform(get("/api/terminals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTerminalMockMvc.perform(get("/api/terminals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTerminal() throws Exception {
        // Get the terminal
        restTerminalMockMvc.perform(get("/api/terminals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerminal() throws Exception {
        // Initialize the database
        terminalService.save(terminal);

        int databaseSizeBeforeUpdate = terminalRepository.findAll().size();

        // Update the terminal
        Terminal updatedTerminal = terminalRepository.findById(terminal.getId()).get();
        // Disconnect from session so that the updates on updatedTerminal are not directly saved in db
        em.detach(updatedTerminal);
        updatedTerminal
            .terminalId(UPDATED_TERMINAL_ID)
            .merchantId(UPDATED_MERCHANT_ID)
            .merchantName(UPDATED_MERCHANT_NAME)
            .marketName(UPDATED_MARKET_NAME)
            .phoneNo(UPDATED_PHONE_NO)
            .sIMSerialNo(UPDATED_S_IM_SERIAL_NO)
            .pOSSerialNo(UPDATED_P_OS_SERIAL_NO)
            .location(UPDATED_LOCATION)
            .user(UPDATED_USER);

        restTerminalMockMvc.perform(put("/api/terminals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTerminal)))
            .andExpect(status().isOk());

        // Validate the Terminal in the database
        List<Terminal> terminalList = terminalRepository.findAll();
        assertThat(terminalList).hasSize(databaseSizeBeforeUpdate);
        Terminal testTerminal = terminalList.get(terminalList.size() - 1);
        assertThat(testTerminal.getTerminalId()).isEqualTo(UPDATED_TERMINAL_ID);
        assertThat(testTerminal.getMerchantId()).isEqualTo(UPDATED_MERCHANT_ID);
        assertThat(testTerminal.getMerchantName()).isEqualTo(UPDATED_MERCHANT_NAME);
        assertThat(testTerminal.getMarketName()).isEqualTo(UPDATED_MARKET_NAME);
        assertThat(testTerminal.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testTerminal.getsIMSerialNo()).isEqualTo(UPDATED_S_IM_SERIAL_NO);
        assertThat(testTerminal.getpOSSerialNo()).isEqualTo(UPDATED_P_OS_SERIAL_NO);
        assertThat(testTerminal.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testTerminal.getUser()).isEqualTo(UPDATED_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingTerminal() throws Exception {
        int databaseSizeBeforeUpdate = terminalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerminalMockMvc.perform(put("/api/terminals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminal)))
            .andExpect(status().isBadRequest());

        // Validate the Terminal in the database
        List<Terminal> terminalList = terminalRepository.findAll();
        assertThat(terminalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTerminal() throws Exception {
        // Initialize the database
        terminalService.save(terminal);

        int databaseSizeBeforeDelete = terminalRepository.findAll().size();

        // Delete the terminal
        restTerminalMockMvc.perform(delete("/api/terminals/{id}", terminal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Terminal> terminalList = terminalRepository.findAll();
        assertThat(terminalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
