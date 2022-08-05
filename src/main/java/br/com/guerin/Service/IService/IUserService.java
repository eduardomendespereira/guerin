package br.com.guerin.Service.IService;

import br.com.guerin.Entity.User;

public interface UserService {
    User save(User user);
    User findByEmail(String email);
}