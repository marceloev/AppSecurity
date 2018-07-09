package com.appsecurity.Repository;

import com.appsecurity.Entity.User;

public interface UserRepository {

    User findByLogin(String login);
}
