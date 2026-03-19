package com.mmendoza.tkd.core.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mmendoza.tkd.core.exception.BussinesException;
import com.mmendoza.tkd.core.model.Role;
import com.mmendoza.tkd.core.repository.IRoleRepository;
import com.mmendoza.tkd.core.service.IRoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new BussinesException("Rol no encontrado", HttpStatus.NOT_FOUND));
    }
}
