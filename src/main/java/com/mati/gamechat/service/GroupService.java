package com.mati.gamechat.service;

import com.mati.gamechat.entity.Group;
import com.mati.gamechat.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group save(Group entity) {
        return groupRepository.save(entity);
    }

    public Group findById(Long aLong) {
        return groupRepository.findById(aLong).orElse(null);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }
}
