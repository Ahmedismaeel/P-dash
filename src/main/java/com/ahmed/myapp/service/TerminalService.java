package com.ahmed.myapp.service;

import com.ahmed.myapp.domain.Terminal;
import com.ahmed.myapp.repository.TerminalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Terminal}.
 */
@Service
@Transactional
public class TerminalService {

    private final Logger log = LoggerFactory.getLogger(TerminalService.class);

    private final TerminalRepository terminalRepository;

    public TerminalService(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    /**
     * Save a terminal.
     *
     * @param terminal the entity to save.
     * @return the persisted entity.
     */
    public Terminal save(Terminal terminal) {
        log.debug("Request to save Terminal : {}", terminal);
        return terminalRepository.save(terminal);
    }

    /**
     * Get all the terminals.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Terminal> findAll() {
        log.debug("Request to get all Terminals");
        return terminalRepository.findAll();
    }


    /**
     * Get one terminal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Terminal> findOne(Long id) {
        log.debug("Request to get Terminal : {}", id);
        return terminalRepository.findById(id);
    }

    /**
     * Delete the terminal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Terminal : {}", id);
        terminalRepository.deleteById(id);
    }
}
