package com.ffs.chat.service.impl;

import com.ffs.chat.service.MemberService;
import com.ffs.chat.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private static final String ID_PREFIX = "chat";
    private static final String ID_TYPE = "mbr";
    private final IdGenerator idGenerator;

    public String createMember() {
        return idGenerator.createNewId(ID_PREFIX, ID_TYPE);

    }
}
