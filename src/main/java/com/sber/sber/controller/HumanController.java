package com.sber.sber.controller;

import com.sber.sber.entity.model.HumanModel;
import com.sber.sber.entity.model.dto.AuthHumanDto;
import com.sber.sber.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/human")
public class HumanController {

    @Autowired
    private HumanService humanService;

    @PostMapping("/registration")
    public ResponseEntity registrationHuman(@RequestBody AuthHumanDto registrationDto) {
        return humanService.registrationHuman(registrationDto);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteHuman(@RequestParam Long id) {
        return humanService.deleteHuman(id);
    }

    @GetMapping("/allHuman")
    public List<HumanModel> getAllHuman() {
        return HumanModel.toModelList(humanService.allHuman());
    }

    @GetMapping("/deleteGet")
    public ResponseEntity deleteHumanGet(@RequestParam Long id) {
        return humanService.deleteHuman(id);
    }
}
