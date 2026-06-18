package com.gamification.streaks.service.Impl;
import com.gamification.streaks.dto.XpTransactionDto;
import com.gamification.streaks.model.XpTransaction;
import com.gamification.streaks.repository.XpTransactionRepository;
import com.gamification.streaks.service.XpTransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class XpTransactionServiceImpl implements XpTransactionService {

    private final XpTransactionRepository xpTransactionRepository;

    public XpTransactionServiceImpl(XpTransactionRepository xpTransactionRepository){
        this.xpTransactionRepository = xpTransactionRepository;
    }

    public String createXpTransaction(XpTransactionDto xpTransactionDto){
        XpTransaction transaction = new XpTransaction();
        transaction.setXpTransactionId(UUID.randomUUID().toString());
        transaction.setUserId(xpTransactionDto.getUserId());
        transaction.setXpAmount(xpTransactionDto.getXpAmount());
        transaction.setXpSource(xpTransactionDto.getXpSource());
        transaction.setReferenceId(xpTransactionDto.getReferenceId());
        transaction.setDescription(xpTransactionDto.getDescription());
        transaction.setCreatedAt(xpTransactionDto.getCreatedAt() != null ? xpTransactionDto.getCreatedAt() : LocalDateTime.now());
        xpTransactionRepository.save(transaction);
        return "XP Transaction Created Successfully";
    }

    public String create(XpTransaction xpTransaction){
        xpTransaction.setXpTransactionId(UUID.randomUUID().toString());
        if (xpTransaction.getCreatedAt() == null) xpTransaction.setCreatedAt(LocalDateTime.now());
        xpTransactionRepository.save(xpTransaction);
        return "XP Transaction Created Successfully";
    }

    public List<XpTransactionDto> getAllXpTransaction(){
        List<XpTransaction> transactions = xpTransactionRepository.findAll();
        List<XpTransactionDto> dtoList = new ArrayList<>();
        for(XpTransaction transaction : transactions){
            dtoList.add(mapToDto(transaction));
        }
        return dtoList;
    }

    public String update(String xpTransactionId, XpTransaction xpTransaction){
        XpTransaction existing = xpTransactionRepository.findById(xpTransactionId);
        if(existing == null){
            throw new RuntimeException("XP Transaction Not Found");
        }
        existing.setUserId(xpTransaction.getUserId());
        existing.setXpAmount(xpTransaction.getXpAmount());
        existing.setXpSource(xpTransaction.getXpSource());
        existing.setReferenceId(xpTransaction.getReferenceId());
        existing.setDescription(xpTransaction.getDescription());
        xpTransactionRepository.save(existing);
        return "XP Transaction Updated Successfully";
    }

    public String deleteXpTransaction(String xpTransactionId){
        XpTransaction transaction = xpTransactionRepository.findById(xpTransactionId);
        if(transaction == null){
            throw new RuntimeException("XP Transaction Not Found");
        }
        xpTransactionRepository.delete(xpTransactionId);
        return "XP Transaction Deleted Successfully";
    }

    private XpTransactionDto mapToDto(XpTransaction transaction){
        XpTransactionDto dto = new XpTransactionDto();
        dto.setXpTransactionId(transaction.getXpTransactionId());
        dto.setUserId(transaction.getUserId());
        dto.setXpAmount(transaction.getXpAmount());
        dto.setXpSource(transaction.getXpSource());
        dto.setReferenceId(transaction.getReferenceId());
        dto.setDescription(transaction.getDescription());
        dto.setCreatedAt(transaction.getCreatedAt());
        return dto;
    }

    @Override
    public List<XpTransactionDto> getXpTransactionsByUserId(String userId) {
        List<XpTransaction> transactions = xpTransactionRepository.findByUserId(userId);
        List<XpTransactionDto> dtoList = new ArrayList<>();
        for(XpTransaction transaction : transactions){
            dtoList.add(mapToDto(transaction));
        }
        return dtoList;
    }
}