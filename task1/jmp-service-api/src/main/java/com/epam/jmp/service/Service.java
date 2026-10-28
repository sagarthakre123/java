package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Service {
    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber);

    List<User> getAllUsers();

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);

    default double getAverageUsersAge() {
        return getAllUsers().stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getBirthday() != null)
                .mapToLong(user -> ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()))
                .average()
                .orElse(0.0);
    }

    static boolean isPayableUser(User user) {
        return user != null
                && user.getBirthday() != null
                && ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()) > 18;
    }
}
