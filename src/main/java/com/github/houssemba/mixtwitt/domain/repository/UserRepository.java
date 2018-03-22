package com.github.houssemba.mixtwitt.domain.repository;

import com.github.houssemba.mixtwitt.domain.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByLogin(String login);
}
