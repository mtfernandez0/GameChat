package com.mati.gamechat.service;

import com.mati.gamechat.entity.Group;
import com.mati.gamechat.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group save(Group entity) {
        return groupRepository.save(entity);
    }
}
