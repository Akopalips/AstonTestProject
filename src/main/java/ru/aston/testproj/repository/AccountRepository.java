package ru.aston.testproj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

import ru.aston.testproj.domain.model.Account;

/**
 * @author tuspring
 */
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByName(String name);

}