package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
