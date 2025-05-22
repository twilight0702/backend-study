package com.twilight.springbootdemo.repository;

import com.twilight.springbootdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 可添加自定义查询方法，如 findByName(String name)
}

