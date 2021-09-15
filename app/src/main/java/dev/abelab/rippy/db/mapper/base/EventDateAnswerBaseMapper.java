package dev.abelab.rippy.db.mapper.base;

import dev.abelab.rippy.db.entity.EventDateAnswer;
import dev.abelab.rippy.db.entity.EventDateAnswerExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EventDateAnswerBaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date_answer
     *
     * @mbg.generated
     */
    long countByExample(EventDateAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date_answer
     *
     * @mbg.generated
     */
    int deleteByExample(EventDateAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date_answer
     *
     * @mbg.generated
     */
    int insert(EventDateAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date_answer
     *
     * @mbg.generated
     */
    int insertSelective(EventDateAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date_answer
     *
     * @mbg.generated
     */
    List<EventDateAnswer> selectByExample(EventDateAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date_answer
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") EventDateAnswer record, @Param("example") EventDateAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date_answer
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") EventDateAnswer record, @Param("example") EventDateAnswerExample example);
}