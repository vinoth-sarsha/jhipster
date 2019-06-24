package com.microsample.myapp.web.rest;

import com.microsample.myapp.service.Pp_userService;
import com.microsample.myapp.web.rest.errors.BadRequestAlertException;
import com.microsample.myapp.service.dto.Pp_userDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.microsample.myapp.domain.Pp_user}.
 */
@RestController
@RequestMapping("/api")
public class Pp_userResource {

    private final Logger log = LoggerFactory.getLogger(Pp_userResource.class);

    private static final String ENTITY_NAME = "microservice1PpUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Pp_userService pp_userService;

    public Pp_userResource(Pp_userService pp_userService) {
        this.pp_userService = pp_userService;
    }

    /**
     * {@code POST  /pp-users} : Create a new pp_user.
     *
     * @param pp_userDTO the pp_userDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pp_userDTO, or with status {@code 400 (Bad Request)} if the pp_user has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pp-users")
    public ResponseEntity<Pp_userDTO> createPp_user(@Valid @RequestBody Pp_userDTO pp_userDTO) throws URISyntaxException {
        log.debug("REST request to save Pp_user : {}", pp_userDTO);
        if (pp_userDTO.getId() != null) {
            throw new BadRequestAlertException("A new pp_user cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pp_userDTO result = pp_userService.save(pp_userDTO);
        return ResponseEntity.created(new URI("/api/pp-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pp-users} : Updates an existing pp_user.
     *
     * @param pp_userDTO the pp_userDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pp_userDTO,
     * or with status {@code 400 (Bad Request)} if the pp_userDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pp_userDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pp-users")
    public ResponseEntity<Pp_userDTO> updatePp_user(@Valid @RequestBody Pp_userDTO pp_userDTO) throws URISyntaxException {
        log.debug("REST request to update Pp_user : {}", pp_userDTO);
        if (pp_userDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pp_userDTO result = pp_userService.save(pp_userDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pp_userDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pp-users} : get all the pp_users.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pp_users in body.
     */
    @GetMapping("/pp-users")
    public ResponseEntity<List<Pp_userDTO>> getAllPp_users(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Pp_users");
        Page<Pp_userDTO> page = pp_userService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pp-users/:id} : get the "id" pp_user.
     *
     * @param id the id of the pp_userDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pp_userDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pp-users/{id}")
    public ResponseEntity<Pp_userDTO> getPp_user(@PathVariable Long id) {
        log.debug("REST request to get Pp_user : {}", id);
        Optional<Pp_userDTO> pp_userDTO = pp_userService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pp_userDTO);
    }

    /**
     * {@code DELETE  /pp-users/:id} : delete the "id" pp_user.
     *
     * @param id the id of the pp_userDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pp-users/{id}")
    public ResponseEntity<Void> deletePp_user(@PathVariable Long id) {
        log.debug("REST request to delete Pp_user : {}", id);
        pp_userService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
