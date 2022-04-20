CREATE DATABASE `academigymraeg` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE USER 'ag-admin'@'%' IDENTIFIED BY 'AcademiGymraeg'; 

GRANT ALL PRIVILEGES ON academigymraeg.* TO 'ag-admin'@'%';