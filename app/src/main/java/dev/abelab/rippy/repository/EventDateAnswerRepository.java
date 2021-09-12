package dev.abelab.rippy.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import dev.abelab.rippy.db.entity.EventDateAnswer;
import dev.abelab.rippy.db.entity.EventDateAnswerExample;
import dev.abelab.rippy.db.mapper.EventDateAnswerMapper;

@RequiredArgsConstructor
@Repository
public class EventDateAnswerRepository {

    private final EventDateAnswerMapper eventDateAnswerMapper;

    /**
     * イベント回答IDから候補日回答リストを取得
     *
     * @param answerId イベント回答ID
     *
     * @return イベント候補日回答リスト
     */
    public List<EventDateAnswer> selectByAnswerId(final int answerId) {
        final var example = new EventDateAnswerExample();
        example.createCriteria().andAnswerIdEqualTo(answerId);
        return this.eventDateAnswerMapper.selectByExample(example);
    }

    /**
     * イベント候補日回答を作成
     *
     * @param eventAnswer イベント候補日回答
     */
    public void insert(final EventDateAnswer eventDateAnswer) {
        this.eventDateAnswerMapper.insert(eventDateAnswer);
    }

}
