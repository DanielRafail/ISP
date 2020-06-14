CREATE OR REPLACE FUNCTION getPackageID (custID IN VARCHAR2)
    RETURN VARCHAR2
    AS
        packageID VARCHAR2(10);
    BEGIN
        SELECT Customer.packageID INTO packageID from Customer WHERE customerID = custID;
        
        return packageID;
    END;
    
CREATE OR REPLACE PROCEDURE upgradePackage(custID IN VARCHAR2, packIDUpgrade IN VARCHAR2)
    AS
    BEGIN        
        UPDATE Customer SET packageID = packIDUpgrade WHERE customerID = custID;
    END;
    
-- MAYBE BETTER DONE IN JAVA DIRECTLY!!!! 
CREATE OR REPLACE PROCEDURE scheduleServiceRequest(custID IN VARCHAR2, callDate IN DATE, notes IN VARCHAR2, reqType IN VARCHAR2)
    AS
    BEGIN
        INSERT INTO ServiceRequset (employeeID, customerID, meetingDate, notes, requestType) VALUES (null, custID, callDate, notes, reqType);
    END;
