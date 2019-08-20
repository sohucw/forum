CREATE CACHED TABLE PUBLIC.USER(
    ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_A7ADE43F_7CCF_4195_B206_D6DFF919C5C6) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_A7ADE43F_7CCF_4195_B206_D6DFF919C5C6,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(100),
    TOKEN CHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
)