package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se331.lab.entity.AuctionItemDTO;
import se331.lab.service.AuctionItemService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction-items")
@CrossOrigin(origins = "http://localhost:5173", exposedHeaders = "x-total-count")
public class AuctionItemController {
    final AuctionItemService auctionItemService;

    @GetMapping
    public ResponseEntity<List<AuctionItemDTO>> getAuctionItems(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) BigDecimal maxSuccessfulBid,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 1 || size < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page and size must be positive");
        }

        Page<AuctionItemDTO> result = auctionItemService.getAuctionItems(
                description, type, maxSuccessfulBid, size, page);
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-total-count", String.valueOf(result.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }
}
