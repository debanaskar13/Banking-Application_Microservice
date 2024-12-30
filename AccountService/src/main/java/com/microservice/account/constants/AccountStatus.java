package com.microservice.account.constants;

public enum AccountStatus {
    ACTIVE,               // Account is open and operational
    INACTIVE,             // Account is temporarily disabled
    SUSPENDED,            // Account is suspended due to issues
    CLOSED,               // Account has been closed permanently
    OVERDRAWN,            // Account has a negative balance
    FROZEN,               // Account is frozen for legal or security reasons
    PENDING,              // Account is awaiting approval or verification
    CLOSED_BY_USER,       // Account has been closed by the user
    BLOCKED,              // Account is blocked due to fraud or suspicious activity
    UNDER_REVIEW,         // Account is under review
    DORMANT,              // Account is inactive for a long period
    REOPENED;             // Account has been reopened after closure
}