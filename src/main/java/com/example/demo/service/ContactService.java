package com.example.demo.service;

import com.example.demo.entity.ContactNumber;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ContactService {

    private static final String COLLECTION_NAME = "Contact_Numbers";

    public ContactNumber getContactNumber(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        ContactNumber contactDetails;
        if(document.exists()) {
            contactDetails = document.toObject(ContactNumber.class);
        } else {
            contactDetails = null;
        }
        return contactDetails;
    }

    public List<ContactNumber> getAllContactNumbers() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = dbFirestore.collection(COLLECTION_NAME).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();

        List<ContactNumber> contactNumbers = new ArrayList<>();
        ContactNumber contactNumber = null;
        while(iterator.hasNext()) {
            DocumentReference documentReference1 = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();
            contactNumber = document.toObject(ContactNumber.class);
            contactNumbers.add(contactNumber);
        }
        return contactNumbers;
    }

    public String saveContactNumber(ContactNumber contactNumber) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(contactNumber.getName()).set(contactNumber);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String updateContactNumber(ContactNumber contactNumber) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(contactNumber.getName()).set(contactNumber);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteContactNumber(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(name).delete();
        return "Contact number with name " + name + " has been deleted successfully";
    }
}
