package collectioner.service.impl;

import collectioner.model.entity.HeroEntity;
import collectioner.model.entity.ItemEntity;
import collectioner.model.entity.UserEntity;
import collectioner.model.entity.UserRoleEntity;
import collectioner.model.entity.enums.UserRole;
import collectioner.model.service.UserLoginServiceModel;
import collectioner.model.service.UserRegistrationServiceModel;
import collectioner.repository.HeroRepository;
import collectioner.repository.ItemRepository;
import collectioner.repository.UserRepository;
import collectioner.repository.UserRoleRepository;
import collectioner.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final CollectionerUserService collectionerUserService;
    private final HeroRepository heroRepository;
    private final ItemRepository itemRepository;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository, UserRepository userRepository,
                           CollectionerUserService collectionerUserService, HeroRepository heroRepository,
                           ItemRepository itemRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.collectionerUserService = collectionerUserService;
        this.heroRepository = heroRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public void registerUser(UserRegistrationServiceModel userServiceModel) {
        UserEntity newUser = modelMapper.map(userServiceModel, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

        UserRoleEntity userRole = userRoleRepository.
                findByRole(UserRole.USER).orElseThrow(
                () -> new IllegalStateException("unknown user role"));

        newUser.addRole(userRole);
        HeroEntity hero = new HeroEntity();

        hero.setBaseHP(100);
        hero.setBaseAttack(10);
        hero.setBaseDefense(10);
        hero.setGold(20);
        hero.setEnergy(10);
        hero.setWeapon(itemRepository.findById(1L).orElse(null));
        hero.setShield(itemRepository.findById(4L).orElse(null));
        hero.updateStats();

        heroRepository.save(hero);
        newUser.setHero(hero);
        userRepository.save(newUser);

    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();

    }


    @Override
    public void loginUser(UserLoginServiceModel userServiceModel) {
        UserDetails principal = collectionerUserService.loadUserByUsername(userServiceModel.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                userServiceModel.getPassword(),
                principal.getAuthorities()
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);

        Object principal2 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal2 instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            System.out.println(username);
        } else {
            String username = principal.toString();
        }

    }
}
