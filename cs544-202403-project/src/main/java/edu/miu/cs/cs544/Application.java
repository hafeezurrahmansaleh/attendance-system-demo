package edu.miu.cs.cs544;

import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.service.AccountService;
import edu.miu.cs.cs544.service.MemberService;
import edu.miu.cs.cs544.service.ScannerService;
import edu.miu.cs.cs544.service.contract.LocationPayload;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import edu.miu.cs.cs544.service.contract.RolePayload;
import edu.miu.cs.cs544.service.contract.ScannerPayload;
import edu.miu.cs.cs544.service.mapper.MemberPayloadToMemberMapper;
import edu.miu.cs.cs544.service.mapper.MemberToMemberPayloadMapper;
import edu.miu.cs.cs544.service.mapper.ScannerPayloadToScannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"edu.miu.common", "edu.miu.cs.cs544"})
@EnableJms
@EnableScheduling
public class Application implements CommandLineRunner {

//    @Autowired
//    private MemberService service;
//
//    @Autowired
//    private AccountService accountService;
//
//    @Autowired
//    private ScannerService scannerService;
//
//    @Autowired
//    private MemberToMemberPayloadMapper memberMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*List<Member> members = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Member m = new Member("firstName" + i, "lastName" + i,
                    "email@"+ i +"gmail.com", "barcode" + i);
            members.add(m);
        }
        service.saveAll(members);

        AccountType accountType = new AccountType("Student", "For student");
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Account m = new Account("Account", "Initial Account",accountType);
            accounts.add(m);
        }
        accountService.saveAll(accounts);

        //Location and Scanner
        LocationPayload location = new LocationPayload();
        location.setName("Agiro");
        location.setDescription("lunch or dinner");
        location.setType(LocationType.DINING);
        ScannerPayload scanner = new ScannerPayload();
        scanner.setName("Agiro scanner");
        scanner.setLocation(location);
        scanner.setMember(memberMapper.map(members.get(0)));
        scannerService.create(scanner);*/
    }

}