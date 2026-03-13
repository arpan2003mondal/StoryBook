package com.company.storybook.repository;

import com.company.storybook.entity.Storybook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorybookRepository extends JpaRepository<Storybook, Long> {
}
