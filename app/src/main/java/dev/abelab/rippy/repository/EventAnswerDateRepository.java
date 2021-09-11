package dev.abelab.rippy.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import dev.abelab.rippy.db.entity.EventAnswerDate;
import dev.abelab.rippy.db.entity.EventAnswerDateExample;
import dev.abelab.rippy.db.mapper.EventAnswerDateMapper;

@RequiredArgsConstructor
@Repository
public class EventAnswerDateRepository {

    private final EventAnswerDateMapper eventAnswerDateMapper;

    /**
     * イベント回答IDから候補日回答リストを取得
     *
     * @param answerId イベント回答ID
     *
     * @return イベント候補日回答リスト
     */
    public List<EventAnswerDate> selectByAnswerId(final int answerId) {
        final var example = new EventAnswerDateExample();
        example.createCriteria().andAnswerIdEqualTo(answerId);
        return this.eventAnswerDateMapper.selectByExample(example);
    }

    /**
     * イベント候補日回答を作成
     *
     * @param eventAnswer イベント候補日回答
     */
    public void insert(final EventAnswerDate eventAnswerDate) {
        this.eventAnswerDateMapper.insert(eventAnswerDate);
    }

}
