
--package for all procedures related to sales rep
Create or replace package SalesRep as
    procedure addUser(customerID in varchar2, packageID in varchar2, username in varchar2, name in varchar2, phone in varchar2, email in varchar2, uploadUser in number, downloadUser in number,installationID in varchar2, passwordExpired in char, salt in varchar, password in raw, streetNumber in varchar2, city in varchar2, province in varchar2, postalCode in varchar2);
    procedure changePackage(custID in varchar2, packID in varchar2);
    function coldCall(employeeID in varchar2, sold in char, customerID in varchar2) return char;
    procedure addInstallation(installationId in varchar2, customerId in varchar2, employeeID in varchar2, scheduledDate in date, notes in varchar2);
end SalesRep;
/
Create or replace package body SalesRep as  
    --Procedure to add user
    procedure addUser(customerID in varchar2, packageID in varchar2, username in varchar2, name in varchar2, phone in varchar2, email in varchar2, uploadUser in number, downloadUser in number,installationID in varchar2,  passwordExpired in char, salt in varchar, password in raw, streetNumber in varchar2, city in varchar2, province in varchar2, postalCode in varchar2)
    as    
    begin
     Insert into Customer values(customerID, packageID, username, name, phone, email, uploadUser, downloadUser, installationID, password, salt, passwordExpired, streetNumber, city, province, postalCode);
    end AddUser;

    --procedure to change package
    procedure changePackage(custID in varchar2, packID in varchar2) as 
    begin
        Update Customer set packageId = packID where customerId = custID;
    end changePackage;

    --procedure to make a coldcall
    function coldCall(employeeID in varchar2, sold in char, customerID in varchar2) return char as
    begin
     if(sold = '0') then
          Insert into salesCall values (employeeID, sold, null);
        return 0;
     else 
            Insert into salesCall values (employeeID, sold, customerID);
        return 1;
     end if;
    end coldCall;

    procedure addInstallation(installationId in varchar2, customerId in varchar2, employeeID in varchar2, scheduledDate in date, notes in varchar2) as
     begin
     Insert into Installation values(installationID, customerId, employeeID, scheduledDate, notes);
    end AddInstallation;
    
end salesRep;