package com.poly.TKShop.service.impl;

import com.poly.TKShop.entity.Role;
import com.poly.TKShop.entity.User;
import com.poly.TKShop.exception.RoleException;
import com.poly.TKShop.repository.RoleRepository;
import com.poly.TKShop.repository.UserRepository;
import com.poly.TKShop.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository  userRepository;
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role newRole(Role role) {
        Optional<Role> findRole =  roleRepository.findByName(role.getName());
        if(findRole.isPresent()){
            log.info("Role name {} already exist", role.getName());
            return null;
        }
        log.info("Saving role {}", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleByUser(String username, String roleName) {
        log.info("Adding role {} to user {}",roleName, username );
        Optional<Role> findRole =  roleRepository.findByName(roleName);
        Optional<User> user = userRepository.findByUsername(username);
        user.get().getRoles().add(findRole.get());
        userRepository.save(user.get());
    }
}
