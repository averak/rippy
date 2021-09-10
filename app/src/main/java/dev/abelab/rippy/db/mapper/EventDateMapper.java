package dev.abelab.rippy.db.mapper;

import java.util.List;

import dev.abelab.rippy.db.mapper.base.EventDateBaseMapper;
import dev.abelab.rippy.db.entity.EventDate;

public interface EventDateMapper extends EventDateBaseMapper {

    void bulkInsert(List<EventDate> eventDates);

}
