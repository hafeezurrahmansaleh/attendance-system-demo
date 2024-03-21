package edu.miu.cs.cs544.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.common.service.contract.CommonAuditData;
import edu.miu.cs.cs544.controller.AccountController;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.service.AccountService;
import edu.miu.cs.cs544.service.contract.AccountPayload;
import edu.miu.cs.cs544.service.contract.AccountTypePayload;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        accountController = new AccountController(accountService);
    }

    @Test
    public void testCreateAccount() throws Exception {
        // Prepare test data
        AccountPayload accountPayload = createAccountPayload();

        // Mock service method
        when(accountService.createAccount(eq(accountPayload)))
                .thenReturn(accountPayload);

        // Perform POST request
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountPayload)))
                // Validate response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(accountPayload.getName()))
                .andExpect(jsonPath("$.description").value(accountPayload.getDescription()));
    }

    @Test
    public void testUpdateAccount() throws Exception {
        // Prepare test data
        Long accountId = 1L;
        AccountPayload accountPayload = createAccountPayload();
        accountPayload.setId(accountId);

        // Mock service method
        when(accountService.updateAccount(eq(accountId), ArgumentMatchers.refEq(accountPayload)))
                .thenReturn(accountPayload);

        // Perform PUT request
        mockMvc.perform(put("/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountPayload)))
                // Validate response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId))
                .andExpect(jsonPath("$.name").value(accountPayload.getName()))
                .andExpect(jsonPath("$.description").value(accountPayload.getDescription()));
    }


    private AccountPayload createAccountPayload() {
        // Create a sample account payload for testing
        AccountPayload accountPayload = new AccountPayload();
        accountPayload.setName("Test Account");
        accountPayload.setDescription("Test Description");
        accountPayload.setMember(createMemberPayload());
        accountPayload.setAccountType(createAccountTypePayload());
        accountPayload.setAuditData(createCommonAuditData());
        return accountPayload;
    }

    private MemberPayload createMemberPayload() {
        // Create a sample member payload for testing
        MemberPayload memberPayload = new MemberPayload();
        memberPayload.setId(1L); // Set member ID
        return memberPayload;
    }

    private AccountTypePayload createAccountTypePayload() {
        // Create a sample account type payload for testing
        AccountTypePayload accountTypePayload = new AccountTypePayload();
        accountTypePayload.setId(1L); // Set account type ID
        accountTypePayload.setType("Test Type");
        return accountTypePayload;
    }

    private CommonAuditData createCommonAuditData() {
        // Create a sample common audit data for testing
        CommonAuditData auditData = new CommonAuditData();
        auditData.setCreatedBy("Test User");
        return auditData;
    }
}
