package pe.edu.upc.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.entities.Follow;
import pe.edu.upc.aggregates.Profile;
import pe.edu.upc.exception.ResourceNotFoundException;
import pe.edu.upc.repositories.FollowRepository;
import pe.edu.upc.repositories.ProfileRepository;
import pe.edu.upc.services.FollowService;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Follow createFollow(Follow follow, Long chefId, Long readerId) {
        Profile chef = profileRepository.findById(chefId)
                .orElseThrow(()-> new ResourceNotFoundException("Profile","Id",chefId));
        Profile reader = profileRepository.findById(readerId)
                .orElseThrow(()-> new ResourceNotFoundException("Profile","Id",readerId));

        return followRepository.save(follow.setChef(chef).setReader(reader));
    }

    @Override
    public List<Follow> findAll() throws Exception {
        return followRepository.findAll();
    }

    @Override
    public Follow findById(Long aLong) throws Exception {
        return followRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Follow","Id",aLong));
    }

    @Override
    public Follow update(Follow entity, Long aLong) throws Exception {
        return null;
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        followRepository.deleteById(aLong);
    }
}
