package com.epam.jmp.cloud.service.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CloudServiceImpl implements Service {
    private final Map<String, Subscription> subscriptions = new ConcurrentHashMap<>();
    private final List<User> users = new ArrayList<>();

    @Override
    public void subscribe(BankCard bankCard) {
        if (bankCard == null || bankCard.getUser() == null) {
            throw new IllegalArgumentException("Bank card and user must be provided");
        }
        var cardNumber = bankCard.getNumber();
        subscriptions.put(cardNumber, new Subscription(cardNumber, LocalDate.now()));
        users.add(bankCard.getUser());
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber) {
        var subscription = subscriptions.values().stream()
                .filter(item -> item.getBankcardNumber().equals(bankCardNumber))
                .findFirst()
                .orElseThrow(() -> new SubscriptionNotFoundException(
                        "There is no subscription for bank card number: " + bankCardNumber));
        return Optional.of(subscription);
    }

    @Override
    public List<User> getAllUsers() {
        return users.stream()
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return subscriptions.values().stream()
                .filter(condition)
                .collect(Collectors.toUnmodifiableList());
    }
}
