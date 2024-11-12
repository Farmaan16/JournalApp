package com.xenoc.journalApp.controller;

import com.xenoc.journalApp.entity.JournalEntry;
import com.xenoc.journalApp.entity.User;
import com.xenoc.journalApp.service.JournalEntryService;
import com.xenoc.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;




    @GetMapping("{userName}")
    public ResponseEntity<?> getAllEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName){

        try {

//            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, userName);
            return new  ResponseEntity<JournalEntry>(myEntry,HttpStatus.CREATED);
        } catch (Exception e){
            return new  ResponseEntity<JournalEntry>(myEntry,HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isPresent()){
            return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
        }

        return  new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?>  deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName){
       journalEntryService.deleteById(myId, userName);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


//    @PutMapping("/id/{id}")
//    public ResponseEntity<?>  updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry ){
//        JournalEntry old = journalEntryService.findById(id).orElse(null);
//        if (old != null){
//            old.setTitle(newEntry.getTitle() != null && !   newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
//            old.setContent(newEntry.getContent() != null && !   newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent() );
//        journalEntryService.saveEntry(old, user);
//        return new ResponseEntity<>(old,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}