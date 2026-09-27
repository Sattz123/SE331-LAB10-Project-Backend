package se331.lab.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.lab.entity.AuctionItem;
import se331.lab.repository.AuctionItemRepository;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
@Profile("db")
public class AuctionItemDaoImpl implements AuctionItemDao {
    final AuctionItemRepository auctionItemRepository;

    @Override
    public Page<AuctionItem> getAuctionItems(
            String description, String type, BigDecimal maxSuccessfulBid, Pageable pageable) {
        if (maxSuccessfulBid != null) {
            return auctionItemRepository.findWithSuccessfulBidBelow(
                    description, type, maxSuccessfulBid, pageable);
        }
        return auctionItemRepository.findByDescriptionContainingIgnoreCaseAndTypeContainingIgnoreCase(
                description, type, pageable);
    }
}
