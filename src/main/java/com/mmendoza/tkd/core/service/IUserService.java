package com.mmendoza.tkd.core.service;

import com.mmendoza.tkd.core.model.User;

public interface IUserService {
    Integer save(User user);

    User findByEmail(String email);

    User findById(Integer id);
}
