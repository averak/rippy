package dev.abelab.rippy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import dev.abelab.rippy.db.entity.EventDate;
import dev.abelab.rippy.db.entity.EventDateExample;
import dev.abelab.rippy.db.mapper.EventDateMapper;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.NotFoundException;

@RequiredArgsConstructor
@Repository
public class EventDateRepository {

    private final EventDateMapper eventDateMapper;

    /**
     * イベント候補日一覧を取得
     *
     * @return イベント候補日一覧
     */
    public List<EventDate> selectAll() {
        final var eventDateExample = new EventDateExample();
        return this.eventDateMapper.selectByExample(eventDateExample);
    }

    /**
     * イベント候補日を作成
     *
     * @param event イベント候補日
     *
     * @return イベント候補日ID
     */
    public int insert(final EventDate eventDate) {
        return this.eventDateMapper.insertSelective(eventDate);
    }

    /**
     * イベント候補日を一括作成
     *
     * @param eventDates イベント候補日リスト
     */
    public void bulkInsert(List<EventDate> eventDates) {
        if (!eventDates.isEmpty()) {
            this.eventDateMapper.bulkInsert(eventDates);
        }
    }

    /**
     * IDからイベント候補日を検索
     *
     * @param eventDateId イベント候補日ID
     *
     * @return イベント候補日
     */
    public EventDate selectById(final int eventDateId) {
        return Optional.ofNullable(this.eventDateMapper.selectByPrimaryKey(eventDateId)) //
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EVENT));
    }

    /**
     * イベント候補日IDの存在確認
     *
     * @param eventDateId イベント候補日ID
     *
     * @return イベント候補日IDが存在するか
     */
    public boolean existsById(final int eventDateId) {
        try {
            this.selectById(eventDateId);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
