package com.poly.TKShop.service.impl;

import com.poly.TKShop.entity.Role;
import com.poly.TKShop.repository.RoleRepository;
import com.poly.TKShop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
