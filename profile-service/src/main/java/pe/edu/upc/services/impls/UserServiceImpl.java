package pe.edu.upc.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.aggregates.Profile;
import pe.edu.upc.aggregates.User;
import pe.edu.upc.valueobject.UserInformation;
import pe.edu.upc.exception.ResourceNotFoundException;
import pe.edu.upc.repositories.ProfileRepository;
import pe.edu.upc.repositories.UserRepository;
import pe.edu.upc.services.ProfileService;
import pe.edu.upc.services.UserService;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Override
    public UserInformation createdUser(User user) {
        try{
            List<User> userList = this.findAll();
            if (userList.isEmpty()){
                userRepository.save(user);
                return new UserInformation().setEmail(user.getEmail()).setId(user.getId());
            }
            userList.forEach(user1 -> {
                if(user1.getEmail().equals(user.getEmail())){
                    throw new ResourceNotFoundException("There is already another user with the same email address");
                }
            });
            userRepository.save(user);
            return new UserInformation().setEmail(user.getEmail()).setId(user.getId());
        }catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User","Email",email));
    }

    @Override
    public UserInformation login(User user) {
        User userR = getUserByEmail(user.getEmail());
        if(Objects.equals(userR.getPassword(), user.getPassword())){
            return new UserInformation().setId(userR.getId()).setEmail(userR.getEmail());
        }
        return null;
    }

    @Override
    public List<User> findAll() throws Exception {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long aLong) throws Exception {
        return userRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",aLong));
    }

    @Override
    public User update(User entity, Long aLong) throws Exception {
        User user1 = userRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",aLong));
        user1.setPassword(entity.getPassword());

        return userRepository.save(user1);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Profile profile = profileRepository.findProfileByUserId(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Profile","UserId",aLong));
        profileService.deleteById(profile.getId());
        userRepository.deleteById(aLong);
    }
}
