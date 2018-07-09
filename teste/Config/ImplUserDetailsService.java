package com.appsecurity.Config;

import com.appsecurity.Entity.User;
import com.appsecurity.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class ImplUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginService ur;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException, Error {
        User user = null;
        try {
            user = ur.getUserByLogin(s);
        } catch (Exception ex) {
            throw new Error("Não foi possível autenticar o usuário\n" + ex.getMessage());
        }
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return user;
    }
}
