package se331.lab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se331.lab.dto.AuctionItemDto;
import se331.lab.repository.AuctionItemRepository;

import java.math.BigDecimal;

@Service
public class AuctionItemService {
    private final AuctionItemRepository repository;

    public AuctionItemService(AuctionItemRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<AuctionItemDto> search(
            String description, String type, BigDecimal maxSuccessfulBid, int page, int size) {
        String descriptionFilter = description == null ? "" : description.trim();
        String typeFilter = type == null ? "" : type.trim();
        PageRequest pageable = PageRequest.of(page - 1, size);

        if (maxSuccessfulBid != null) {
            return repository.findWithSuccessfulBidBelow(
                    descriptionFilter, typeFilter, maxSuccessfulBid, pageable).map(AuctionItemDto::from);
        }
        return repository.findByDescriptionContainingIgnoreCaseAndTypeContainingIgnoreCase(
                descriptionFilter, typeFilter, pageable).map(AuctionItemDto::from);
    }
}
