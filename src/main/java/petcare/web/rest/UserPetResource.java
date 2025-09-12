package petcare.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import petcare.domain.UserPet;
import petcare.repository.UserPetRepository;
import petcare.service.UserPetQueryService;
import petcare.service.UserPetService;
import petcare.service.criteria.UserPetCriteria;
import petcare.service.dto.LoginRequest;
import petcare.service.dto.UserPetDTO;
import petcare.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link petcare.domain.UserPet}.
 */
@RestController
@RequestMapping("/api/user-pets")
public class UserPetResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserPetResource.class);

    private static final String ENTITY_NAME = "userPet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserPetService userPetService;

    private final UserPetRepository userPetRepository;

    private final UserPetQueryService userPetQueryService;

    private final PasswordEncoder passwordEncoder;

    public UserPetResource(
        UserPetService userPetService,
        UserPetRepository userPetRepository,
        UserPetQueryService userPetQueryService,
        PasswordEncoder passwordEncoder
    ) {
        this.userPetService = userPetService;
        this.userPetRepository = userPetRepository;
        this.userPetQueryService = userPetQueryService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@code POST  /user-pets} : Create a new userPet.
     *
     * @param userPetDTO the userPetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPetDTO, or with status {@code 400 (Bad Request)} if the userPet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UserPetDTO> createUserPet(@Valid @RequestBody UserPetDTO userPetDTO) throws URISyntaxException {
        LOG.debug("REST request to save UserPet : {}", userPetDTO);
        if (userPetDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userPetDTO = userPetService.save(userPetDTO);
        return ResponseEntity.created(new URI("/api/user-pets/" + userPetDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, userPetDTO.getId().toString()))
            .body(userPetDTO);
    }

    /**
     * {@code PUT  /user-pets/:id} : Updates an existing userPet.
     *
     * @param id the id of the userPetDTO to save.
     * @param userPetDTO the userPetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPetDTO,
     * or with status {@code 400 (Bad Request)} if the userPetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserPetDTO> updateUserPet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserPetDTO userPetDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update UserPet : {}, {}", id, userPetDTO);
        if (userPetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userPetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userPetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        userPetDTO = userPetService.update(userPetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userPetDTO.getId().toString()))
            .body(userPetDTO);
    }

    /**
     * {@code PATCH  /user-pets/:id} : Partial updates given fields of an existing userPet, field will ignore if it is null
     *
     * @param id the id of the userPetDTO to save.
     * @param userPetDTO the userPetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPetDTO,
     * or with status {@code 400 (Bad Request)} if the userPetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userPetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userPetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserPetDTO> partialUpdateUserPet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserPetDTO userPetDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update UserPet partially : {}, {}", id, userPetDTO);
        if (userPetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userPetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userPetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserPetDTO> result = userPetService.partialUpdate(userPetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userPetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-pets} : get all the userPets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPets in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UserPetDTO>> getAllUserPets(
        UserPetCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get UserPets by criteria: {}", criteria);

        Page<UserPetDTO> page = userPetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-pets/count} : count all the userPets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countUserPets(UserPetCriteria criteria) {
        LOG.debug("REST request to count UserPets by criteria: {}", criteria);
        return ResponseEntity.ok().body(userPetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-pets/:id} : get the "id" userPet.
     *
     * @param id the id of the userPetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserPetDTO> getUserPet(@PathVariable("id") Long id) {
        LOG.debug("REST request to get UserPet : {}", id);
        Optional<UserPetDTO> userPetDTO = userPetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPetDTO);
    }

    /**
     * {@code DELETE  /user-pets/:id} : delete the "id" userPet.
     *
     * @param id the id of the userPetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPet(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete UserPet : {}", id);
        userPetService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserPetDTO> register(@Valid @RequestBody UserPetDTO userPetDTO) {
        LOG.debug("REST request to register UserPet : {}", userPetDTO);
        UserPetDTO result = userPetService.register(userPetDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Optional<UserPet> userOpt = userPetRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email không tồn tại!"));
        }

        UserPet userPet = userOpt.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), userPet.getPasswordHash())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Sai mật khẩu!"));
        }

        Map<String, Object> result = Map.of(
            "id", userPet.getId(),
            "email", userPet.getEmail(),
            "name", userPet.getName(),
            "message", "Đăng nhập thành công!"
        );

        return ResponseEntity.ok(result);
    }

}
