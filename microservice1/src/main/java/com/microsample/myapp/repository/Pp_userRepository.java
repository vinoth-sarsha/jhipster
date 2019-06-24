package com.microsample.myapp.repository;

import com.microsample.myapp.domain.Pp_user;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pp_user entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Pp_userRepository extends JpaRepository<Pp_user, Long> {

}
