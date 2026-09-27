package se331.lab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se331.lab.dao.AuctionItemDao;
import se331.lab.entity.AuctionItemDTO;
import se331.lab.util.LabMapper;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuctionItemServiceImpl implements AuctionItemService {
    final AuctionItemDao auctionItemDao;

    @Override
    @Transactional(readOnly = true)
    public Page<AuctionItemDTO> getAuctionItems(
            String description, String type, BigDecimal maxSuccessfulBid, Integer pageSize, Integer page) {
        description = description == null ? "" : description.trim();
        type = type == null ? "" : type.trim();
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return auctionItemDao.getAuctionItems(description, type, maxSuccessfulBid, pageRequest)
                .map(LabMapper.INSTANCE::getAuctionItemDto);
    }
}
