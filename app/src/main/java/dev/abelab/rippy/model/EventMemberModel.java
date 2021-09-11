package dev.abelab.rippy.model;

import java.util.List;

import lombok.*;

/**
 * イベントのメンバー情報モデル
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventMemberModel {

    /**
     * ユーザID
     */
    Integer id;

    /**
     * ファーストネーム
     */
    String firstName;

    /**
     * ラストネーム
     */
    String lastName;

    /**
     * 入学年度
     */
    Integer admissionYear;

    /**
     * 参加可能日リスト
     */
    List<EventDateModel> availableDates;

}
