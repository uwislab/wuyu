package com.fiveup.core.award.controller;
import com.fiveup.core.award.entity.Award;
import com.fiveup.core.award.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/awards")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @GetMapping
    public List<Award> getAllAwards() {
        return awardService.getAllAwards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Award> getAwardById(@PathVariable Long id) {
        return awardService.getAwardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Award createAward(@RequestBody Award award) {
        return awardService.createAward(award);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Award> updateAward(@PathVariable Long id, @RequestBody Award awardDetails) {
        return ResponseEntity.ok(awardService.updateAward(id, awardDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAward(@PathVariable Long id) {
        awardService.deleteAward(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Award> searchAwards(@RequestParam String name) {
        return awardService.searchAwardsByName(name);
    }


}