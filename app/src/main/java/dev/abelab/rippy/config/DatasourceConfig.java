package dev.abelab.rippy.config;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.repository.UserRepository;
import dev.abelab.rippy.logic.UserLogic;
import dev.abelab.rippy.property.RippyProperty;
import dev.abelab.rippy.enums.UserRoleEnum;

@Profile("prod | local")
@Configuration
public class DatasourceConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserLogic userLogic;

    @Autowired
    RippyProperty rippyProperty;

    @PostConstruct
    public void createDefaultAdmin() {
        // 管理者アカウントが既に存在する
        if (this.userRepository.existsByEmail(this.rippyProperty.getAdmin().getEmail())) {
            return;
        }

        // 管理者アカウントを作成
        final var adminUser = User.builder() //
            .firstName(this.rippyProperty.getAdmin().getFirstName()) //
            .lastName(this.rippyProperty.getAdmin().getLastName()) //
            .email(this.rippyProperty.getAdmin().getEmail()) //
            .password(this.userLogic.encodePassword(this.rippyProperty.getAdmin().getPassword())) //
            .roleId(UserRoleEnum.ADMIN.getId()) //
            .admissionYear(this.rippyProperty.getAdmin().getAdmissionYear()) //
            .build();
        this.userRepository.insert(adminUser);
    }

}
