package edu.miu.cs.cs544.service.contract;

import edu.miu.common.domain.AuditData;
import edu.miu.common.service.contract.CommonAuditData;
import edu.miu.cs.cs544.domain.AccountType;
import edu.miu.cs.cs544.domain.Member;
import jakarta.persistence.Embedded;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AccountPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private Long balance;
    private MemberPayload member;
    private AccountTypePayload accountType;
    private CommonAuditData auditData;
}
