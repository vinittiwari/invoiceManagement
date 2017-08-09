-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.18-log - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             9.4.0.5174
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for billmanagement
CREATE DATABASE IF NOT EXISTS `billmanagement` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `billmanagement`;

-- Dumping structure for table billmanagement.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `item` longtext NOT NULL,
  `party_id` varchar(50) DEFAULT NULL,
  `invoice_date` date NOT NULL,
  `invoice_number` bigint(20) NOT NULL,
  `dispatch_date` date NOT NULL,
  `address` varchar(255) NOT NULL,
  `address1` varchar(255) NOT NULL,
  `party_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table billmanagement.invoice: ~5 rows (approximately)
DELETE FROM `invoice`;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` (`item`, `party_id`, `invoice_date`, `invoice_number`, `dispatch_date`, `address`, `address1`, `party_name`) VALUES
	('[{"item_total_price":"115","item_id":"ads32","item_name":"papias","item_quantity":"5"},{"item_total_price":"238","item_id":"saa33","item_name":"werw","item_quantity":"7"},{"item_total_price":"238","item_id":"saa33","item_name":"werw","item_quantity":"7"}]', 'DFG789', '2017-07-20', 122321, '2017-07-29', 'asdfa', 'asdfasdf', 'vinit'),
	('[{"item_total_price":"70","item_id":"JB12","item_name":"Jaledi","item_quantity":"7"}]', 'BNGH5', '2017-07-20', 122321, '2017-06-26', 'asdfasdf', 'asdfasdf', 'Gopal'),
	('[{"item_total_price":"272","item_id":"saa33","item_name":"werw","item_quantity":"8"}]', 'DFG789', '2017-07-31', 122321, '2017-07-24', '9 kamla nehru ', 'sadfasf', 'vinit'),
	('[{"item_total_price":"272","item_id":"saa33","item_name":"werw","item_quantity":"8"}]', 'BNGH5', '2017-07-04', 122321, '2017-07-11', 'asdfe', 'sdfasfdas', 'Gopal'),
	('[{"item_total_price":"80","item_id":"JB12","item_name":"Jaledi","item_quantity":"8"}]', 'BNGH5', '2017-07-25', 122321, '2017-07-24', 'asdfe', 'sdfasfdas', 'Gopal'),
	('[{"item_total_price":"10","item_id":"JB12","item_name":"Jaledi","item_quantity":"1"}]', 'DFG789', '2017-07-24', 122321, '2017-07-24', '9 kamla nehru ', 'sadfasf', 'vinit');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;

-- Dumping structure for table billmanagement.item
CREATE TABLE IF NOT EXISTS `item` (
  `item_name` varchar(50) NOT NULL,
  `item_id` varchar(50) NOT NULL,
  `price` int(11) NOT NULL,
  `item_code` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL,
  `unit` varchar(50) NOT NULL,
  `gms` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table billmanagement.item: ~3 rows (approximately)
DELETE FROM `item`;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` (`item_name`, `item_id`, `price`, `item_code`, `status`, `unit`, `gms`) VALUES
	('papias', 'ads32', 23, 'FED12', 'T', '54', '5'),
	('werw', 'saa33', 34, 'EDF22', 'F', '60', '6'),
	('Jaledi', 'JB12', 10, 'EDF55', 'T', '100', '5');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;

-- Dumping structure for table billmanagement.party
CREATE TABLE IF NOT EXISTS `party` (
  `party_name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `address1` varchar(255) NOT NULL,
  `state` varchar(255) DEFAULT NULL,
  `gstin` varchar(255) DEFAULT NULL,
  `tin_no` int(11) NOT NULL,
  `transport` varchar(255) DEFAULT NULL,
  `phone1` bigint(20) NOT NULL,
  `phone2` bigint(20) DEFAULT NULL,
  `email1` varchar(255) DEFAULT NULL,
  `email2` varchar(255) DEFAULT NULL,
  `party_id` varchar(255) NOT NULL,
  UNIQUE KEY `party_name` (`party_name`),
  UNIQUE KEY `party_id` (`party_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billmanagement.party: ~2 rows (approximately)
DELETE FROM `party`;
/*!40000 ALTER TABLE `party` DISABLE KEYS */;
INSERT INTO `party` (`party_name`, `address`, `address1`, `state`, `gstin`, `tin_no`, `transport`, `phone1`, `phone2`, `email1`, `email2`, `party_id`) VALUES
	('Gopal', 'asdfe', 'sdfasfdas', 'FSD', 'asgg34', 53455, 'grfsgt', 846965, 65416, 'sdfsdgf', 'hfh', 'BNGH5'),
	('vinit', '9 kamla nehru ', 'sadfasf', 'MP', 'FG5562', 9865322, 'narmada', 9865321, 986532134, 'vinit@gmail.com', 'drfgdg', 'DFG789');
/*!40000 ALTER TABLE `party` ENABLE KEYS */;

-- Dumping structure for table billmanagement.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `login_id` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `isLogin` varchar(50) NOT NULL,
  `company_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='delatils of user\r\nRequired for login';

-- Dumping data for table billmanagement.user: ~2 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `username`, `login_id`, `password`, `isLogin`, `company_id`) VALUES
	(7, 'vinit', 'J005', 'changeme', 'yes', 45),
	(8, 'shyam', '8', 'changeme', 'yes', 46);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
