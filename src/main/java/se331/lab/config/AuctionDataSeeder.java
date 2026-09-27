package se331.lab.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se331.lab.entity.AuctionItem;
import se331.lab.entity.Bid;
import se331.lab.repository.AuctionItemRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class AuctionDataSeeder implements ApplicationRunner {
    private final AuctionItemRepository repository;

    public AuctionDataSeeder(AuctionItemRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (repository.count() > 0) {
            return;
        }

        createItem("Vintage camera", "Electronics", new String[] {"80.00", "100.00", "120.00"}, 2);
        createItem("Oak desk", "Furniture", new String[] {"45.00", "60.00", "70.00"}, 2);
        createItem("Road bicycle", "Sports", new String[] {"90.00", "130.00", "150.00"}, 2);
        createItem("Landscape painting", "Art", new String[] {"40.00", "55.00", "65.00"}, null);
        createItem("Laptop computer", "Electronics", new String[] {"200.00", "230.00", "260.00"}, null);
    }

    private void createItem(String description, String type, String[] amounts, Integer winnerIndex) {
        AuctionItem item = new AuctionItem(description, type);
        LocalDateTime firstBidTime = LocalDateTime.of(2026, 9, 1, 9, 0);
        for (int i = 0; i < amounts.length; i++) {
            item.addBid(new Bid(new BigDecimal(amounts[i]), firstBidTime.plusHours(i)));
        }
        repository.saveAndFlush(item);
        if (winnerIndex != null) {
            item.setSuccessfulBid(item.getBids().get(winnerIndex));
        }
    }
}
