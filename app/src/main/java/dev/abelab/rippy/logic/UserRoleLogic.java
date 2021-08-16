package dev.abelab.rippy.logic;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import lombok.*;
import dev.abelab.rippy.enums.UserRoleEnum;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.NotFoundException;

@RequiredArgsConstructor
@Component
public class UserRoleLogic {

    /**
     * 有効なユーザロールかチェック
     *
     * @param roleId ロールID
     */
    public void checkForValidRoleId(final int roleId) {
        Arrays.stream(UserRoleEnum.values()).filter(e -> e.getId() == roleId) //
            .findFirst().orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER_ROLE));
    }

}
