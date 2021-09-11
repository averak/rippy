package dev.abelab.rippy.db.mapper;

import java.util.List;

import dev.abelab.rippy.db.mapper.base.UserBaseMapper;
import dev.abelab.rippy.db.entity.join.UserWithDates;

public interface UserMapper extends UserBaseMapper {

    List<UserWithDates> selectWithDatesByEventId(final int eventId);

}
