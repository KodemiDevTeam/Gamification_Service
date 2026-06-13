package com.gamification.streaks.service;

import com.gamification.streaks.dto.XpTransactionDto;
import com.gamification.streaks.model.XpTransaction;

import java.util.List;

public interface XpTransactionService {
    String createXpTransaction(XpTransactionDto xpTransactionDto);
    String create(XpTransaction xpTransaction);
    List<XpTransactionDto> getAllXpTransaction();
    String update(String xpTransactionId,XpTransaction xpTransaction);
    String deleteXpTransaction(String xpTransactionId);
}
