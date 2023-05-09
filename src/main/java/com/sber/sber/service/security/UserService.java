package com.sber.sber.service.security;

import com.sber.sber.entity.Human;
import com.sber.sber.entity.security.Role;
import com.sber.sber.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private HumanRepository humanRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Human human = humanRepository.findHumanByEmail(email);
        if (human == null) {
            throw new UsernameNotFoundException("Человек с таким именем не обнаружен!");
        }
        return new User(human.getEmail(), human.getPassword(), mapRoleToGrantedAuthority(human.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRoleToGrantedAuthority(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
