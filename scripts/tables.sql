CREATE DATABASE  IF NOT EXISTS `KystFiske_db`;
USE `KystFiske_db`;

create table Department
(
    PK_DepartmentID int auto_increment
        primary key,
    DepartmentName  varchar(255) not null,
    Rights          int          null
);

create table LoginTable
(
    PK_UserID int auto_increment
        primary key,
    Username  varchar(255) not null,
    Password  varchar(255) not null,
    FK_ShipID int          null,
    Fullname  varchar(255) null,
    Token     varchar(255) null
);

create table Orders
(
    PK_OrderID      int auto_increment
        primary key,
    Imagename       varchar(255) not null,
    FK_DepartmentID int          null,
    Status          int          not null,
    ImageURL        varchar(255) null,
    constraint orders_ibfk_1
        foreign key (FK_DepartmentID) references Department (PK_DepartmentID)
);

create index FK_DepartmentID
    on Orders (FK_DepartmentID);

create table Product
(
    PK_ProdID     int auto_increment
        primary key,
    ProductNumber varchar(255) not null,
    ProductName   varchar(255) not null,
    EAN           varchar(13)  null
);

create table Reports
(
    PK_ReportID int auto_increment
        primary key,
    FK_Product  int                                null,
    ProductName varchar(255)                       not null,
    Quantity    int                                not null,
    Longitude   float                              not null,
    Latitude    float                              not null,
    Fullname    varchar(255)                       null,
    Regdate     datetime default CURRENT_TIMESTAMP null,
    constraint reports_ibfk_1
        foreign key (FK_Product) references Product (PK_ProdID),
    constraint reports_ibfk_2
        foreign key (FK_Product) references Product (PK_ProdID),
    constraint reports_ibfk_3
        foreign key (FK_Product) references Product (PK_ProdID)
);

create index FK_Product
    on Reports (FK_Product);

create table Storelink
(
    PK_LinkID     int auto_increment
        primary key,
    FK_Product    int      null,
    FK_Store      int      null,
    Stock         int      null,
    Desired_Stock int      null,
    minStock      int      null,
    Updated       datetime null,
    constraint storelink_ibfk_1
        foreign key (FK_Product) references Product (PK_ProdID),
    constraint storelink_ibfk_2
        foreign key (FK_Store) references Department (PK_DepartmentID),
    constraint storelink_ibfk_3
        foreign key (FK_Product) references Product (PK_ProdID),
    constraint storelink_ibfk_4
        foreign key (FK_Store) references Department (PK_DepartmentID)
);

create index FK_Product
    on Storelink (FK_Product);

create index FK_Store
    on Storelink (FK_Store);

create table UserDepartment
(
    PK_UserDepID  int auto_increment
        primary key,
    FK_Department int null,
    FK_User       int null,
    constraint userdepartment_ibfk_1
        foreign key (FK_Department) references Department (PK_DepartmentID),
    constraint userdepartment_ibfk_2
        foreign key (FK_User) references LoginTable (PK_UserID)
            on update cascade on delete cascade
);

create index FK_Department
    on UserDepartment (FK_Department);
