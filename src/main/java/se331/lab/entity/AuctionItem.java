package se331.lab.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "auction_items")
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;
    String description;
    String type;

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<Bid> bids = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "successful_bid_id", unique = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Bid successfulBid;

    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setItem(this);
    }

    public void setSuccessfulBid(Bid bid) {
        if (bid != null && bids.stream().noneMatch(existing -> existing == bid)) {
            throw new IllegalArgumentException("The successful bid must belong to this item");
        }
        successfulBid = bid;
    }
}
