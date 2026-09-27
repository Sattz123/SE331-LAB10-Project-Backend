package se331.lab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.lab.entity.AuctionItem;

import java.math.BigDecimal;

public interface AuctionItemDao {
    Page<AuctionItem> getAuctionItems(
            String description, String type, BigDecimal maxSuccessfulBid, Pageable pageable);
}
