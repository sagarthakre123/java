package com.epam.jmp.application;

import com.epam.jmp.bank.Bank;
import com.epam.jmp.cloud.bank.impl.CentralBank;
import com.epam.jmp.cloud.bank.impl.InvestmentBank;
import com.epam.jmp.cloud.bank.impl.RetailBank;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.util.ServiceLoader;

public class Application {
    public static void main(String[] args) {
        var user1 = new User("Alice", "Smith", LocalDate.of(2000, 3, 15));
        var user2 = new User("Bob", "Jones", LocalDate.of(2010, 5, 20));
        var user3 = new User("Charlie", "Brown", LocalDate.of(1995, 8, 1));

        Bank retailBank = new RetailBank();
        Bank investmentBank = new InvestmentBank();
        Bank centralBank = new CentralBank();

        BankCard creditCard = retailBank.createBankCard(user1, BankCardType.CREDIT);
        BankCard debitCard = investmentBank.createBankCard(user2, BankCardType.DEBIT);
        BankCard anotherCard = centralBank.createBankCard(user3, BankCardType.CREDIT);

        Service service = ServiceLoader.load(Service.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No Service implementation found"));

        service.subscribe(creditCard);
        service.subscribe(debitCard);
        service.subscribe(anotherCard);

        System.out.println("Users: " + service.getAllUsers());
        System.out.println("Average age: " + service.getAverageUsersAge());
        System.out.println("Is Alice payable? " + Service.isPayableUser(user1));
        System.out.println("Is Bob payable? " + Service.isPayableUser(user2));

        var subscription = service.getSubscriptionByBankCardNumber(creditCard.getNumber());
        System.out.println("Subscription for credit card: " + subscription.orElse(null));

        var filtered = service.getAllSubscriptionsByCondition(s -> s.getStartDate().isAfter(LocalDate.now().minusDays(30)));
        System.out.println("Filtered subscriptions: " + filtered);
    }
}
