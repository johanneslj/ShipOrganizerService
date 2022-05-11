create
    definer = root@localhost procedure GetReports(IN Department varchar(255), IN Calltime varchar(255))
BEGIN
    IF(Calltime = 'Detail')
    then
        Select Product.ProductNumber,Product.ProductName, Storelink.Stock, Reports.Regdate,Department.DepartmentName
        from Reports
                 left join Product on
                Reports.FK_Product = Product.PK_ProdID
                 left join Storelink on
                Product.PK_ProdID = Storelink.FK_Product
                 left join Department on
                Storelink.FK_Store = Department.PK_DepartmentID
        Where Department.DepartmentName like CONCAT('%', Department , '%');
    END IF;
    if (Calltime = 'All')
    then
        Select SUM(Storelink.Stock) As Stock,Department.DepartmentName
        from Department
                 left join Storelink on
                Storelink.FK_Store = Department.PK_DepartmentID
                 left join Product on
                Product.PK_ProdID = Storelink.FK_Product
        Group by Department.DepartmentName;
    end if;
END;

create
    definer = root@localhost procedure HandleOrders(IN Calltime varchar(255), IN Department varchar(255),
                                                    IN ImageNameString varchar(255), IN statusType int)
BEGIN
    Declare DepartmentID int;
    if (Calltime = 'New')
    then
        Set DepartmentID =
                (Select PK_DepartmentID from Department where DepartmentName like CONCAT('%', Department, '%'));
        Insert into Orders (Imagename, FK_DepartmentID, Status)
        values (ImageNameString, DepartmentID, 0);
    end if;
    if (Calltime = 'Pending')
    then
        if (Department = '')
        then
            select Orders.Imagename, Department.DepartmentName, Orders.Status
            from Orders
                     left join Department on
                    Department.PK_DepartmentID = Orders.FK_DepartmentID
            where Orders.status = 0;
        else
            select Orders.Imagename, Department.DepartmentName, Orders.Status
            from Orders
                     left join Department on
                    Department.PK_DepartmentID = Orders.FK_DepartmentID
            where Department.DepartmentName like CONCAT('%', Department, '%')
              and Orders.status = 0;
        end if;
    end if;
    if (Calltime = 'Confirmed')
    then
        select Orders.Imagename, Department.DepartmentName, Orders.Status
        from Orders
                 left join Department on
                Department.PK_DepartmentID = Orders.FK_DepartmentID
        where Department.DepartmentName like CONCAT('%', Department, '%')
          and Orders.status > 0;
    end if;
    if (Calltime = 'Update')
    then
        update Orders set status=statusType where Imagename = ImageNameString;
    end if;
END;

create
    definer = root@localhost procedure HandleProduct(IN Calltime varchar(255), IN Department varchar(255),
                                                     IN ProductNameString varchar(255),
                                                     IN ProductNumberString varchar(255), IN EANString varchar(255),
                                                     IN DesiredStock int, IN Stock int, IN DateTimeString varchar(255))
BEGIN
    -- Local Variables
    Declare date datetime;
    Declare productID int;
    Declare negativStock int;
    if (CHAR_LENGTH(DateTimeString) > 0)
    then
        set date = STR_TO_DATE(DateTimeString, '%Y-%m-%d %H:%i:%s');
    end IF;
    if (Calltime = 'Insert')
    then
-- Insert new product to table
        insert into Product (ProductNumber, ProductName, EAN)
        values (ProductNumberString, ProductNameString, EANString);
-- Set stock
        CALL HandleStock(ProductNumberString, DesiredStock, Stock, Department, date);
    end if;
    if (Calltime = 'Update')
    then
        Update Product set ProductName=ProductNameString, EAN=EAN where ProductNumber = ProductNumberString;
        CALL HandleStock(ProductNumberString, DesiredStock, Stock, Department, date);
    end if;
    if (Calltime = 'InitialInventory')
    then
        SELECT Product.PK_ProdID,
               Product.ProductName,
               Product.ProductNumber,
               Product.EAN,
               Storelink.Stock,
               Storelink.Desired_Stock
        FROM Storelink
                 left join Department on
                Storelink.FK_Store = Department.PK_DepartmentID
                 left join Product on
                Product.PK_ProdID = Storelink.FK_Product
        where Department.DepartmentName like CONCAT('%', Department, '%');
    end if;
    if (Calltime = 'UpdatedInventory')
    then
        SELECT Product.PK_ProdID,
               Product.ProductName,
               Product.ProductNumber,
               Product.EAN,
               Storelink.Stock,
               Storelink.Desired_Stock
        FROM Storelink
                 left join Department on
                Storelink.FK_Store = Department.PK_DepartmentID
                 left join Product on
                Product.PK_ProdID = Storelink.FK_Product
        where Department.DepartmentName like CONCAT('%', Department, '%')
          and Storelink.Updated > date;
    end if;
    if (Calltime = 'Recommended')
    then
        Create table TempList
        (
            PK_ProdID     int,
            ProductName   varchar(255),
            ProductNumber varchar(255),
            EAN           char(13),
            Stock         int,
            Desired_Stock int
        );
        INSERT into TempList(PK_ProdID, ProductName, ProductNumber, EAN, Stock, Desired_Stock)
            (SELECT Product.PK_ProdID,
                    Product.ProductName,
                    Product.ProductNumber,
                    Product.EAN,
                    (Storelink.Desired_Stock - Storelink.Stock) AS `Stock`,
                    Storelink.Desired_Stock
             FROM Storelink
                      left join Department on
                     Storelink.FK_Store = Department.PK_DepartmentID
                      left join Product on
                     Storelink.FK_Product = Product.PK_ProdID
             where DepartmentName like CONCAT('%', Department, '%'));
        Call UpdateTempList();
    end if;
    if (Calltime = 'Delete')
    then
        SET productID = (select PK_ProdID from Product where ProductNumber = ProductNumberString);
        delete from Storelink where FK_Product = productID;
        delete from Reports where FK_Product = productID;
        delete from Product where PK_ProdID = productID;
    end if;
    if (Calltime = 'CheckProduct')
    then
        Select *
        from Product
                 left join Storelink on Product.PK_ProdID = Storelink.FK_Product
        where productNumber = ProductNumberString;
    end if;
END;

create
    definer = root@localhost procedure HandleStock(IN Prodnumber varchar(255), IN Desired_Stock int, IN Stock int,
                                                   IN Department varchar(255), IN Date datetime)
BEGIN
    -- Local Variables
    Declare ProdID int;
    Declare DepartementID int;
    Set ProdID = (select pk_prodid from Product where ProductNumber=Prodnumber);
    set DepartementID = (select PK_DepartmentID from Department where DepartmentName like CONCAT('%', Department , '%'));
    if((SELECT COUNT(1) FROM Storelink WHERE FK_Product = ProdID) = 0)
    then
        insert into Storelink (FK_Product,FK_Store,Stock,Desired_Stock,Updated) values (ProdID,DepartementID,Stock,Desired_Stock,Date);
    else
        update Storelink set Desired_Stock=Desired_Stock , Updated = Date where FK_Product=ProdID;
    end if;
END;

create
    definer = root@localhost procedure HandleUser(IN Calltime varchar(255), IN UsernameString varchar(255),
                                                  IN PasswordString varchar(255), IN Fullname varchar(255),
                                                  IN Department varchar(255), IN OldEmail varchar(255))
BEGIN
    -- NB!! Department needs to have (,) at the end
-- Local Variables
    DECLARE DepartemntID int;
    DECLARE UserID int;
    DECLARE _next TEXT DEFAULT NULL;
    DECLARE _nextlen INT DEFAULT NULL;
    DECLARE _value TEXT DEFAULT NULL;

    if(Calltime = 'Insert')
    then
-- LoginTable
        Insert into LoginTable (Username,Password,FullName) values (UsernameString,PasswordString,Fullname);
    end if;
    if(Calltime = 'UpdatePass')
    then
-- Logintable
        Update LoginTable set Password = @Password where Username = UsernameString;
    end if;
    if(Calltime = 'Update')
    then
-- Logintable
        Update LoginTable set  Username= UsernameString, Fullname=Fullname where Username = OldEmail;
    end if;
    if(Calltime = 'UpdateDepartment')
    then
        SET UserID  = (Select PK_UserID from LoginTable where Username = UsernameString LIMIT 1);
        -- Delete from table
        Delete from UserDepartment where FK_User = UserID;
        -- Make insert
        iterator:
        LOOP
            IF CHAR_LENGTH(TRIM(Department)) = 0 OR Department IS NULL THEN
                LEAVE iterator;
            END IF;
            SET _next = SUBSTRING_INDEX(Department,',',1);
            SET _nextlen = CHAR_LENGTH(_next);
            SET _value = TRIM(_next);
            -- Declare variables
            Set DepartemntID = (Select PK_DepartmentID from Department where DepartmentName like CONCAT('%',_value, '%'));
            -- Make insert
            Insert INTO UserDepartment (FK_Department,FK_User) values (DepartemntID,UserID);
            -- Set new possision
            SET Department = INSERT(Department,1,_nextlen + 1,'');
        END lOOP;
    end if;
    if(@Calltime = 'Delete')
    then
-- UserDepartment
        Set UserID = (Select PK_UserID from LoginTable where Username like CONCAT('%', UsernameString , '%'));
        Delete from UserDepartment where FK_User = UserID;
        Delete from LoginTable where PK_UserID = UserID;
    end if;
END;
create
    definer = root@localhost procedure InsertRecordAndUpdateStorelink(IN productno varchar(255),
                                                                      IN usernameString varchar(255), IN quantity int,
                                                                      IN longitude float, IN latitude float,
                                                                      IN DateTimeString varchar(255))
BEGIN
    -- Local Variables
    DECLARE PK_Product int;
    DECLARE ProductnameString varchar(255);
    Declare CurrStock int;
    DECLARE FullnameString varchar(255);
    Declare date DATETIME;
    Set PK_Product = (Select PK_ProdID from Product where ProductNumber = productno);
    Set ProductnameString = (select ProductName from Product where PK_ProdID = PK_Product);
    Set FullnameString = (Select Fullname from LoginTable where username like CONCAT('%', usernameString , '%'));
    if(CHAR_LENGTH(DateTimeString)>0)
    then
        set date = STR_TO_DATE(DateTimeString,'%Y-%m-%d %H:%i:%s');
    end IF;

-- Insert record into Report
    Insert into Reports (FK_Product,ProductName,Quantity,Longitude,Latitude,Fullname,Regdate) VALUES (PK_Product,ProductnameString,quantity,longitude,latitude,FullnameString,date);


-- Update Stock on product
    SET CurrStock = (Select STOCK from Storelink where FK_Product = PK_Product);
    Update Storelink set Stock = (CurrStock + quantity) , Updated=date where FK_Product = PK_Product;
END;


create
    definer = root@localhost procedure SelectAll(IN Calltime varchar(255), IN Department varchar(255),
                                                 IN Username varchar(255), IN ProductName varchar(255),
                                                 IN DateTimeString varchar(255))
Begin
    -- Local Variables
    Declare date datetime;
    if(CHAR_LENGTH(DateTimeString)>0)
    then
        set date = STR_TO_DATE(DateTimeString,'%Y-%m-%d %H:%i:%s');
    end IF;
    if(Calltime = 'Department')
    then
        SELECT DepartmentName FROM Department;
    end if;
    if(Calltime = 'Users')
    then
        if(len(Username) > 0)
        then
            SELECT LoginTable.Fullname,Department.DepartmentName FROM LoginTable
                                                                          left join UserDepartment on
                    LoginTable.PK_UserID = UserDepartment.FK_User
                                                                          left join Department on
                    UserDepartment.FK_Department = Department.PK_DepartmentID
            where LoginTable.Username like CONCAT('%', Username , '%');
        else
            SELECT LoginTable.Fullname,Department.DepartmentName FROM LoginTable
                                                                          left join UserDepartment on
                    LoginTable.PK_UserID = UserDepartment.FK_User
                                                                          left join Department on
                    UserDepartment.FK_Department = Department.PK_DepartmentID;
        end if;
    end if;
    if(Calltime = 'Map')
    then
        select Reports.PK_ReportID,Reports.ProductName,(Reports.Quantity),Reports.Latitude,Reports.Longitude,Reports.Regdate, Reports.Fullname from Reports
                                                                                                                                                        left join Product on
                Reports.FK_Product = Product.PK_ProdID
                                                                                                                                                        left join Storelink on
                Product.PK_ProdID = Storelink.FK_Product
                                                                                                                                                        left join Department on
                Storelink.FK_Store = Department.PK_DepartmentID
        where Department.DepartmentName like CONCAT('%', Department , '%') and Reports.Quantity <  0;
    end if;
    if(Calltime = 'Productmap')
    then
        Select Reports.PK_ReportID,Reports.ProductName,Reports.Quantity,Reports.Latitude,Reports.Longitude,Reports.Regdate,Reports.Fullname from Reports
                                                                                                                                                     left join Product on
                Reports.FK_Product = Product.PK_ProdID
                                                                                                                                                     left join Storelink on
                Product.PK_ProdID = Storelink.FK_Product
                                                                                                                                                     left join Department on
                Storelink.FK_Store = Department.PK_DepartmentID
        where Department.DepartmentName like  CONCAT('%', Department , '%') and Reports.ProductName like CONCAT('%', ProductName , '%') and Reports.Quantity <  0;
    end if;
End;

create
    definer = root@localhost procedure UpdateTempList()
BEGIN
    update TempList set stock=0 where stock <0;
    select * from TempList;
    DROP TABLE IF EXISTS TempList;

END;