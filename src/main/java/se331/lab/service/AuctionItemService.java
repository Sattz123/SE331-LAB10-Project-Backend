package se331.lab.service;

import org.springframework.data.domain.Page;
import se331.lab.entity.AuctionItemDTO;

import java.math.BigDecimal;

public interface AuctionItemService {
    Page<AuctionItemDTO> getAuctionItems(
            String description, String type, BigDecimal maxSuccessfulBid, Integer pageSize, Integer page);
}
