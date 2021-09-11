package dev.abelab.rippy.model;

import lombok.*;

/**
 * イベントのオーナー情報モデル
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventOwnerModel {

    /**
     * ファーストネーム
     */
    String firstName;

    /**
     * ラストネーム
     */
    String lastName;

}
