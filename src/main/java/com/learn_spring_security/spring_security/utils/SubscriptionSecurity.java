package com.learn_spring_security.spring_security.utils;

import com.learn_spring_security.spring_security.entity.UserEntity;
import com.learn_spring_security.spring_security.entity.enums.Subscription;
import com.learn_spring_security.spring_security.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionSecurity {

    public boolean isValidBasicPlan() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new ResourceNotFoundException("User not found!");
        }
        return user.getSubscription().name().equals(Subscription.BASIC.name());
    }

    public boolean isValidPremiumPlan() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new ResourceNotFoundException("User not found!");
        }
        return user.getSubscription().name().equals(Subscription.PREMIUM.name());
    }
}
