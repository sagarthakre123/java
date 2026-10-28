package com.epam.jmp.dto;

public class CreditBankCard extends BankCard {
    private static long counter = 1;

    public CreditBankCard() {
        super();
    }

    public CreditBankCard(User user) {
        super("CREDIT-" + counter++, user);
    }
}
