package petcare.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import petcare.repository.DiscoveryRepository;
import petcare.service.DiscoveryQueryService;
import petcare.service.DiscoveryService;
import petcare.service.criteria.DiscoveryCriteria;
import petcare.service.dto.DiscoveryDTO;
import petcare.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link petcare.domain.Discovery}.
 */
@RestController
@RequestMapping("/api/discoveries")
public class DiscoveryResource {

    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryResource.class);

    private static final String ENTITY_NAME = "discovery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscoveryService discoveryService;

    private final DiscoveryRepository discoveryRepository;

    private final DiscoveryQueryService discoveryQueryService;

    public DiscoveryResource(
        DiscoveryService discoveryService,
        DiscoveryRepository discoveryRepository,
        DiscoveryQueryService discoveryQueryService
    ) {
        this.discoveryService = discoveryService;
        this.discoveryRepository = discoveryRepository;
        this.discoveryQueryService = discoveryQueryService;
    }

    /**
     * {@code POST  /discoveries} : Create a new discovery.
     *
     * @param discoveryDTO the discoveryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discoveryDTO, or with status {@code 400 (Bad Request)} if the discovery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DiscoveryDTO> createDiscovery(@Valid @RequestBody DiscoveryDTO discoveryDTO) throws URISyntaxException {
        LOG.debug("REST request to save Discovery : {}", discoveryDTO);
        if (discoveryDTO.getId() != null) {
            throw new BadRequestAlertException("A new discovery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        discoveryDTO = discoveryService.save(discoveryDTO);
        return ResponseEntity.created(new URI("/api/discoveries/" + discoveryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, discoveryDTO.getId().toString()))
            .body(discoveryDTO);
    }

    /**
     * {@code PUT  /discoveries/:id} : Updates an existing discovery.
     *
     * @param id the id of the discoveryDTO to save.
     * @param discoveryDTO the discoveryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discoveryDTO,
     * or with status {@code 400 (Bad Request)} if the discoveryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discoveryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DiscoveryDTO> updateDiscovery(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DiscoveryDTO discoveryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Discovery : {}, {}", id, discoveryDTO);
        if (discoveryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, discoveryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!discoveryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        discoveryDTO = discoveryService.update(discoveryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, discoveryDTO.getId().toString()))
            .body(discoveryDTO);
    }

    /**
     * {@code PATCH  /discoveries/:id} : Partial updates given fields of an existing discovery, field will ignore if it is null
     *
     * @param id the id of the discoveryDTO to save.
     * @param discoveryDTO the discoveryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discoveryDTO,
     * or with status {@code 400 (Bad Request)} if the discoveryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the discoveryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the discoveryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiscoveryDTO> partialUpdateDiscovery(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DiscoveryDTO discoveryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Discovery partially : {}, {}", id, discoveryDTO);
        if (discoveryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, discoveryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!discoveryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiscoveryDTO> result = discoveryService.partialUpdate(discoveryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, discoveryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /discoveries} : get all the discoveries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discoveries in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DiscoveryDTO>> getAllDiscoveries(
        DiscoveryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Discoveries by criteria: {}", criteria);

        Page<DiscoveryDTO> page = discoveryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /discoveries/count} : count all the discoveries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDiscoveries(DiscoveryCriteria criteria) {
        LOG.debug("REST request to count Discoveries by criteria: {}", criteria);
        return ResponseEntity.ok().body(discoveryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /discoveries/:id} : get the "id" discovery.
     *
     * @param id the id of the discoveryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discoveryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiscoveryDTO> getDiscovery(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Discovery : {}", id);
        Optional<DiscoveryDTO> discoveryDTO = discoveryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discoveryDTO);
    }

    /**
     * {@code DELETE  /discoveries/:id} : delete the "id" discovery.
     *
     * @param id the id of the discoveryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscovery(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Discovery : {}", id);
        discoveryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
