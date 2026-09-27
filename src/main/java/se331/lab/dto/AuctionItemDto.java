package se331.lab.dto;

import se331.lab.entity.AuctionItem;

import java.util.List;

public record AuctionItemDto(
        Long id,
        String description,
        String type,
        List<BidDto> bids,
        BidDto successfulBid) {
    public static AuctionItemDto from(AuctionItem item) {
        return new AuctionItemDto(
                item.getId(),
                item.getDescription(),
                item.getType(),
                item.getBids().stream().map(BidDto::from).toList(),
                item.getSuccessfulBid() == null ? null : BidDto.from(item.getSuccessfulBid()));
    }
}
