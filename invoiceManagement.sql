-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.55-0ubuntu0.14.04.1 - (Ubuntu)
-- Server OS:                    debian-linux-gnu
-- HeidiSQL Version:             9.4.0.5174
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping data for table billmanagement.invoice: ~3 rows (approximately)
DELETE FROM `invoice`;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` (`item`, `party_id`, `invoice_date`, `invoice_number`, `dispatch_date`, `address`, `party_name`, `invoice_total`) VALUES
	('[{"item_sgst":"9.0","item_total_price":"90","item_id":"JB12","item_sgst_amount":"8.1","item_gst":"9.0","item_name":"Jaledi","item_total":"110.7","item_igst_amount":"0","item_igst":"0","item_gst_amount":"8.1","item_cess":"5","item_cess_amount":"4.5","item_quantity":"9"},{"item_sgst":"9.0","item_total_price":"90","item_id":"JB12","item_sgst_amount":"8.1","item_gst":"9.0","item_name":"Jaledi","item_total":"110.7","item_igst_amount":"0","item_igst":"0","item_gst_amount":"8.1","item_cess":"5","item_cess_amount":"4.5","item_quantity":"9"},{"item_sgst":"7.5","item_total_price":"340","item_id":"saa33","item_sgst_amount":"25.500002","item_gst":"7.5","item_name":"werw","item_total":"411.4","item_igst_amount":"0","item_igst":"0","item_gst_amount":"25.500002","item_cess":"6","item_cess_amount":"20.4","item_quantity":"10"}]', 'FIN75', '2017-08-22', 23, '2017-08-23', '15~New York Streat ', 'Finlander Corp.', 633),
	('[{"item_sgst":"9.0","item_total_price":"90","item_id":"JB12","item_sgst_amount":"8.1","item_gst":"9.0","item_name":"Jaledi","item_total":"110.7","item_igst_amount":"0","item_igst":"0","item_gst_amount":"8.1","item_cess":"5","item_cess_amount":"4.5","item_quantity":"9"}]', 'FIN75', '2017-08-13', 24, '2017-08-21', '15~ New York Streat ', 'Finlander Corp.', 111),
	('[{"item_sgst":"0%","item_total_price":"200","item_id":"AJF81","item_sgst_amount":"0","item_gst":"0%","item_cgst":"0","item_name":"Ajfhv jasd","item_total":"240.0","item_igst_amount":"30.000002","item_igst":"15%%","item_gst_amount":"0","item_cess":"5%","item_cess_amount":"10.0","item_quantity":"10"}]', 'DA96', '2017-08-14', 25, '2017-08-15', '10~ KhTI WALATA KJASND JKFFI', 'Damodar Sons', 240);
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;

-- Dumping data for table billmanagement.item: ~5 rows (approximately)
DELETE FROM `item`;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` (`item_name`, `item_id`, `price`, `item_code`, `rate`, `create_date`, `status`, `unit`, `cess`) VALUES
	('Ajfhv jasd', 'AJF81', 20, 'ID23', '15', '2017-08-14', 'true', 'Bags', '5'),
	('asdfasdf', 'ASD50', 50, 'ASD56', '15', '2017-08-14', 'true', 'Bags', '6'),
	('Bottle', 'BOT67', 20, 'BT26', '15', '2017-08-13', 'true', 'Bags', '5'),
	('Guns', 'GUN57', 560, 'GU45', '10', '2017-08-13', 'true', 'Nos.', '5'),
	('Mobiles', 'MOB16', 45000, 'MB456', '20', '2017-08-13', 'true', 'Nos.', '6');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;

-- Dumping data for table billmanagement.party: ~8 rows (approximately)
DELETE FROM `party`;
/*!40000 ALTER TABLE `party` DISABLE KEYS */;
INSERT INTO `party` (`party_name`, `address`, `address1`, `state`, `gstin`, `transport`, `phone1`, `phone2`, `email1`, `email2`, `party_id`) VALUES
	('Cocca Cola', '10 jsadf dsfjasfnd', '74sgsdg', 'Andhra Pradesh', '28JDSK09', 'sdfgsdtg', '56165', '541514', 'sdtghdg', 'sfgsdg', 'FGL90'),
	('Damodar Sons', '10, KhTI WALATA KJASND JKFFI', 'asdfasdfasdfasdf', 'Andhra Pradesh', '28ASDAS2635D6F3', 'jAI GOPALA', '9658965896', '9632587415', 'asdfasdfas@sdf.com', 'asdfasdfas@sdf.com', 'DA96'),
	('Finlander Corp.', '15~New York Streat ', '16~Kamhsdbc FJNsfd jdf', 'Mizoram', '15DFVDF2365C5C5', '', '9865326598', '', 'dsjkfhjkd@gmail.com', 'dsjkfhjkd@gmail.com', 'FIN75'),
	('Gopal', 'asdfe', 'sdfasfdas', 'Chandigarh', '04KJLK098', 'grfsgt', '846965', '65416', 'sdfsdgf', 'hfh', 'BNGH5'),
	('Jabalpur Motors', '15 palasia hera nagar hjagsdf', 'asdfa asdfja 10sdfsadh', 'Assam', '24JHJHJ3265S5S5', 'Jndfjng dfomd', '9887546532', '9887542132', 'ksjdfg@gmail.com', 'sahdbrgv@dsaf.com', 'JAB49'),
	('Tata Company', '10 lala nagar bihar', '10 lala nagar bihar', 'Bihar', '05DSJBHN56465', 'bajrang transport', '986532215465', '875421326565', 'sdfg@gmail.com', 'sdfg@gmail.com', 'SDAF51465'),
	('TATA consult', '164/AS4 Kasdbc Injdgh delhi', 'dsfgsdfg dfgsdfgvbdsf sdfgsdfg', 'Delhi', '07SAASD6532S1S1', 'Njkas Tysnd JKNCX', '986532215487', '', 'asdfcasdcf@sdafc.com', '', 'TAT98'),
	('vinit', '9 kamla nehru ', 'sadfasf', 'Jammu and Kashmir', '01SDJ989', 'narmada', '9865321', '986532134', 'vinit@gmail.com', 'drfgdg', 'DFG78');
/*!40000 ALTER TABLE `party` ENABLE KEYS */;

-- Dumping data for table billmanagement.state: ~36 rows (approximately)
DELETE FROM `state`;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` (`state_id`, `state_name`, `state_initials`, `state_code`, `state_type`) VALUES
	(1, 'Andhra Pradesh', '"AP"', 28, '"STATE"'),
	(2, 'Andaman and Nicobar Islands', '"AN"', 35, '"UT"'),
	(3, 'Arunachal Pradesh', '"AR"', 12, '"STATE"'),
	(4, 'Assam', '"AS"', 18, '"STATE"'),
	(5, 'Bihar', '"BR"', 10, '"STATE"'),
	(6, 'Chandigarh', '"CH"', 4, '"UT"'),
	(7, 'Chhattisgarh', '"CG"', 22, '"STATE"'),
	(8, 'Dadar and Nagar Haveli', '"DH"', 26, '"UT"'),
	(9, 'Daman and Diu', '"DD"', 25, '"UT"'),
	(10, 'Delhi', '"DL"', 7, '"UT"'),
	(11, 'Goa', '"GA"', 30, '"STATE"'),
	(12, 'Gujarat', '"GJ"', 24, '"STATE"'),
	(13, 'Haryana', '"HR"', 6, '"STATE"'),
	(14, 'Himachal Pradesh', '"HP"', 2, '"STATE"'),
	(15, 'Jammu and Kashmir', '"JK"', 1, '"STATE"'),
	(16, 'Jharkhand', '"JH"', 20, '"STATE"'),
	(17, 'Karnataka', '"KA"', 29, '"STATE"'),
	(18, 'Kerala', '"KL"', 32, '"STATE"'),
	(19, 'Lakshadweep', '"LD"', 31, '"UT"'),
	(20, 'Madhya Pradesh', '"MP"', 23, '"STATE"'),
	(21, 'Maharashtra', '"MH"', 27, '"STATE"'),
	(22, 'Manipur', '"MN"', 14, '"STATE"'),
	(23, 'Meghalaya', '"ML"', 17, '"STATE"'),
	(24, 'Mizoram', '"MZ"', 15, '"STATE"'),
	(25, 'Nagaland', '"NL"', 13, '"STATE"'),
	(26, 'Odisha', '"OR"', 21, '"STATE"'),
	(27, 'Pondicherry', '"PY"', 34, '"UT"'),
	(28, 'Punjab', '"PB"', 3, '"STATE"'),
	(29, 'Rajasthan', '"RJ"', 8, '"STATE"'),
	(30, 'Sikkim', '"SK"', 11, '"STATE"'),
	(31, 'Tamil Nadu', '"TN"', 33, '"STATE"'),
	(32, 'Telangana', '"TN"', 36, '"STATE"'),
	(33, 'Tripura', '"TR"', 16, '"STATE"'),
	(34, 'Uttar Pradesh', '"UP"', 9, '"STATE"'),
	(35, 'Uttarakhand', '"UK"', 5, '"STATE"'),
	(36, 'West Bangal', '"WB"', 19, '"STATE"');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;

-- Dumping data for table billmanagement.user: ~2 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `username`, `login_id`, `password`, `isLogin`, `company_id`, `state_code`) VALUES
	(7, 'vinit', 'J005', 'changeme', 'yes', 45, '26'),
	(8, 'shyam', '8', 'changeme', 'yes', 46, '15');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
