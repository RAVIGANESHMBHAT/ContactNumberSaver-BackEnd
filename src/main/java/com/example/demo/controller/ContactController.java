package com.example.demo.controller;

import com.example.demo.entity.ContactNumber;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contactNumber/{name}")
    public ContactNumber getContact(@PathVariable("name") String name) throws ExecutionException, InterruptedException {
        return contactService.getContactNumber(name);
    }

    @GetMapping("/contactNumber")
    public List<ContactNumber> getAllContacts() throws ExecutionException, InterruptedException {
        return contactService.getAllContactNumbers();
    }

    @PostMapping("/addContactNumber")
    public String saveContact(@RequestBody ContactNumber contact) throws ExecutionException, InterruptedException {
        return contactService.saveContactNumber(contact);
    }

    @PutMapping("/updateContactNumber")
    public String updateContact(@RequestBody ContactNumber contact) throws ExecutionException, InterruptedException {
        return contactService.saveContactNumber(contact);
    }

    @DeleteMapping("/deleteContactNumber/{name}")
    public String deleteContact(@PathVariable String name) throws ExecutionException, InterruptedException {
        return contactService.deleteContactNumber(name);
    }
}
