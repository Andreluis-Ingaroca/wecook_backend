package pe.edu.upc.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.entities.Follow;
import pe.edu.upc.aggregates.Profile;
import pe.edu.upc.aggregates.User;
import pe.edu.upc.exception.ResourceNotFoundException;
import pe.edu.upc.repositories.FollowRepository;
import pe.edu.upc.repositories.ProfileRepository;
import pe.edu.upc.repositories.UserRepository;
import pe.edu.upc.services.ProfileService;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Override
    public Profile createProfile(Long userId, Profile profile) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));

        return profileRepository.save(profile.setUser(user));

    }
    @Override
    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findProfileByUserId(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Profile","Id",userId));
    }
    @Override
    public Profile getProfileByName(String name) {
        return profileRepository.findProfileByName(name)
                .orElseThrow(()->new ResourceNotFoundException("Profile","Name",name));
    }

    @Override
    public List<Profile> findAll() throws Exception {
        return profileRepository.findAll();
    }

    @Override
    public Profile findById(Long aLong) throws Exception {
        return profileRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Profile","Id",aLong));
    }

    @Override
    public Profile update(Profile entity, Long aLong) throws Exception {
        Profile profile = profileRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("","",aLong));
        profile.setBirthdate(entity.getBirthdate())
                .setDNI(entity.getDNI())
                .setGender(entity.getGender())
                .setName(entity.getName())
                .setProfilePictureUrl(entity.getProfilePictureUrl())
                .setSubscribersPrice(entity.getSubscribersPrice())
                .setSubsOn(entity.getSubsOn())
                .setTipsOn(entity.getTipsOn());

        return profileRepository.save(profile);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        List<Follow> follows = followRepository.findAll();

        follows.forEach(follow -> {
            if(follow.getChef().getId().equals(aLong)||follow.getReader().getId().equals(aLong)){
                followRepository.deleteById(follow.getId());
            }
        });

        profileRepository.deleteById(aLong);
    }
}
