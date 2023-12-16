package com.ienh.cpi.cpi.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ienh.cpi.cpi.Models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM Transaction t WHERE t.userId = :userId AND t.ticker = :ticker")
    List<Transaction> findTransactionsByUserIdAndTicker(@Param("userId") int userId, @Param("ticker") String ticker);

    @Query("SELECT t FROM Transaction t WHERE t.userId = :userId")
    List<Transaction> findTransactionsByUserId(@Param("userId") int userId);

    @Query("SELECT SUM(t.price * t.quantity) / SUM(t.quantity) " +
            "FROM Transaction t " +
            "WHERE t.userId = :userId AND t.ticker = :ticker")
    Double calculateAveragePrice(@Param("userId") int userId, @Param("ticker") String ticker);

    @Query("SELECT " +
            "   t.stockId as stockId, " +
            "   t.ticker as ticker, " +
            "   AVG(t.price) as averagePrice, " +
            "   SUM(t.quantity) as totalQuantity, " +
            "   SUM(t.totalInvested) as totalValue " +
            "FROM Transaction t " +
            "WHERE t.userId = :userId " +
            "GROUP BY t.ticker")
    public abstract List<Object[]> getPortfolioSummary(@Param("userId") int userId);

}
