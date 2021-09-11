package dev.abelab.rippy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import dev.abelab.rippy.db.entity.Event;
import dev.abelab.rippy.db.entity.EventExample;
import dev.abelab.rippy.db.entity.join.EventWithDates;
import dev.abelab.rippy.db.mapper.EventMapper;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.NotFoundException;

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
     * イベント（+候補日リスト）一覧を取得
     *
     * @return イベント（+候補日リスト）一覧
     */
    public List<EventWithDates> selectAllWithDates() {
        return this.eventMapper.selectAllWithDates();
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

    /**
     * イベントを更新
     *
     * @param event イベント
     */
    public void update(final Event event) {
        event.setUpdatedAt(null);
        this.eventMapper.updateByPrimaryKeySelective(event);
    }

    /**
     * イベントを削除
     *
     * @param eventId イベントID
     */
    public void deleteById(final int eventId) {
        if (this.existsById(eventId)) {
            this.eventMapper.deleteByPrimaryKey(eventId);
        } else {
            throw new NotFoundException(ErrorCode.NOT_FOUND_EVENT);
        }
    }

    /**
     * IDからイベントを取得
     *
     * @param eventId イベントID
     *
     * @return イベント
     */
    public Event selectById(final int eventId) {
        return Optional.ofNullable(this.eventMapper.selectByPrimaryKey(eventId)) //
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EVENT));
    }

    /**
     * IDからイベント（+候補日リスト）を取得
     *
     * @param eventId イベントID
     *
     * @return イベント（+候補日リスト）
     */
    public EventWithDates selectWithDatesById(final int eventId) {
        return Optional.ofNullable(this.eventMapper.selectWithDatesById(eventId)) //
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EVENT));
    }

    /**
     * オーナーIDからイベント一覧を検索
     *
     * @param ownerId オーナーID
     *
     * @return イベント一覧
     */
    public List<Event> selectByOwnerId(final int ownerId) {
        final var eventExample = new EventExample();
        eventExample.createCriteria().andOwnerIdEqualTo(ownerId);
        return this.eventMapper.selectByExample(eventExample);
    }

    /**
     * イベントIDの存在確認
     *
     * @param eventId イベントID
     *
     * @return イベントIDが存在するか
     */
    public boolean existsById(final int eventId) {
        try {
            this.selectById(eventId);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
