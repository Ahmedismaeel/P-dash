package com.ahmed.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.ahmed.myapp.domain.Terminal;
import com.ahmed.myapp.domain.*; // for static metamodels
import com.ahmed.myapp.repository.TerminalRepository;
import com.ahmed.myapp.service.dto.TerminalCriteria;

/**
 * Service for executing complex queries for {@link Terminal} entities in the database.
 * The main input is a {@link TerminalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Terminal} or a {@link Page} of {@link Terminal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TerminalQueryService extends QueryService<Terminal> {

    private final Logger log = LoggerFactory.getLogger(TerminalQueryService.class);

    private final TerminalRepository terminalRepository;

    public TerminalQueryService(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    /**
     * Return a {@link List} of {@link Terminal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Terminal> findByCriteria(TerminalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Terminal> specification = createSpecification(criteria);
        return terminalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Terminal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Terminal> findByCriteria(TerminalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Terminal> specification = createSpecification(criteria);
        return terminalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TerminalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Terminal> specification = createSpecification(criteria);
        return terminalRepository.count(specification);
    }

    /**
     * Function to convert {@link TerminalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Terminal> createSpecification(TerminalCriteria criteria) {
        Specification<Terminal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Terminal_.id));
            }
            if (criteria.getTerminalId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTerminalId(), Terminal_.terminalId));
            }
            if (criteria.getMerchantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMerchantId(), Terminal_.merchantId));
            }
            if (criteria.getMerchantName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMerchantName(), Terminal_.merchantName));
            }
            if (criteria.getMarketName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarketName(), Terminal_.marketName));
            }
            if (criteria.getPhoneNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNo(), Terminal_.phoneNo));
            }
            if (criteria.getsIMSerialNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getsIMSerialNo(), Terminal_.sIMSerialNo));
            }
            if (criteria.getpOSSerialNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getpOSSerialNo(), Terminal_.pOSSerialNo));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), Terminal_.location));
            }
            if (criteria.getUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUser(), Terminal_.user));
            }
        }
        return specification;
    }
}
