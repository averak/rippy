package dev.abelab.rippy.model;

import lombok.*;

/**
 * イベントのユーザ情報モデル
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventUserModel {

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

}
