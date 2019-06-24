package com.microsample.myapp.service;

import com.microsample.myapp.service.dto.Pp_userDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.microsample.myapp.domain.Pp_user}.
 */
public interface Pp_userService {

    /**
     * Save a pp_user.
     *
     * @param pp_userDTO the entity to save.
     * @return the persisted entity.
     */
    Pp_userDTO save(Pp_userDTO pp_userDTO);

    /**
     * Get all the pp_users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pp_userDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pp_user.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pp_userDTO> findOne(Long id);

    /**
     * Delete the "id" pp_user.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
