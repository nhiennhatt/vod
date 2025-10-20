package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.UserInform;
import com.hiennhatt.vod.repositories.UserInformRepository;
import com.hiennhatt.vod.repositories.UserRepository;
import com.hiennhatt.vod.repositories.projections.*;
import com.hiennhatt.vod.services.UserService;
import com.hiennhatt.vod.utils.HTTPResponseStatusException;
import com.hiennhatt.vod.utils.StoreUtils;
import com.hiennhatt.vod.validations.RegisterUserValidation;
import com.hiennhatt.vod.validations.UpdateProfileValidation;
import jakarta.annotation.PostConstruct;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInformRepository userInformRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Environment env;
    private Path uploadAvatarDir;
    private Path uploadCoverDir;

    @PostConstruct
    public void init() {
        String uploadDirString = env.getProperty("uploadedDir", "classpath:uploadDir/");
        Path uploadDirPath = Paths.get(uploadDirString);
        uploadAvatarDir = uploadDirPath.resolve("public/avatars/");
        uploadCoverDir = uploadDirPath.resolve("public/covers/");
        try {
            Files.createDirectories(uploadAvatarDir);
            Files.createDirectories(uploadCoverDir);
        } catch (IOException e) {
        }
    }

    @Override
    @Transactional
    public void registerUser(RegisterUserValidation registerUser) {
        if (userRepository.existsUserByUsername(registerUser.getUsername()))
            throw new HTTPResponseStatusException("Username has already been taken", "USER_CONFLICT", HttpStatus.CONFLICT, null);
        if (userRepository.existsUserByEmail(registerUser.getEmail()))
            throw new HTTPResponseStatusException("Email has already been taken", "USER_CONFLICT", HttpStatus.CONFLICT, null);

        User user = registerUser.toUser();
        user.setRole(User.Role.ROLE_USER);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase());
        user.setStatus(User.Status.ACTIVE);
        user.setIsVerified(false);
        Instant now = Instant.now();
        user.setCreatedOn(now);
        user.setUpdatedOn(now);
        this.userRepository.save(user);

        UserInform userInform = new UserInform();
        userInform.setUser(user);
        userInform.setCreatedOn(now);
        userInform.setUpdatedOn(now);
        this.userInformRepository.save(userInform);
    }

    @Override
    public PublicUserInformProjection getUserInformByUsername(String username) {
        PublicUserInformProjection userInform = userInformRepository.findPublicUserInformProjectionByUserUsername(username);
        if (userInform == null)
            throw new HTTPResponseStatusException("User inform not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);
        return userInform;
    }

    @Override
    public DetailUserProjection getUserInformByUser(User user) {
        DetailUserProjection userInform = userRepository.findDetailUserProjectionByUsername(user.getUsername());
        if (userInform == null)
            throw new HTTPResponseStatusException("User inform not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);
        return userInform;
    }

    @Override
    @Transactional
    public void updateProfile(UpdateProfileValidation newProfile, User user) {
        UserInform userInform = userInformRepository.findUserInformByUser(user);
        if (userInform == null) {
            createUserInformFromUpdateProfile(user, newProfile);
            return;
        }

        userInform.setFirstName(newProfile.getFirstName());
        userInform.setLastName(newProfile.getLastName());
        userInform.setMiddleName(newProfile.getMiddleName());
        userInform.setDescription(newProfile.getDescription());
        userInform.setDateOfBirth(newProfile.getDateOfBirth());
    }

    private void createUserInformFromUpdateProfile(User user, UpdateProfileValidation updateProfile) {
        UserInform userInform = new UserInform();
        userInform.setUser(user);
        userInform.setLastName(updateProfile.getLastName());
        userInform.setFirstName(updateProfile.getFirstName());
        userInform.setMiddleName(updateProfile.getMiddleName());
        userInform.setDescription(updateProfile.getDescription());
        userInform.setDateOfBirth(updateProfile.getDateOfBirth());
        userInformRepository.save(userInform);
    }

    private UserInform createUserInformFrom(User user) {
        UserInform userInform = new UserInform();
        userInform.setUser(user);
        return userInform;
    }

    @Override
    @Transactional
    public String updateAvatar(MultipartFile avatar, User user) {
        try {
            Path savedPath = StoreUtils.save(uploadAvatarDir, StoreUtils.generateUid(), avatar);
            UserInform inform = userInformRepository.findUserInformByUser(user);
            if (inform == null) {
                inform = createUserInformFrom(user);
            }
            inform.setAvatar(savedPath.getFileName().toString());
            userInformRepository.save(inform);
            return savedPath.getFileName().toString();
        } catch (MimeTypeException e) {
            e.printStackTrace();
            throw new HTTPResponseStatusException("Invalid mime type", "INVALID_MIME_TYPE", HttpStatus.BAD_REQUEST, null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public String updateCoverImage(MultipartFile cover, User user) {
        try {
            Path savedPath = StoreUtils.save(uploadCoverDir, StoreUtils.generateUid(), cover);
            UserInform inform = userInformRepository.findUserInformByUser(user);
            if (inform == null) {
                inform = createUserInformFrom(user);
            }
            System.out.println(savedPath.getFileName().toString());
            inform.setCoverImg(savedPath.getFileName().toString());
            userInformRepository.save(inform);
            return savedPath.getFileName().toString();
        } catch (MimeTypeException e) {
            throw new HTTPResponseStatusException("Invalid mime type", "INVALID_MIME_TYPE", HttpStatus.BAD_REQUEST, null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BasicUserInformProjection getBasicUserInformDTO(User user) {
        return userInformRepository.findUserInformProjectionByUser((user));
    }

    @Transactional
    @Override
    public AuthorizationUserProjection updateUsername(String newUsername, User user) {
        try {
            userRepository.updateUserUsername(newUsername.toLowerCase(), user.getId());
            return userRepository.findAuthorizationUserByUsername(newUsername);
        } catch (DataIntegrityViolationException e) {
            throw new HTTPResponseStatusException("Username has already been taken", "USER_CONFLICT", HttpStatus.CONFLICT, null);
        }
    }

    @Override
    @Transactional
    public void updateEmail(String newEmail, User user) {
        try {
            userRepository.updateUserEmail(newEmail, user.getId());
        } catch (DataIntegrityViolationException e) {
            throw new HTTPResponseStatusException("Email has already been taken", "USER_CONFLICT", HttpStatus.CONFLICT, null);
        }
    }

    @Override
    public List<UserOverviewProjection> searchUser(String keyword, Pageable pageable) {
        return userRepository.searchUsers("%" + keyword + "%", pageable);
    }
}
