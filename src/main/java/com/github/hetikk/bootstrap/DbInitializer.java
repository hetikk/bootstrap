package com.github.hetikk.bootstrap;

import com.github.hetikk.bootstrap.common.model.user.RoleName;
import com.github.hetikk.bootstrap.common.model.user.User;
import com.github.hetikk.bootstrap.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DbInitializer implements CommandLineRunner {

    private final UserService userService;

    @Value("${bootstrap.db.init:false}")
    private boolean needInit = true;

    @Override
    public void run(String... args) {
        if (!needInit) {
            return;
        }

        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.name = "name-" + i;
            user.email = "email-" + i;
            user.password = "password-" + i;
            user.phone = "phone-" + i;

            if (i % 5 == 0) {
                user.roles = Set.of(RoleName.BASE, RoleName.ADVANCED);
            } else {
                user.roles = Set.of(i % 2 == 0 ? RoleName.BASE : RoleName.ADVANCED);
            }

            userService.create(user);
        }
    }

}