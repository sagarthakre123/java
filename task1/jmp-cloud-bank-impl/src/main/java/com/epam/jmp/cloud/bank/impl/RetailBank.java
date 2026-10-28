package com.epam.jmp.cloud.bank.impl;

import com.epam.jmp.bank.Bank;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.dto.User;

import java.util.Map;
import java.util.function.Function;

public class RetailBank implements Bank {
    private static final Map<BankCardType, Function<User, BankCard>> CARD_CREATORS = Map.of(
            BankCardType.CREDIT, CreditBankCard::new,
            BankCardType.DEBIT, DebitBankCard::new
    );

    @Override
    public BankCard createBankCard(User user, BankCardType type) {
        var creator = CARD_CREATORS.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unsupported bank card type: " + type);
        }
        return creator.apply(user);
    }
}
