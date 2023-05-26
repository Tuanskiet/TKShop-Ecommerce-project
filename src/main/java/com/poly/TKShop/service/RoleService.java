package com.poly.TKShop.service;

import com.poly.TKShop.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);
}
