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

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auction_items")
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String type;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "successful_bid_id", unique = true)
    private Bid successfulBid;

    protected AuctionItem() {
    }

    public AuctionItem(String description, String type) {
        this.description = description;
        this.type = type;
    }

    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setItem(this);
    }

    public void setSuccessfulBid(Bid bid) {
        if (bid != null && !bids.contains(bid)) {
            throw new IllegalArgumentException("The successful bid must belong to this item");
        }
        successfulBid = bid;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public Bid getSuccessfulBid() {
        return successfulBid;
    }
}
