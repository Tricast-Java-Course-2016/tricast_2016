DO $$
BEGIN
    IF EXISTS
    (SELECT 
      schema_name
    FROM 
      information_schema.schemata 
    WHERE 
      schema_name = 'tricast2016')
    THEN
      DROP SCHEMA tricast2016 CASCADE; 
    END IF; 

    CREATE SCHEMA IF NOT EXISTS TRICAST2016;

    CREATE TABLE TRICAST2016.ACCOUNTTYPES
    (
    id INTEGER NOT NULL,
    typeDescription VARCHAR(40),
    CONSTRAINT ACCOUNTTYPES_PK PRIMARY KEY (id)
    );
  
    CREATE TABLE TRICAST2016.BETTYPES
    (
    id integer NOT NULL,
    description character varying(50),
    CONSTRAINT BETTYPES_PK PRIMARY KEY (id)
    );

    CREATE TABLE TRICAST2016.COUNTRIES 
    (
    id integer NOT NULL,
    description character varying(50),
    CONSTRAINT COUNTRIES_PK PRIMARY KEY (id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_COUNTRIES INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

    CREATE TABLE TRICAST2016.LEAGUES 
    (
    id integer NOT NULL,
    description character varying(50),
    CONSTRAINT LEAGUES_PK PRIMARY KEY (id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_LEAGUES INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

    CREATE TABLE TRICAST2016.TEAMS 
    (
    id integer NOT NULL,
    description character varying(50),
    CONSTRAINT TEAMS_PK PRIMARY KEY (id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_TEAMS INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

    CREATE TABLE TRICAST2016.PERIODTYPES 
    (
    id integer NOT NULL,
    description character varying(50),
    CONSTRAINT PERIODTYPES_PK PRIMARY KEY (id)
    );

    CREATE TABLE TRICAST2016.MARKETTYPES 
    (
    id integer NOT NULL,
    description character varying(50),
    CONSTRAINT MARKETTYPES_PK PRIMARY KEY (id)
    );

    CREATE TABLE TRICAST2016.ACCOUNTS
    ( 
    id INTEGER NOT NULL, 
    accountTypeId INTEGER NOT NULL,
    username VARCHAR(30) NOT NULL, 
    password VARCHAR(50) NOT NULL, 
    firstName VARCHAR(50) NOT NULL, 
    lastName VARCHAR(50) NOT NULL,
    DOB VARCHAR(8) NOT NULL,
    address VARCHAR(300) NOT NULL,
    emailAddress VARCHAR(100) NOT NULL,
    phoneNumber VARCHAR(50) NULL,
    PIN VARCHAR(20) NOT NULL,
    bankAccountNumber VARCHAR(40) NOT NULL,
    bankCardNumber VARCHAR(40) NOT NULL,
    createdDate TIMESTAMP NOT NULL,
    CONSTRAINT ACCOUNTS_PK PRIMARY KEY (id),
      CONSTRAINT ACCOUNTTYPE_ACCOUNT_FK1 FOREIGN KEY (accountTypeId)
      REFERENCES TRICAST2016.ACCOUNTTYPES(id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_ACCOUNTS INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

    CREATE TABLE TRICAST2016.BETS 
    (
    id integer NOT NULL,
    accountid integer NOT NULL,
    bettypeid integer NOT NULL,
    CONSTRAINT BETS_PK PRIMARY KEY (id),
      CONSTRAINT ACCOUNT_BETS_FK1 FOREIGN KEY (accountid)
      REFERENCES TRICAST2016.ACCOUNTS(id),
      CONSTRAINT BETTYPE_BETS_FK1 FOREIGN KEY (bettypeid)
      REFERENCES TRICAST2016.BETTYPES(id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_BETS INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

    CREATE TABLE TRICAST2016.TRANSACTIONS
    (
    id integer NOT NULL,
    accountid integer NOT NULL,
    betid integer,
    createddate timestamp(6) without time zone NOT NULL,
    description character varying(60) NOT NULL,
    amount numeric(18,2) NOT NULL,
    CONSTRAINT TRANSACTIONS_PK PRIMARY KEY (id),
      CONSTRAINT account_transaction_fk1 FOREIGN KEY (accountid) 
      REFERENCES TRICAST2016.ACCOUNTS(id),
      CONSTRAINT bet_transaction_fk1 FOREIGN KEY (betid) 
      REFERENCES TRICAST2016.BETS(id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_TRANSACTIONS INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

    CREATE TABLE TRICAST2016.EVENTS
    (
    id integer NOT NULL,
    leagueid integer NOT NULL,
    countryid integer NOT NULL,
    hometeamid integer NOT NULL,
    awayteamid integer NOT NULL,
    description character varying(100) NOT NULL,
    status character varying(30) NOT NULL,
    starttime timestamp(6) with time zone NOT NULL,
    CONSTRAINT EVENTS_PK PRIMARY KEY (id),
      CONSTRAINT league_event_fk1 FOREIGN KEY (leagueid) 
      REFERENCES TRICAST2016.LEAGUES(id),
      CONSTRAINT country_event_fk1 FOREIGN KEY (countryid) 
      REFERENCES TRICAST2016.COUNTRIES(id),
      CONSTRAINT hometeam_event_fk1 FOREIGN KEY (hometeamid) 
      REFERENCES TRICAST2016.TEAMS(id),
      CONSTRAINT awayteam_event_fk1 FOREIGN KEY (awayteamid) 
      REFERENCES TRICAST2016.TEAMS(id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_EVENTS INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

    CREATE TABLE TRICAST2016.PERIODS
    (
    id integer NOT NULL,
    eventid integer NOT NULL,
    periodtypeid integer NOT NULL,
    description character varying(50) NOT NULL,
    hometeamscore integer,
    awayteamscore integer,
    CONSTRAINT PERIODS_PK PRIMARY KEY (id),
      CONSTRAINT event_period_fk1 FOREIGN KEY (eventid) 
      REFERENCES TRICAST2016.EVENTS(id),
      CONSTRAINT periodtype_period_fk1 FOREIGN KEY (periodtypeid) 
      REFERENCES TRICAST2016.PERIODTYPES(id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_PERIODS INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

    CREATE TABLE TRICAST2016.MARKETS
    (
    id integer NOT NULL,
    periodid integer NOT NULL,
    eventid integer NOT NULL,
    markettypeid integer NOT NULL,
    description character varying(50) NOT NULL,
    CONSTRAINT MARKETS_PK PRIMARY KEY (id),
      CONSTRAINT event_market_fk1 FOREIGN KEY (eventid) 
      REFERENCES TRICAST2016.EVENTS(id),
      CONSTRAINT markettype_market_fk1 FOREIGN KEY (markettypeid) 
      REFERENCES TRICAST2016.MARKETTYPES(id),
      CONSTRAINT period_market_fk1 FOREIGN KEY (periodid) 
      REFERENCES TRICAST2016.PERIODS(id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_MARKETS INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

    CREATE TABLE TRICAST2016.OUTCOMES
    (
    id integer NOT NULL,
    marketid integer NOT NULL,
    outcomecode character varying(5) NOT NULL,
    description character varying(50) NOT NULL,
    odds numeric(18,2),
    result character varying(1),
    CONSTRAINT OUTCOMES_PK PRIMARY KEY (id),
      CONSTRAINT market_outcome_fk1 FOREIGN KEY (marketid) 
      REFERENCES TRICAST2016.MARKETS(id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_OUTCOMES INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

    CREATE TABLE TRICAST2016.BETDATA 
    (
    betid integer NOT NULL,
    outcomeid integer NOT NULL,
    odds numeric(18,2) NOT NULL,
	CONSTRAINT BETDATA_PK PRIMARY KEY (betid, outcomeid),
      CONSTRAINT bet_betdata_fk1 FOREIGN KEY (betid) 
      REFERENCES TRICAST2016.ACCOUNTS(id),
      CONSTRAINT outcome_betdata_fk1 FOREIGN KEY (outcomeid) 
      REFERENCES TRICAST2016.BETS(id)
    );

    CREATE SEQUENCE TRICAST2016.SEQ_BETDATA INCREMENT BY 1 MINVALUE 100 NO MAXVALUE START WITH 100 CACHE 20 NO CYCLE;

END$$;

INSERT INTO TRICAST2016.ACCOUNTTYPES (ID, TYPEDESCRIPTION)
VALUES (1, 'OPERATOR'), (2, 'PLAYER');

INSERT INTO TRICAST2016.ACCOUNTS (ID, ACCOUNTTYPEID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, DOB, ADDRESS, EMAILADDRESS, PHONENUMBER, PIN, BANKACCOUNTNUMBER, BANKCARDNUMBER, CREATEDDATE)
VALUES
  (1, 1, 'op1', 'pass', 'Oszkár', 'Orosz', '19800102', 'address 1', 'oszkar@test.com', NULL, '1234',
   '12345678-90123456-78901234', '1234-5678-1234-5678', TIMESTAMP WITH TIME ZONE '2016-09-27 21:09:44 +2:00'),
  (2, 2, 'alma', 'pass', 'Alma', 'Adler', '19870930', 'alma address 1', 'alma@test.com', NULL, '1234',
   '12345678-90123456-78901234', '1234-5678-1234-5678', TIMESTAMP WITH TIME ZONE '2016-09-27 21:09:44 +2:00'),
  (3, 2, 'barack', 'pass', 'Barack', 'Kiss', '19910508', 'barack address 1', 'barack@test.com', NULL, '1234',
   '12345678-90123456-78901234', '1234-5678-1234-5678', TIMESTAMP WITH TIME ZONE '2016-09-27 21:09:44 +2:00'),
  (4, 2, 'citrom', 'pass', 'Citrom', 'Zöld', '19920422', 'citrom address 1', 'citrom@test.com', NULL, '1234',
   '12345678-90123456-78901234', '1234-5678-1234-5678', TIMESTAMP WITH TIME ZONE '2016-09-27 21:09:44 +2:00');

INSERT INTO TRICAST2016.LEAGUES (ID, DESCRIPTION)
VALUES (1, 'Premier League'), (2, 'FA Cup');

INSERT INTO TRICAST2016.COUNTRIES (ID, DESCRIPTION)
VALUES (1, 'England'), (2, 'Germany');

INSERT INTO TRICAST2016.TEAMS (ID, DESCRIPTION)
VALUES (1, 'Arsenal'), (2, 'Manchester United'), (3, 'Chelsea'), (4, 'Blackburn Rovers'), (5, 'Leicester City'),
  (6, 'Tottenham');

INSERT INTO TRICAST2016.EVENTS (ID, LEAGUEID, COUNTRYID, HOMETEAMID, AWAYTEAMID, DESCRIPTION, STATUS, STARTTIME)
VALUES (1, 2, 1, 1, 4, 'arsenal vs blackburn rovers', 'OPEN', current_timestamp),
  (2, 2, 1, 2, 5, 'manchester united vs leicester city', 'CLOSED', current_timestamp),
  (3, 2, 1, 3, 6, 'chelsea vs tottenham', 'OPEN', current_timestamp);

INSERT INTO TRICAST2016.PERIODTYPES (ID, DESCRIPTION)
VALUES (1, 'first half'), (2, 'second half'), (3, '90 mins'), (4, 'full time');

INSERT INTO TRICAST2016.PERIODS (ID, EVENTID, PERIODTYPEID, DESCRIPTION)
VALUES (1, 1, 1, 'arsenal vs blackburn first half'), (2, 1, 3, 'arsenal vs blackburn 90 mins'), (3, 1, 4, 'arsenal vs blackburn full time'),
  (4, 2, 1, 'manchester vs leicester first half'), (5, 2, 3, 'manchester vs leicester 90 mins'), (6, 2, 4, 'manchester vs leicester full time'),
  (7, 3, 1, 'chelsea vs tottenham first half'), (8, 3, 3, 'chelsea vs tottenham 90 mins'), (9, 3, 4, 'chelsea vs tottenham full time');

INSERT INTO TRICAST2016.MARKETTYPES (ID, DESCRIPTION)
VALUES (1, 'Win/Draw/Win'), (2, 'Total Goals O/U 2.5'), (3, 'Correct score'), (4, 'Double Chance');

INSERT INTO TRICAST2016.MARKETS (ID, PERIODID, EVENTID, MARKETTYPEID, DESCRIPTION)
VALUES (1, 4, 1, 1, 'arsenal vs blackburn full time WDW'), (2, 4, 1, 3, 'arsenal vs blackburn FT Total Goals O/U 2.5');

INSERT INTO TRICAST2016.OUTCOMES (ID, MARKETID, OUTCOMECODE, DESCRIPTION, ODDS)
VALUES (1, 1, '1', 'Arsenal', 1.24), (2, 1, '2', 'Blackburn', 1.94), (3, 1, 'X', 'Draw', 1.11),
  (4, 2, '0-0', '0-0', 3.64), (5, 2, '1-0', '1-0', 3.11), (6, 2, '0-1', '0-1', 2.64);

INSERT INTO TRICAST2016.BETTYPES (ID, DESCRIPTION)
VALUES (1, 'SINGLE'), (2, 'SYSTEM');

INSERT INTO TRICAST2016.BETS (ID, ACCOUNTID, BETTYPEID)
VALUES (1, 2, 1), (2, 3, 1), (3, 4, 1);

INSERT INTO TRICAST2016.BETDATA (BETID, OUTCOMEID, ODDS)
VALUES (1, 1, 1.24), (2, 2, 2.64), (3, 3, 1.12);

INSERT INTO TRICAST2016.TRANSACTIONS (ID, ACCOUNTID, BETID, CREATEDDATE, DESCRIPTION, AMOUNT)
VALUES
  (1, 2, NULL, TIMESTAMP WITH TIME ZONE '2016-09-28 21:09:44 +2:00', 'Pay in 1500 HUF', 1500),
  (2, 3, NULL, TIMESTAMP WITH TIME ZONE '2016-09-28 21:09:44 +2:00', 'Pay in 3500 HUF', 3500),
  (3, 4, NULL, TIMESTAMP WITH TIME ZONE '2016-09-28 21:09:44 +2:00', 'Pay out 900 HUF', -900),
  (4, 2, 1, TIMESTAMP WITH TIME ZONE '2016-09-29 21:09:44 +2:00', 'Place bet for 100 HUF', -100);