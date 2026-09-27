package se331.lab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se331.lab.entity.AuctionItem;

import java.math.BigDecimal;

public interface AuctionItemRepository extends JpaRepository<AuctionItem, Long> {
    Page<AuctionItem> findByDescriptionContainingIgnoreCaseAndTypeContainingIgnoreCase(
            String description, String type, Pageable pageable);

    @Query("""
            select item from AuctionItem item
            join item.successfulBid winner
            where lower(item.description) like lower(concat('%', :description, '%'))
              and lower(item.type) like lower(concat('%', :type, '%'))
              and winner.amount < :maxAmount
            """)
    Page<AuctionItem> findWithSuccessfulBidBelow(
            @Param("description") String description,
            @Param("type") String type,
            @Param("maxAmount") BigDecimal maxAmount,
            Pageable pageable);
}
