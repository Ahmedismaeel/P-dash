package com.ahmed.myapp.web.rest;

import com.ahmed.myapp.domain.Terminal;
import com.ahmed.myapp.service.TerminalService;
import com.ahmed.myapp.web.rest.errors.BadRequestAlertException;
import com.ahmed.myapp.service.dto.TerminalCriteria;
import com.ahmed.myapp.service.TerminalQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ahmed.myapp.domain.Terminal}.
 */
@RestController
@RequestMapping("/api")
public class TerminalResource {

    private final Logger log = LoggerFactory.getLogger(TerminalResource.class);

    private static final String ENTITY_NAME = "terminal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerminalService terminalService;

    private final TerminalQueryService terminalQueryService;

    public TerminalResource(TerminalService terminalService, TerminalQueryService terminalQueryService) {
        this.terminalService = terminalService;
        this.terminalQueryService = terminalQueryService;
    }

    /**
     * {@code POST  /terminals} : Create a new terminal.
     *
     * @param terminal the terminal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new terminal, or with status {@code 400 (Bad Request)} if the terminal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/terminals")
    public ResponseEntity<Terminal> createTerminal(@Valid @RequestBody Terminal terminal) throws URISyntaxException {
        log.debug("REST request to save Terminal : {}", terminal);
        if (terminal.getId() != null) {
            throw new BadRequestAlertException("A new terminal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Terminal result = terminalService.save(terminal);
        return ResponseEntity.created(new URI("/api/terminals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terminals} : Updates an existing terminal.
     *
     * @param terminal the terminal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terminal,
     * or with status {@code 400 (Bad Request)} if the terminal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the terminal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/terminals")
    public ResponseEntity<Terminal> updateTerminal(@Valid @RequestBody Terminal terminal) throws URISyntaxException {
        log.debug("REST request to update Terminal : {}", terminal);
        if (terminal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Terminal result = terminalService.save(terminal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, terminal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /terminals} : get all the terminals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terminals in body.
     */
    @GetMapping("/terminals")
    public ResponseEntity<List<Terminal>> getAllTerminals(TerminalCriteria criteria) {
        log.debug("REST request to get Terminals by criteria: {}", criteria);
        List<Terminal> entityList = terminalQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /terminals/count} : count all the terminals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/terminals/count")
    public ResponseEntity<Long> countTerminals(TerminalCriteria criteria) {
        log.debug("REST request to count Terminals by criteria: {}", criteria);
        return ResponseEntity.ok().body(terminalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /terminals/:id} : get the "id" terminal.
     *
     * @param id the id of the terminal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the terminal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/terminals/{id}")
    public ResponseEntity<Terminal> getTerminal(@PathVariable Long id) {
        log.debug("REST request to get Terminal : {}", id);
        Optional<Terminal> terminal = terminalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(terminal);
    }

    /**
     * {@code DELETE  /terminals/:id} : delete the "id" terminal.
     *
     * @param id the id of the terminal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/terminals/{id}")
    public ResponseEntity<Void> deleteTerminal(@PathVariable Long id) {
        log.debug("REST request to delete Terminal : {}", id);
        terminalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
