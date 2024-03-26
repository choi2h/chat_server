package com.ffs.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MatchingResult {

    private Long memberId;
    private String memberName;
    private Long employeeId;
    private String employeeName;
    private boolean isMatched;

}
