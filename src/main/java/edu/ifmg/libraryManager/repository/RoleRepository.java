package edu.ifmg.libraryManager.repository;

import edu.ifmg.libraryManager.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByAuthority(String authority);
}
