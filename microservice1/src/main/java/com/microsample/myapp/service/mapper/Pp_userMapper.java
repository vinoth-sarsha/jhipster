package com.microsample.myapp.service.mapper;

import com.microsample.myapp.domain.*;
import com.microsample.myapp.service.dto.Pp_userDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pp_user} and its DTO {@link Pp_userDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Pp_userMapper extends EntityMapper<Pp_userDTO, Pp_user> {



    default Pp_user fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pp_user pp_user = new Pp_user();
        pp_user.setId(id);
        return pp_user;
    }
}
