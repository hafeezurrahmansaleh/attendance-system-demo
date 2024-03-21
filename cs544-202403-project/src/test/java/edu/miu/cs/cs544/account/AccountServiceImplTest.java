package edu.miu.cs.cs544.account;

import edu.miu.common.exception.ResourceNotFoundException;
import edu.miu.common.service.contract.CommonAuditData;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.repository.AccountRepository;
import edu.miu.cs.cs544.repository.AccountTypeRepository;
import edu.miu.cs.cs544.repository.MemberRepository;
import edu.miu.cs.cs544.service.AccountServiceImpl;
import edu.miu.cs.cs544.service.contract.*;
import edu.miu.cs.cs544.service.mapper.AccountPayloadToAccountMapper;
import edu.miu.cs.cs544.service.mapper.AccountToAccountPayloadMapper;
import edu.miu.cs.cs544.service.mapper.MemberPayloadToMemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AccountToAccountPayloadMapper accountToAccountPayloadMapper;

    @Mock
    private AccountPayloadToAccountMapper accountPayloadToAccountMapper;

    @Mock
    private MemberPayloadToMemberMapper memberPayloadToMemberMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount() {
        // Prepare test data
        AccountPayload accountPayload = createAccountPayload();
        Account account = createAccountFromPayload(accountPayload);
        Member member = createMemberFromPayload(accountPayload.getMember());

        // Mock behavior
        when(memberRepository.findById(eq(1L))).thenReturn(Optional.of(member));
        when(accountPayloadToAccountMapper.map(eq(accountPayload))).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountToAccountPayloadMapper.map(eq(account))).thenReturn(accountPayload);

        // Perform the service call
        AccountPayload createdAccount = accountService.createAccount(accountPayload);

        // Validate the result
        assertEquals(accountPayload.getName(), createdAccount.getName());
        assertEquals(accountPayload.getDescription(), createdAccount.getDescription());
    }

    private Member createMemberFromPayload(MemberPayload memberPayload) {
        Member member = new Member();
        member.setId(memberPayload.getId());
        // Set other properties as needed
        return member;
    }
    @Test
    public void testUpdateAccount() {
        // Prepare test data
        Long accountId = 1L;
        AccountPayload accountPayload = createAccountPayload();
        accountPayload.setId(accountId);

        System.out.println(accountPayload);

        Account account = createAccountFromPayload(accountPayload);
        Member member = createMemberFromPayload(accountPayload.getMember());

        // Mock behavior
        when(accountRepository.findById(eq(accountId))).thenReturn(Optional.of(account));
        when(memberRepository.findById(eq(1L))).thenReturn(Optional.of(member));
        when(accountPayloadToAccountMapper.map(eq(accountPayload))).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountToAccountPayloadMapper.map(eq(account))).thenReturn(accountPayload);

        // Perform the service call
        AccountPayload updatedAccount = accountService.updateAccount(accountId, accountPayload);

        // Validate the result
        assertEquals(accountPayload.getName(), updatedAccount.getName());
        assertEquals(accountPayload.getDescription(), updatedAccount.getDescription());
    }



    private AccountPayload createAccountPayload() {
        // Create a sample account payload for testing
        AccountPayload accountPayload = new AccountPayload();
        accountPayload.setName("Test Account");
        accountPayload.setDescription("Test Description");
        accountPayload.setMember(createMemberPayload());

        // Ensure that AccountTypePayload is not null
        AccountTypePayload accountTypePayload = createAccountTypePayload();
        accountPayload.setAccountType(accountTypePayload);

        accountPayload.setAuditData(createCommonAuditData());
        return accountPayload;
    }

    private AccountTypePayload createAccountTypePayload() {
        // Create a sample account type payload for testing
        AccountTypePayload accountTypePayload = new AccountTypePayload();
        accountTypePayload.setId(1L); // Set account type ID
        accountTypePayload.setType("Test Type");
        return accountTypePayload;
    }

    private MemberPayload createMemberPayload() {
        // Create a sample member payload for testing
        MemberPayload memberPayload = new MemberPayload();
        memberPayload.setId(1L); // Set member ID
        return memberPayload;
    }

    private CommonAuditData createCommonAuditData() {
        // Create a sample common audit data for testing
        CommonAuditData auditData = new CommonAuditData();
        auditData.setCreatedBy("Test User");
        auditData.setUpdatedBy("Test User");
        return auditData;
    }

    private Account createAccountFromPayload(AccountPayload accountPayload) {
        // Create an Account entity from the payload for testing
        Account account = new Account();
        account.setId(accountPayload.getId());
        account.setName(accountPayload.getName());
        account.setDescription(accountPayload.getDescription());
        return account;
    }

//    @Test
    public void testGetAttendance_Success() {
        // Prepare test data
        Long accountId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-12-31";
        List<Object[]> attendanceList = createMockAttendanceList();

        // Mocking behavior
        AccountPayload accountPayload = createAccountPayload1();
        Account account = createAccountFromPayload(accountPayload);
        when(accountRepository.findById(eq(accountId))).thenReturn(Optional.of(account));
        when(accountPayloadToAccountMapper.map(any(AccountPayload.class))).thenReturn(account);
        when(accountRepository.getAttendanceForAccount(eq(accountId), any(Date.class), any(Date.class))).thenReturn(attendanceList);

        // Perform the service call
        AccountAttendanceSummaryPayload summaryPayload = accountService.getAttendanceForAccount(accountId, startDate, endDate);

        // Validate the result
        assertNotNull(summaryPayload);

        // Assert account attributes
        assertEquals(accountPayload.getId(), summaryPayload.getAccountId());
        assertEquals(accountPayload.getName(), summaryPayload.getAccountName());
        assertEquals(accountPayload.getAccountType().getType(), summaryPayload.getAccountType());
        assertEquals(accountPayload.getMember().getId(), summaryPayload.getMemberId());
        assertEquals(accountPayload.getBalance(), summaryPayload.getAccountBalance());

        // Assert attendance attributes
        List<AttendancePayload> attendancePayloadList = summaryPayload.getAttendances();
        assertEquals(attendanceList.size(), attendancePayloadList.size());

        // Add more assertions to validate attendance data
        for (int i = 0; i < attendanceList.size(); i++) {
            Object[] attendanceData = attendanceList.get(i);
            AttendancePayload attendancePayload = attendancePayloadList.get(i);
            assertEquals(attendanceData[0], attendancePayload.getAttendanceId());
            assertEquals(attendanceData[1], attendancePayload.getSession());
            assertEquals(attendanceData[2], attendancePayload.getMemberId());
            assertEquals(attendanceData[3], attendancePayload.getScannerId());
            assertEquals(attendanceData[4].toString(), attendancePayload.getAttendanceDateTIme());
        }

        // Assert summary attributes
        assertEquals(attendanceList.size(), summaryPayload.getTotalSessionRegistered());
        assertEquals(attendanceList.size(), summaryPayload.getTotalAttended());
        assertEquals(0, summaryPayload.getTotalAbsent()); // Assuming all sessions are attended
    }

    private AccountPayload createAccountPayload1() {
        // Create a sample account payload for testing
        AccountPayload accountPayload = new AccountPayload();
        accountPayload.setId(1L);
        // Set other attributes as needed
        return accountPayload;
    }

    private List<Object[]> createMockAttendanceList() {
        List<Object[]> attendanceList = new ArrayList<>();
        attendanceList.add(new Object[]{1L, "session1", 1L, 1L, Timestamp.valueOf("2024-01-01 08:00:00")});
        attendanceList.add(new Object[]{2L, "session2", 2L, 2L, Timestamp.valueOf("2024-01-02 08:00:00")});
        return attendanceList;
    }



    @Test
    public void testGetAttendance_AccountNotFound() {
        // Prepare test data
        Long accountId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-12-31";

        // Mock repository method to throw ResourceNotFoundException
        when(accountRepository.findById(eq(accountId))).thenReturn(Optional.empty());

        // Perform service call
        try {
            accountService.getAttendanceForAccount(accountId, startDate, endDate);
        } catch (ResourceNotFoundException e) {
            // Validate exception message
            assertEquals("Account not found with ID: " + accountId, e.getMessage());
        }
    }

    @Test
    public void testGetAttendance_InternalServerError() {
        // Prepare test data
        Long accountId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-12-31";

        // Mock repository method to throw RuntimeException
        when(accountRepository.findById(eq(accountId))).thenThrow(new RuntimeException("Internal Server Error"));

        // Perform service call
        try {
            accountService.getAttendanceForAccount(accountId, startDate, endDate);
        } catch (RuntimeException e) {
            // Validate exception message
            assertEquals("Internal Server Error", e.getMessage());
        }
    }




}
