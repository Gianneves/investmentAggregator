package com.gianpneves.investmentaggregator.service;

import com.gianpneves.investmentaggregator.controller.dtos.AccountResponseDTO;
import com.gianpneves.investmentaggregator.controller.dtos.CreateAccountDTO;
import com.gianpneves.investmentaggregator.controller.dtos.CreateUserDTO;
import com.gianpneves.investmentaggregator.controller.dtos.UpdateUserDTO;
import com.gianpneves.investmentaggregator.entity.Account;
import com.gianpneves.investmentaggregator.entity.BillingAddress;
import com.gianpneves.investmentaggregator.entity.User;
import com.gianpneves.investmentaggregator.repository.AccountRepository;
import com.gianpneves.investmentaggregator.repository.BillingAddressRepository;
import com.gianpneves.investmentaggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BillingAddressRepository billingAddressRepository;

    public UUID createUser(CreateUserDTO createUserDTO) {

        var entity = new User(
                UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password()
        );
        var userSaved = repository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return repository.findById(UUID.fromString(userId));
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDTO userDTO) {
        var id = UUID.fromString(userId);
        var userEntity = repository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (userDTO.username() != null) {
                user.setUsername(userDTO.username());
            }

            if (userDTO.password() != null) {
                user.setUsername(userDTO.password());
            }
            repository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);
        var userExists = repository.existsById(id);

        if (userExists) {
            repository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDTO createAccountDTO) {
         var user = repository.findById(UUID.fromString(userId))
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

         var account = new Account(
                 UUID.randomUUID(),
                 user,
                 null,
                 createAccountDTO.description(),
                 new ArrayList<>()
         );

         var accountCreated = accountRepository.save(account);

         var billingAddress = new BillingAddress(
                 accountCreated.getAccountId(),
                 account,
                 createAccountDTO.street(),
                 createAccountDTO.number()
         );

         billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDTO> listAccounts(String userId) {
        var user = repository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream()
                .map(acc -> new AccountResponseDTO(acc.getAccountId().toString(), acc.getDescription()))
                .toList();
    }
}
