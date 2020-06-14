


--package for all procedures related to sales rep
Create or replace package Manager as
    procedure addEmployee(EmployeeID in varchar2, name in varchar2, position in varchar2, phone in varchar2, email in varchar2, managerID in varchar2, passwordHash in raw, salt in varchar2, passwordExpired in varchar2, active in char);
    procedure removeEmployee(pEmployeeID in varchar2, pManagerID in varchar2);
end Manager;

Create or replace package body Manager as
--Procedure to add user
 procedure addEmployee(EmployeeID in varchar2, name in varchar2, position in varchar2, phone in varchar2, email in varchar2, managerID in varchar2, passwordHash in raw, salt in varchar2, passwordExpired in varchar2, active in char) as
  begin
    Insert into Representatives values(EmployeeID, name, position, phone, email, managerID, passwordHash, salt, passwordExpired, active);
  end AddEmployee;
--Procedure to remove user
procedure removeEmployee(pEmployeeID in varchar2, pManagerID in varchar2) as
  vEmployeeID VARCHAR2(100);
  vManagerID VARCHAR2(100);
  begin
    SELECT EmployeeID, ManagerID INTO vEmployeeID, vManagerID FROM representatives
    WHERE EmployeeID = pEmployeeID;

    IF vManagerID = pManagerID THEN
          UPDATE representatives
          SET active = '0'
          WHERE vEmployeeID = pEmployeeID;
    END IF;
  end removeEmployee;
end;