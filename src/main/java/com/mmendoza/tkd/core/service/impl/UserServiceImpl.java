package com.mmendoza.tkd.core.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mmendoza.tkd.core.exception.BussinesException;
import com.mmendoza.tkd.core.model.User;
import com.mmendoza.tkd.core.repository.IUserRepository;
import com.mmendoza.tkd.core.service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;

    @Override
    public Integer save(User user) {
        return iUserRepository.save(user).getId();
    }

    @Override
    public User findByEmail(String email) {
        return iUserRepository.findByEmail(email)
                .orElseThrow(() -> new BussinesException("Usuario no encontrado", HttpStatus.NOT_FOUND));
    }

    @Override
    public User findById(Integer id) {
        return iUserRepository.findById(id)
                .orElseThrow(() -> new BussinesException("Usuario no encontrado", HttpStatus.NOT_FOUND));
    }
}
