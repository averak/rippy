package dev.abelab.rippy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.db.entity.UserExample;
import dev.abelab.rippy.db.mapper.UserMapper;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.ConflictException;
import dev.abelab.rippy.exception.NotFoundException;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final UserMapper userMapper;

    /**
     * ユーザ一覧を取得
     *
     * @return ユーザ一覧
     */
    public List<User> selectAll() {
        final var userExample = new UserExample();
        userExample.setOrderByClause("updated_at desc");
        return this.userMapper.selectByExample(userExample);
    }

    /**
     * ユーザを作成
     *
     * @param user ユーザ
     *
     * @return ユーザID
     */
    public int insert(final User user) {
        if (this.existsByEmail(user.getEmail())) {
            throw new ConflictException(ErrorCode.CONFLICT_EMAIL);
        }
        return this.userMapper.insertSelective(user);
    }

    /**
     * IDからユーザを検索
     *
     * @param userId ユーザID
     *
     * @return ユーザ
     */
    public User selectById(final int userId) {
        return Optional.ofNullable(this.userMapper.selectByPrimaryKey(userId)) //
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
    }

    /**
     * メールアドレスからユーザを検索
     *
     * @param email メールアドレス
     *
     * @return ユーザ
     */
    public User selectByEmail(final String email) {
        final var example = new UserExample();
        example.createCriteria().andEmailEqualTo(email);
        return this.userMapper.selectByExample(example).stream().findFirst() //
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
    }

    /**
     * emailの存在確認
     *
     * @param email email
     *
     * @return emailが存在するか
     */
    public boolean existsByEmail(final String email) {
        try {
            this.selectByEmail(email);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
