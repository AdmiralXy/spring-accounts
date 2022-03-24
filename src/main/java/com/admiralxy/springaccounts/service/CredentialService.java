package com.admiralxy.springaccounts.service;

import com.admiralxy.springaccounts.entity.Credential;
import com.admiralxy.springaccounts.entity.User;
import com.admiralxy.springaccounts.repository.CredentialRepository;
import com.admiralxy.springaccounts.service.interfaces.ICredentialService;
import com.admiralxy.springaccounts.util.AuthUtils;
import com.admiralxy.springaccounts.util.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CredentialService implements ICredentialService {
    private final EncryptionUtils encryptionUtils;
    private final CredentialRepository credentialRepository;
    private final AuthUtils authUtils;

    @Autowired
    public CredentialService(EncryptionUtils encryptionUtils, CredentialRepository credentialRepository, AuthUtils authUtils) {
        this.encryptionUtils = encryptionUtils;
        this.credentialRepository = credentialRepository;
        this.authUtils = authUtils;
    }

    @Override
    public List<Credential> getAll() {
        return credentialRepository.findAll();
    }

    @Override
    public Credential findById(Long id, String key) throws Exception {
        Credential credential = credentialRepository.findById(id).orElse(null);
        if (credential != null && key != null) {
            String decrypted = encryptionUtils.decode(credential.getPassword(), key);
            credential.setPassword(decrypted);
            credential.setSecurityKey(key);
            credential.setDecrypted(true);
        }
        return credential;
    }

    @Override
    public void delete(Long id) {
        credentialRepository.deleteById(id);
    }

    @Override
    public Credential save(Credential credential) throws Exception {
        String encrypted = encryptionUtils.encode(
                credential.getPassword(),
                credential.getSecurityKey()
        );
        credential.setUser(authUtils.getCurrentUser());
        credential.setPassword(encrypted);
        return this.credentialRepository.save(credential);
    }

    @Override
    public List<Credential> getAllByUser(User user) {
        return credentialRepository.getAllByUser(user);
    }
}