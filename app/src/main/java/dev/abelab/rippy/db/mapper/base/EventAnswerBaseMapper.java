package dev.abelab.rippy.db.mapper.base;

import dev.abelab.rippy.db.entity.EventAnswer;
import dev.abelab.rippy.db.entity.EventAnswerExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EventAnswerBaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    long countByExample(EventAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    int deleteByExample(EventAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    int insert(EventAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    int insertSelective(EventAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    List<EventAnswer> selectByExampleWithBLOBs(EventAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    List<EventAnswer> selectByExample(EventAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    EventAnswer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") EventAnswer record, @Param("example") EventAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") EventAnswer record, @Param("example") EventAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") EventAnswer record, @Param("example") EventAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(EventAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(EventAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_answer
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(EventAnswer record);
}