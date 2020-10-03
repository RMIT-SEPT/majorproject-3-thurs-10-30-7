CREATE DATABASE IF NOT EXISTS `appointment_system`;
USE `appointment_system`;

DROP TABLE IF EXISTS `employee_service`;
DROP TABLE IF EXISTS `booking`;
DROP TABLE IF EXISTS `service`;
DROP TABLE IF EXISTS `availability`;
DROP TABLE IF EXISTS `working_time`;
DROP TABLE IF EXISTS `security_user`;
DROP TABLE IF EXISTS `admin`;
DROP TABLE IF EXISTS `customer`;
DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `security_role`;


--
-- Table structure for table `security_role`
--

DROP TABLE IF EXISTS `security_role`;

CREATE TABLE `security_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone_no` varchar(11) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `img` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone_no` varchar(11) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `img` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone_no` varchar(11) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `security_user`
--

DROP TABLE IF EXISTS `security_user`;

CREATE TABLE `security_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(68) DEFAULT NULL,
  `customer_id` int(11),
  `employee_id` int(11),
  `admin_id` int(11),
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_account_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_account_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_account_admin` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `security_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `name` varchar(20) NOT NULL,
  `img` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `employee_service`
--

DROP TABLE IF EXISTS `employee_service`;

CREATE TABLE `employee_service` (
  `employee_id` int(11), 
  `service_id` int(11),
  PRIMARY KEY (employee_id, service_id),
  CONSTRAINT `fk_employee_id_service` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_service_id_employee` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `customer_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `status` varchar(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_customer_booking_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_employee_booking_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_service_id` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `availability`
--

DROP TABLE IF EXISTS `availability`;

CREATE TABLE `availability` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  CONSTRAINT `fk_employee_id_availaibility` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `working_time`
--

DROP TABLE IF EXISTS `working_time`;

CREATE TABLE `working_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  CONSTRAINT `fk_employee_id_working_time` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table `customer`
--

INSERT INTO `customer` VALUES
    (1,'Leslie','Uzumaki','leslie@hotmail.com', 0410101010, '4 Poornima Road', 'https://i.imgur.com/VXnG3tg.jpg'),
    (2,'Emma','Gaumbarten','emma@gmail.com', 0423101563, '3 Buttersworth Street', 'https://i.imgur.com/t6EJkdF.jpg'),
    (3,'Avani','Yupta','avani@hotmail.com', 0445231020, '9 King Court', 'https://i.imgur.com/vmFc3lX.jpg');
    
--
-- Data for table `employee`
--

INSERT INTO `employee` VALUES
    (4,'Yuri','Detrov','yuri@gmail.com', 0410164823, '2 Salamander Way', 'https://i.imgur.com/ZauQ8MZ.jpg'),
    (5,'Joe','Cool','yasdasdsri@gmail.com', 0410164823, '2 Salasdasder Way', 'https://i.imgur.com/RRSQ7uh.jpg');
    
--
-- Data for table `admin`
--

INSERT INTO `admin` VALUES
    (5,'Juan','Yega','juan@hotmail.com', 0410567343, '1 Digger Road');

--
-- Data for table `security_role`
--

INSERT INTO `security_role` VALUES
    (1, 'ROLE_CUSTOMER', 'Customer role.' ),
    (2,'ROLE_EMPLOYEE', 'Employee role.'),
    (3,'ROLE_ADMIN', 'Admin role.');


--
-- Data for table `security_user`
--

INSERT INTO `security_user` VALUES
    (1,'sasuke1', '$2a$10$C/8xsj.CiUBaCJIYPpjLg.25k3RZASgu37zHD6K6CltmAvb9Z2wLa', 1, null, null, 1),
    (2,'sakura1', '$2a$10$C/8xsj.CiUBaCJIYPpjLg.25k3RZASgu37zHD6K6CltmAvb9Z2wLa', 2, null, null, 1),
    (3,'naruto1', '$2a$10$C/8xsj.CiUBaCJIYPpjLg.25k3RZASgu37zHD6K6CltmAvb9Z2wLa', 3, null, null, 1),
    (4,'employee1', '$2a$10$C/8xsj.CiUBaCJIYPpjLg.25k3RZASgu37zHD6K6CltmAvb9Z2wLa', null, 4, null, 2),
    (5,'employee2', '$2a$10$C/8xsj.CiUBaCJIYPpjLg.25k3RZASgu37zHD6K6CltmAvb9Z2wLa', null, 5, null, 2),
    (6,'admin', '$2a$10$C/8xsj.CiUBaCJIYPpjLg.25k3RZASgu37zHD6K6CltmAvb9Z2wLa', null, null, 5, 3);
    
    
--
-- Data for table `availability`
--

INSERT INTO `availability` VALUES
    
    (1, 4, '2020-10-06 00:00:00', '2020-10-06 09:00:00'),
    (2, 4, '2020-10-07 00:00:00', '2020-10-07 09:00:00'),
    (3, 4, '2020-10-08 00:00:00', '2020-10-08 09:00:00'),
    (4, 4, '2020-10-09 00:00:00', '2020-10-09 09:00:00'),
    (5, 4, '2020-10-10 00:00:00', '2020-10-10 09:00:00'),
    (6, 5, '2020-10-06 00:00:00', '2020-10-06 09:00:00'),
    (7, 5, '2020-10-07 00:00:00', '2020-10-07 09:00:00'),
    (8, 5, '2020-10-08 00:00:00', '2020-10-08 09:00:00'),
    (9, 5, '2020-10-09 00:00:00', '2020-10-09 09:00:00'),
    (10, 5, '2020-10-10 00:00:00', '2020-10-10 09:00:00');

    
--
-- Data for table `working_time`
--

INSERT INTO `working_time` VALUES
    (1, 4, '2020-10-06 02:00:00', '2020-10-06 09:00:00'),
    (2, 4, '2020-10-07 02:00:00', '2020-10-07 09:00:00'),
    (3, 4, '2020-10-08 02:00:00', '2020-10-08 09:00:00'),
    (4, 4, '2020-10-09 02:00:00', '2020-10-09 09:00:00'),
    (5, 4, '2020-10-10 02:00:00', '2020-10-10 09:00:00'),
    (6, 5, '2020-10-06 02:00:00', '2020-10-06 09:00:00'),
    (7, 5, '2020-10-07 02:00:00', '2020-10-07 09:00:00'),
    (8, 5, '2020-10-08 02:00:00', '2020-10-08 09:00:00'),
    (9, 5, '2020-10-09 02:00:00', '2020-10-09 09:00:00'),
    (10, 5, '2020-10-10 02:00:00', '2020-10-10 09:00:00');

--
-- Data for table `working_time`
--

INSERT INTO `service` VALUES
	(1, 'service1', 'https://i.imgur.com/z53yLmm.jpg'),
    (2, 'service2', 'https://i.imgur.com/gzi5Nmu.jpg'),
    (3, 'service3', 'https://i.imgur.com/NfYcrHt.jpg'),
    (4, 'service4', 'https://i.imgur.com/eD7gQ7N.jpg');
    
INSERT INTO `employee_service` VALUES
	(4, 1),
    (4, 2),
    (5, 2),
    (5, 3),
    (5, 4);

