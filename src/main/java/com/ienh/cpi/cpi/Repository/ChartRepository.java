package com.ienh.cpi.cpi.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ienh.cpi.cpi.Models.UserChart;

public interface ChartRepository extends JpaRepository<UserChart, Integer> {

    // find by ticker and user id
    UserChart findByTickerAndUserId(String ticker, int userId);

    // find all by ticker and user id
    List<UserChart> findAllByTickerAndUserId(String ticker, int userId);
}
