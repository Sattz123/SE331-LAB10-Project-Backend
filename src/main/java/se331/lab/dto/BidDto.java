package se331.lab.dto;

import se331.lab.entity.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BidDto(Long id, BigDecimal amount, LocalDateTime datetime) {
    public static BidDto from(Bid bid) {
        return new BidDto(bid.getId(), bid.getAmount(), bid.getDatetime());
    }
}
