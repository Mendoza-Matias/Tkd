package com.mmendoza.tkd.core.service;

import com.mmendoza.tkd.core.model.Role;

public interface IRoleService {
    Role findByName(String name);
}
