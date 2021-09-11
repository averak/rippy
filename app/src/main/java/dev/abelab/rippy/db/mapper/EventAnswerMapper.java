package dev.abelab.rippy.db.mapper;

import java.util.List;

import dev.abelab.rippy.db.mapper.base.EventAnswerBaseMapper;
import dev.abelab.rippy.db.entity.join.EventAnswerWithUser;
import dev.abelab.rippy.db.entity.join.EventAnswerWithDates;
import dev.abelab.rippy.db.entity.join.EventAnswerWithUserAndDates;

public interface EventAnswerMapper extends EventAnswerBaseMapper {

    public List<EventAnswerWithUser> selectWithUserByEventId(final int eventId);

    public List<EventAnswerWithDates> selectWithDatesByEventId(final int eventId);

    public List<EventAnswerWithUserAndDates> selectWithUserAndDatesByEventId(final int eventId);

}
