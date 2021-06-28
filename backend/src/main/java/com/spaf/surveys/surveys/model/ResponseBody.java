package com.spaf.surveys.surveys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public @Data
class ResponseBody {

    private UUID appUserId;

    private List<List<Response>> responses;

}
