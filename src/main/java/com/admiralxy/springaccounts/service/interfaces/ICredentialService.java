package com.admiralxy.springaccounts.service.interfaces;

import com.admiralxy.springaccounts.entity.Credential;
import com.admiralxy.springaccounts.entity.User;

import java.util.List;

public interface ICredentialService {
    List<Credential> getAll();
    Credential findById(Long id, String key) throws Exception;
    void delete(Long id);
    Credential save(Credential credential) throws Exception;
    List<Credential> getAllByUser(User user);
}
