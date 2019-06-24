package com.microsample.myapp.service.impl;

import com.microsample.myapp.service.Pp_userService;
import com.microsample.myapp.domain.Pp_user;
import com.microsample.myapp.repository.Pp_userRepository;
import com.microsample.myapp.service.dto.Pp_userDTO;
import com.microsample.myapp.service.mapper.Pp_userMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pp_user}.
 */
@Service
@Transactional
public class Pp_userServiceImpl implements Pp_userService {

    private final Logger log = LoggerFactory.getLogger(Pp_userServiceImpl.class);

    private final Pp_userRepository pp_userRepository;

    private final Pp_userMapper pp_userMapper;

    public Pp_userServiceImpl(Pp_userRepository pp_userRepository, Pp_userMapper pp_userMapper) {
        this.pp_userRepository = pp_userRepository;
        this.pp_userMapper = pp_userMapper;
    }

    /**
     * Save a pp_user.
     *
     * @param pp_userDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pp_userDTO save(Pp_userDTO pp_userDTO) {
        log.debug("Request to save Pp_user : {}", pp_userDTO);
        Pp_user pp_user = pp_userMapper.toEntity(pp_userDTO);
        pp_user = pp_userRepository.save(pp_user);
        return pp_userMapper.toDto(pp_user);
    }

    /**
     * Get all the pp_users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Pp_userDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pp_users");
        return pp_userRepository.findAll(pageable)
            .map(pp_userMapper::toDto);
    }


    /**
     * Get one pp_user by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pp_userDTO> findOne(Long id) {
        log.debug("Request to get Pp_user : {}", id);
        return pp_userRepository.findById(id)
            .map(pp_userMapper::toDto);
    }

    /**
     * Delete the pp_user by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pp_user : {}", id);
        pp_userRepository.deleteById(id);
    }
}
