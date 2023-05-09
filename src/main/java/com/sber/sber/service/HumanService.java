package com.sber.sber.service;

import com.sber.sber.entity.Human;
import com.sber.sber.entity.model.dto.AuthHumanDto;
import com.sber.sber.event.EventHumanObject;
import com.sber.sber.exception.ErrorMessages;
import com.sber.sber.exception.NotFoundException;
import com.sber.sber.exception.RegistrationException;
import com.sber.sber.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class HumanService {

    @Autowired
    private HumanRepository humanRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public ResponseEntity registrationHuman(AuthHumanDto registrationDto) {
        validateEmail(registrationDto);
        Human newHuman = Human.builder()
                .name(registrationDto.getName())
                .password(registrationDto.getPassword())
                .email(registrationDto.getEmail())
                .build();
        applicationEventPublisher.publishEvent(EventHumanObject.builder()
                .flag(true)
                .human(newHuman)
                .build());
        humanRepository.save(newHuman);
        return ResponseEntity.ok("Вы успешно зарегестрировались!");
    }

    public List<Human> getHuman(String name) {
        List<Human> listByName = humanRepository.findByName(name);
        if (listByName.isEmpty()) {
            throw new NotFoundException(ErrorMessages.PERSON_NOT_FOUND);
        }
        return listByName;
    }

    public ResponseEntity deleteHuman(Long id) {
        Human human = humanRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.PERSON_NOT_FOUND));
        humanRepository.delete(human);
        return ResponseEntity.ok("Человек с id: " + id + " успешно удален!");
    }

    public List<Human> allHuman() {
        List<Human> all = humanRepository.findAll();
        return all;
    }

    private void  validateEmail(AuthHumanDto authHumanDto) {
        Set<String> allEmail = humanRepository.findAllEmail();
        if(authHumanDto.getPassword() == null) {
            throw new RegistrationException(ErrorMessages.MISSING_PASSWORD);
        }
        if(authHumanDto.getEmail() == null) {
            throw new RegistrationException(ErrorMessages.MISSING_EMAIL);
        }
        if(authHumanDto.getName() == null) {
            throw new RegistrationException(ErrorMessages.MISSING_NAME);
        }
        if (allEmail.contains(authHumanDto.getEmail())) {
            throw new RegistrationException(ErrorMessages.REPEATED_EMAIL);
        }
    }

}
