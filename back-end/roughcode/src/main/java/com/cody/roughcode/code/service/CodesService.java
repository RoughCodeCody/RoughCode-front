package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.CodeReq;

public interface CodesService {

    Long insertCode(CodeReq req, Long userId);
}
