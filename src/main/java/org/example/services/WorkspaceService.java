package org.example.services;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.Client.Client;
import org.example.dto.UserDto;
import org.example.dto.WorkspaceResponseDto;
import org.example.entity.User;
import org.example.entity.Workspace;
import org.example.entity.WorkspaceMember;
import org.example.mapper.UserMapper;
import org.example.mapper.WorkspaceMapper;
import org.example.repository.UserRepository;
import org.example.repository.WorkspaceMemberRepository;
import org.example.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final Client client;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceMapper workspaceMapper;
    private final UserMapper userMapper;

    @Transactional
    public List<String> syncWorkspaces(String userAccessToken) {

        WorkspaceResponseDto response = client.getWorkspaces(userAccessToken);

        if (response == null || response.teams() == null) {
            return List.of();
        }

        List<String> workspaceIds = new ArrayList<>();

        for (WorkspaceResponseDto.WorkspaceDto teamDto : response.teams()) {
            workspaceIds.add(teamDto.id());

            Workspace workspace = workspaceRepository.findById(teamDto.id()).orElseGet(Workspace::new);

            workspaceMapper.updateEntityFromDto(teamDto, workspace);
            workspaceRepository.save(workspace);
            if (teamDto.members() != null) {
                for (WorkspaceResponseDto.WorkspaceMemberDto memberDto : teamDto.members()) {
                    if (memberDto.user() == null) continue;

                    User user = saveOrUpdateUser(memberDto.user());
                    User invitedBy = saveOrUpdateUser(memberDto.invitedBy());

                    WorkspaceMember member = workspaceMemberRepository
                            .findByWorkspaceIdAndUserId(workspace.getId(), user.getId())
                            .orElseGet(WorkspaceMember::new);

                    member.setWorkspace(workspace);
                    member.setUser(user);
                    member.setInvitedBy(invitedBy);

                    workspaceMemberRepository.save(member);
                }
            }
        }

        return List.copyOf(workspaceIds);
    }

    private User saveOrUpdateUser(UserDto userDto) {
        if (userDto == null) return null;

        User user = userRepository.findById(userDto.id()).orElseGet(User::new);

        userMapper.updateEntityFromDto(userDto, user);

        return userRepository.save(user);
    }
}
