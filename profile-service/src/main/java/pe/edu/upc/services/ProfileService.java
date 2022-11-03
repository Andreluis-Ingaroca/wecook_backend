package pe.edu.upc.services;

import pe.edu.upc.aggregates.Profile;

public interface ProfileService extends CrudService<Profile,Long> {

    Profile createProfile(Long userId, Profile profile);

    Profile getProfileByUserId(Long userId);

    Profile getProfileByName(String name);

}
