package com.cegeka.ginkgo.domain.confirmation;

import com.cegeka.ginkgo.domain.users.UserEntity;
import com.cegeka.ginkgo.domain.users.UserRepository;
import com.cegeka.ginkgo.email.ConfirmationEmailCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/*
Responsible for generating confirmation token and emailing an user this token
Also responsible for confirming an user based on a token
 */
@Service
public class ConfirmationService {

    @Resource
    ConfirmationTokenRepository confirmationTokenRepository;
    @Resource
    private ConfirmationEmailCommand confirmationEmailCommand;
    @Resource
    private UserRepository userRepository;

    public void sendConfirmationEmailTo(UserEntity user) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(TokenFactory.generateToken());
        confirmationToken.setUser(user);
        confirmationTokenRepository.saveAndFlush(confirmationToken);
        confirmationEmailCommand.send(user);
    }

    @Transactional
    public void confirmToken(String token) throws TokenNotFoundException {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findOne(token);
        if (confirmationToken != null && !confirmationToken.getUser().isConfirmed()) {
            confirmationToken.getUser().setConfirmed(true);
            userRepository.saveAndFlush(confirmationToken.getUser());
        } else {
            throw new TokenNotFoundException("Could not find your token: " + token);
        }

    }

    public void setConfirmationTokenRepository(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void setConfirmationEmailCommand(ConfirmationEmailCommand confirmationEmailCommand) {
        this.confirmationEmailCommand = confirmationEmailCommand;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
