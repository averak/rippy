package dev.abelab.rippy.db.mapper;

import java.util.List;

import dev.abelab.rippy.db.mapper.base.EventBaseMapper;
import dev.abelab.rippy.db.entity.join.EventWithDates;

public interface EventMapper extends EventBaseMapper {

    List<EventWithDates> selectAllWithDates();

    EventWithDates selectWithDatesById(final int dd);

}
