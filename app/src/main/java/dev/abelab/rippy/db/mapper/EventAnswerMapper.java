package dev.abelab.rippy.db.mapper;

import java.util.List;

import dev.abelab.rippy.db.mapper.base.EventAnswerBaseMapper;
import dev.abelab.rippy.db.entity.join.EventAnswerWithDates;

public interface EventAnswerMapper extends EventAnswerBaseMapper {

    public List<EventAnswerWithDates> selectWithDatesByEventId(final int eventId);

}
