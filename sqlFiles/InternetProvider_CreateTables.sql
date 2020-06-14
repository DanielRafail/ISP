-- Authors: Daniel Rafail, Sonya Rabhi, Nick Trinh
-- Version: 26/04/2018

-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2018-05-01 18:36:12.903

-- tables
-- Table: BillingProblem
CREATE TABLE BillingProblem (
    callID varchar2(10)  NOT NULL,
    detailsCall varchar2(1000) NULL
) ;

-- Table: Calls
CREATE TABLE Calls (
    callID varchar2(10)  NOT NULL,
    customerID varchar2(10)  NOT NULL,
    employeeID varchar2(10)  NOT NULL,
    requestType varchar2(100)  NOT NULL,
    CONSTRAINT Calls_pk PRIMARY KEY (callID)
) ;

-- Table: Customer
CREATE TABLE Customer (
    customerID varchar2(10)  NOT NULL,
    packageID varchar2(10)  NOT NULL,
    username varchar2(30)  NULL,
    name varchar2(50)  NULL,
    phone varchar2(14)  NOT NULL,
    email varchar2(50)  NOT NULL,
    uploadUser number(5,2)  NOT NULL,
    downloadUser number(5,2)  NOT NULL,
    installationID varchar2(10)  NOT NULL,
    passwordHash raw(100)  NULL,
    salt varchar2(30)  NULL,
    passwordExpired char(1)  NOT NULL,
    streetNumber varchar2(100)  NOT NULL,
    city varchar2(30)  NOT NULL,
    province varchar2(20)  NOT NULL,
    postalCode varchar2(7)  NOT NULL,
    CONSTRAINT Customer_pk PRIMARY KEY (customerID)
) ;

-- Table: Features
CREATE TABLE Features (
    featuresID varchar2(10)  NOT NULL,
    featuresDesc varchar2(1000)  NOT NULL,
    featurePrice number(5,2)  NOT NULL,
    CONSTRAINT Features_pk PRIMARY KEY (featuresID)
) ;

-- Table: Invoice
CREATE TABLE Invoice (
    invoiceID varchar2(10)  NOT NULL,
    customerID varchar2(10)  NOT NULL,
    dueDate date  NOT NULL,
    creationDate date  NOT NULL,
    CONSTRAINT Invoice_pk PRIMARY KEY (invoiceID)
) ;

-- Table: InvoiceItems
CREATE TABLE InvoiceItems (
    invoiceID varchar2(10)  NOT NULL,
    serviceCharges number(6,2)  NOT NULL,
    setupFee number(5,2)  NOT NULL,
    price number(6,2)  NULL,
    itemNumber varchar2(10)  NOT NULL,
    CONSTRAINT InvoiceItems_pk PRIMARY KEY (itemNumber)
) ;

-- Table: Package
CREATE TABLE Package (
    packageID varchar2(10)  NOT NULL,
    price number(6,2)  NULL,
    uploadSpeed number(6,2)  NULL,
    downloadSpeed number(6,2)  NULL,
    limitBandwidth varchar2(100)  NOT NULL,
    overchargeCost number(6,2)  NULL,
    description varchar2(1000)  NOT NULL,
    CONSTRAINT Package_pk PRIMARY KEY (packageID)
) ;

-- Table: PackageFeatures
CREATE TABLE PackageFeatures (
    featuresID varchar2(10)  NOT NULL,
    packageID varchar2(10)  NOT NULL
) ;

-- Table: Representatives
CREATE TABLE Representatives (
    employeeID varchar2(10)  NOT NULL,
    name varchar2(50)  NULL,
    position varchar2(30)  NULL,
    phone varchar2(14)  NULL,
    email varchar2(50)  NOT NULL,
    managerID varchar2(10) NULL,
    passwordHash raw(100)  NULL,
    salt varchar2(30)  NULL,
    passwordExpired char(1)  NOT NULL,
    active char(1) NOT NULL,
    CONSTRAINT Representatives_pk PRIMARY KEY (employeeID)
) ;

-- Table: SalesCall
CREATE TABLE SalesCall (
    employeeID varchar2(10)  NOT NULL,
    sold char(1)  NULL,
    customerID varchar2(10)  NULL
) ;

-- Table: ServiceRequest
CREATE TABLE ServiceRequest (
    employeeID varchar2(10)  NULL,
    customerID varchar2(10)  NULL,
    meetingDate date  NOT NULL,
    notes varchar2(1000) NULL,
    requestType varchar2(100)  NOT NULL,
    CONSTRAINT ServiceRequest_pk PRIMARY KEY (requestType)
) ;

-- Table: Taxes
CREATE TABLE Taxes (
    type varchar2(5)  NOT NULL,
    startDate date  NOT NULL,
    endDate date  NOT NULL,
    rates number(5,3)  NOT NULL,
    CONSTRAINT Taxes_pk PRIMARY KEY (type)
) ;

-- Table: TechProblem
CREATE TABLE TechProblem (
    callID varchar2(10)  NOT NULL,
    problemDescription varchar2(1000)  NOT NULL
) ;



-- data insertion
INSERT INTO representatives (employeeID, name, position, phone, email, managerID, passwordHash, salt, passwordExpired, active)
VALUES ('11', 'Edmon Boldison', 'Billing', '9518837682', 'eboldison0@baidu.com', '22', '', 'salt', '1', '1');
INSERT INTO representatives (employeeID, name, position, phone, email, managerID, passwordHash, salt, passwordExpired, active)
VALUES ('12', 'Kippie Bullerwell', 'Tech support', '3685589982', 'kbullerwell1@studiopress.com', '22', '', 'salt', '1', '1');
INSERT INTO representatives (employeeID, name, position, phone, email, managerID, passwordHash, salt, passwordExpired, active)
VALUES ('13', 'Theda Alton', 'Sales', '1804156586', 'talton2@nydailynews.com', null, '', 'salt', '1', '1');
INSERT INTO representatives (employeeID, name, position, phone, email, managerID, passwordHash, salt, passwordExpired, active)
VALUES ('22', 'Teresita Backhurst', 'Manager', '9545473521', 'tbackhurst0@acquirethisname.com', null, '', 'salt', '1', '1');

INSERT INTO ServiceRequest (customerID, employeeID, meetingDate, notes, requestType)
VALUES ('1', '12', '15-JAN-18', null, 'upgrade');
INSERT INTO ServiceRequest(customerID, employeeID, meetingDate, notes, requestType)
VALUES ('2', '12', '26-JAN-19', null, 'serviceRemoval');
INSERT INTO ServiceRequest (customerID, employeeID, meetingDate, notes,requestType)
VALUES ('3', '12', '30-JUN-18', null, 'techSupport');


INSERT INTO package (packageID, price, uploadSpeed, downloadSpeed, limitBandwidth, overchargeCost, description)
VALUES ('1001', 89.99, 30.00, 200.00, 'UNLIMITED', 9.50 , 'Amazing download and upload speed');
INSERT INTO package (packageID, price, uploadSpeed, downloadSpeed, limitBandwidth, overchargeCost, description)
VALUES ('1002', 50.50, 15.00, 100.00, '999', 5.00, 'Unlimited data');
INSERT INTO package (packageID, price, uploadSpeed, downloadSpeed, limitBandwidth, overchargeCost, description)
VALUES ('1003', 124.99, 20.00, 120.00, 'UNLIMITED', 15.75, 'Free International Calls');

INSERT INTO packagefeatures (featuresID, packageID)
VALUES ('1', '1001');
INSERT INTO packagefeatures (featuresID, packageID)
VALUES ('2', '1002');
INSERT INTO packagefeatures (featuresID, packageID)
VALUES ('3', '1003');

INSERT INTO features (featuresID, featuresDesc, featurePrice)
VALUES ('1', 'FREE PHONE SERVICES, ' ||
             'FREE TELEVISION SERVICES', 0);
INSERT INTO features (featuresID, featuresDesc, featurePrice)
VALUES ('2', '24/7 CUSTOMER SUPPORT AVAILABILITY', 30);
INSERT INTO features (featuresID, featuresDesc, featurePrice)
VALUES ('3', '24/7 CUSTOMER SUPPORT AVAILABILITY, '||
             '24/7 TECHNICIAN SUPPORT AVAILABILITY, '||
             'FREE PREMIUM TELEVISION SERVICES', 80);

INSERT INTO customer (customerID, packageID, username, name, phone, email, uploadUser, downloadUser, installationID, passwordHash, salt, passwordExpired, streetNumber, city, province, postalCode)
VALUES ('1', '1001', 'bwardington0', 'Bard Wardington', '4292615825', 'bwardington0@quantcast.com', 15.00, 32.00, '52', '', 'salt', '1', '5143 street yeet', 'Montreal', 'Quebec', 'H7R 6K9');
INSERT INTO customer (customerID, packageID, username, name, phone, email, uploadUser, downloadUser, installationID, passwordHash, salt, passwordExpired, streetNumber, city, province, postalCode)
VALUES ('2', '1002', 'aneylon1', 'Aubrie Neylon', '5255556206', 'aneylon1@slate.com', 5.00, 20.00, '53', '', 'salt', '1', '8465 surepeme washington', 'Saint-Jerome', 'Quebec', 'T8Z 9U2');
INSERT INTO customer (customerID, packageID, username, name, phone, email, uploadUser, downloadUser, installationID, passwordHash, salt, passwordExpired, streetNumber, city, province, postalCode)
VALUES ('3', '1003', 'czavattieri2', 'Currey Zavattieri', '6577900719', 'czavattieri2@histats.com', 25.00, 50.00, '54', '', 'salt', '1', '3218 my house', 'Shawinigan', 'Quebec', 'E9J 4A9');

INSERT INTO salescall (employeeID, sold, customerID)
VALUES ('13', '1', '1');
INSERT INTO salescall (employeeID, sold, customerID)

VALUES ('13', '1', '2');
INSERT INTO salescall (employeeID, sold, customerID)
VALUES ('13', '1', '3');
/*
INSERT INTO salescall (employeeID, sold, customerID)
VALUES ('13', '0', null);
*/

INSERT INTO calls (callID, customerID, employeeID, requestType)
VALUES ('90', '1', '12', 'techSupport');
INSERT INTO calls (callID, customerID, employeeID, requestType)
VALUES ('91', '2', '12', 'serviceRemoval');
INSERT INTO calls (callID, customerID, employeeID, requestType)
VALUES ('92', '3', '11', 'upgrade');

INSERT INTO techproblem (callID, problemDescription)
VALUES ('90', 'N/A');
INSERT INTO techproblem (callID, problemDescription)
VALUES ('91', 'N/A');


INSERT INTO billingproblem (callID, detailsCall)
VALUES ('92', null);

INSERT INTO invoice (invoiceID, customerID, dueDate, creationDate)
VALUES ('5', '1', '05-JUN-17', '01-JUN-17');
INSERT INTO invoice (invoiceID, customerID, dueDate, creationDate)
VALUES ('6', '2', '24-JAN-18', '19-JAN-18');
INSERT INTO invoice (invoiceID, customerID, dueDate, creationDate)
VALUES ('7', '3', '10-JAN-18', '01-JAN-18');

INSERT INTO taxes (type, startDate, endDate, rates)
VALUES ('GST', '24-JUL-16', '29-JUL-20', 5);
INSERT INTO taxes (type, startDate, endDate, rates)
VALUES ('QST', '24-JUL-16', '29-JUL-20', 9.975);

INSERT INTO invoiceitems (invoiceID, serviceCharges, setupFee, price, itemNumber)
VALUES ('5', 9.98, 0.01, 9.99, '854');
INSERT INTO invoiceitems (invoiceID, serviceCharges, setupFee, price, itemNumber)
VALUES ('6', 6.94, 7.96, 14.90, '217');
INSERT INTO invoiceitems (invoiceID, serviceCharges, setupFee, price, itemNumber)
VALUES ('7', 0.38, 4.72, 5.10, '971');


-- foreign keys
-- Reference: BillingProblem_Calls (table: BillingProblem)
ALTER TABLE BillingProblem ADD CONSTRAINT BillingProblem_Calls
FOREIGN KEY (callID)
REFERENCES Calls (callID);

-- Reference: Customer_Package (table: Customer)
ALTER TABLE Customer ADD CONSTRAINT Customer_Package
FOREIGN KEY (packageID)
REFERENCES Package (packageID);


-- Reference: Invoice_Customer (table: Invoice)
ALTER TABLE Invoice ADD CONSTRAINT Invoice_Customer
FOREIGN KEY (customerID)
REFERENCES Customer (customerID);

-- Reference: PackageFeatures_Features (table: PackageFeatures)
ALTER TABLE PackageFeatures ADD CONSTRAINT PackageFeatures_Features
FOREIGN KEY (featuresID)
REFERENCES Features (featuresID);

-- Reference: PackageFeatures_Package (table: PackageFeatures)
ALTER TABLE PackageFeatures ADD CONSTRAINT PackageFeatures_Package
FOREIGN KEY (packageID)
REFERENCES Package (packageID);


-- Reference: SalesCall_Customer (table: SalesCall)
ALTER TABLE SalesCall ADD CONSTRAINT SalesCall_Customer
FOREIGN KEY (customerID)
REFERENCES Customer (customerID);

-- Reference: SalesCall_Representatives (table: SalesCall)
ALTER TABLE SalesCall ADD CONSTRAINT SalesCall_Representatives
FOREIGN KEY (employeeID)
REFERENCES Representatives (employeeID);


-- Reference: Table_16_Customer (table: Calls)
ALTER TABLE Calls ADD CONSTRAINT Table_16_Customer
FOREIGN KEY (customerID)
REFERENCES Customer (customerID);

-- Reference: Table_16_Representatives (table: Calls)
ALTER TABLE Calls ADD CONSTRAINT Table_16_Representatives
FOREIGN KEY (employeeID)
REFERENCES Representatives (employeeID);


-- End of file.

