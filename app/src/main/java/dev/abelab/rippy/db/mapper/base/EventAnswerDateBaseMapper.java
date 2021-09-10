package dev.abelab.rippy.db.mapper.base;

import dev.abelab.rippy.db.entity.EventAnswerDate;
import dev.abelab.rippy.db.entity.EventAnswerDateExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EventAnswerDateBaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    long countByExample(EventAnswerDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    int deleteByExample(EventAnswerDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    int insert(EventAnswerDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    int insertSelective(EventAnswerDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    List<EventAnswerDate> selectByExample(EventAnswerDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    EventAnswerDate selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") EventAnswerDate record, @Param("example") EventAnswerDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") EventAnswerDate record, @Param("example") EventAnswerDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(EventAnswerDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer_date
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(EventAnswerDate record);
}