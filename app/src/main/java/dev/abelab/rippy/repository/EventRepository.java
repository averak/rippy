package dev.abelab.rippy.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import dev.abelab.rippy.db.entity.Event;
import dev.abelab.rippy.db.entity.EventExample;
import dev.abelab.rippy.db.mapper.EventMapper;

@RequiredArgsConstructor
@Repository
public class EventRepository {

    private final EventMapper eventMapper;

    /**
     * イベント一覧を取得
     *
     * @return イベント一覧
     */
    public List<Event> selectAll() {
        final var eventExample = new EventExample();
        eventExample.setOrderByClause("updated_at desc");
        return this.eventMapper.selectByExampleWithBLOBs(eventExample);
    }

    /**
     * イベントを作成
     *
     * @param event イベント
     *
     * @return イベントID
     */
    public int insert(final Event event) {
        return this.eventMapper.insertSelective(event);
    }

}
