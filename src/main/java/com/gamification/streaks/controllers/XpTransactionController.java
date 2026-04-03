package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.XpTransactionDto;
import com.gamification.streaks.model.XpTransaction;
import com.gamification.streaks.service.XpTransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/xp-transaction")
public class XpTransactionController {

    private final XpTransactionService xpTransactionService;

    public XpTransactionController(XpTransactionService xpTransactionService) {
        this.xpTransactionService = xpTransactionService;
    }

    @PostMapping("/create-dto")
    public String createXpTransaction(@RequestBody XpTransactionDto xpTransactionDto) {
        return xpTransactionService.createXpTransaction(xpTransactionDto);
    }

    @PostMapping("/create")
    public String create(@RequestBody XpTransaction xpTransaction) {
        return xpTransactionService.create(xpTransaction);
    }

    @GetMapping("/all")
    public List<XpTransactionDto> getAllXpTransactions() {
        return xpTransactionService.getAllXpTransaction();
    }

    @PutMapping("/{xpTransactionId}")
    public String update(@PathVariable String xpTransactionId, @RequestBody XpTransaction xpTransaction) {
        return xpTransactionService.update(xpTransactionId, xpTransaction);
    }

    @DeleteMapping("/{xpTransactionId}")
    public String deleteXpTransaction(@PathVariable String xpTransactionId) {
        return xpTransactionService.deleteXpTransaction(xpTransactionId);
    }
}
