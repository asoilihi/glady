package com.backend.as.glady.data.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.as.glady.data.entities.WalletUser;

/**
 * @author asoilihi
 *
 */
public interface WalletRepository extends JpaRepository<WalletUser, BigInteger> {

}
