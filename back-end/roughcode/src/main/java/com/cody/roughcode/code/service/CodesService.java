package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.res.CodeDetailRes;

public interface CodesService {

    Long insertCode(CodeReq req, Long userId);

    CodeDetailRes getCode(Long codeId, Long userId);

}
