    package com.dika.userApps.repository;

    import com.dika.userApps.entity.User;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface UserRepository extends JpaRepository<User, String>{
    }