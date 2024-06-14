package com.example.projectservice.utlis;

import com.example.projectservice.domain.entities.PersonProjectRole;
import com.example.projectservice.exceptions.InvalidInviteLinkException;
import com.example.projectservice.services.PersonProjectService;
import lombok.RequiredArgsConstructor;
import org.hashids.Hashids;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InviteGenerator {
    private final Hashids hashids = new Hashids("sdfsdfsfdffddfdsf", 20);
    private final PersonProjectService personProjectService;

    public String generateHash(Long personId, Long projectId) throws IllegalAccessException {
        if (!personProjectService.hasAuthority(personId, projectId, PersonProjectRole.ADMIN, PersonProjectRole.OWNER))
            throw new IllegalAccessException();
        long timeStamp = System.currentTimeMillis();
        timeStamp += 60 * 60 * 1000;
        return hashids.encode(projectId, timeStamp);
    }

    public long validateHash(String hash) throws InvalidInviteLinkException {
        long[] decoded = hashids.decode(hash);
        if (decoded.length != 2)
            throw new InvalidInviteLinkException();
        else if (System.currentTimeMillis() > decoded[1])
            throw new InvalidInviteLinkException();
        return decoded[0];
    }
}
