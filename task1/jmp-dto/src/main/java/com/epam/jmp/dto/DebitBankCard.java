package com.epam.jmp.dto;

public class DebitBankCard extends BankCard {
    private static long counter = 1;

    public DebitBankCard() {
        super();
    }

    public DebitBankCard(User user) {
        super("DEBIT-" + counter++, user);
    }
}
