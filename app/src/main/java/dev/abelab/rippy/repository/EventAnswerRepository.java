package dev.abelab.rippy.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import dev.abelab.rippy.db.entity.EventAnswer;
import dev.abelab.rippy.db.entity.EventAnswerExample;
import dev.abelab.rippy.db.mapper.EventAnswerMapper;

@RequiredArgsConstructor
@Repository
public class EventAnswerRepository {

    private final EventAnswerMapper eventAnswerMapper;

    /**
     * イベントIDからイベント回答リストを取得
     *
     * @param eventId イベントID
     *
     * @return イベント回答リスト
     */
    public List<EventAnswer> selectByEventId(final int eventId) {
        final var example = new EventAnswerExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        return this.eventAnswerMapper.selectByExample(example);
    }

    /**
     * イベント回答を作成
     *
     * @param eventAnswer イベント回答
     */
    public void insert(final EventAnswer eventAnswer) {
        this.eventAnswerMapper.insert(eventAnswer);
    }

}
