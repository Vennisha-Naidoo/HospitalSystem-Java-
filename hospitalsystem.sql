-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 22, 2021 at 05:56 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hospitalsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointmentdetails`
--

CREATE TABLE `appointmentdetails` (
  `AppointmentID` int(10) NOT NULL,
  `PatientName` varchar(20) NOT NULL,
  `PatientSurname` varchar(20) NOT NULL,
  `PatientID` varchar(13) NOT NULL,
  `DoctorName` varchar(20) NOT NULL,
  `DoctorSurname` varchar(20) NOT NULL,
  `DoctorID` varchar(13) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `RoomNumber` varchar(5) NOT NULL,
  `Status` varchar(10) NOT NULL,
  `Symptoms` varchar(150) NOT NULL,
  `Diagnosis` varchar(150) NOT NULL,
  `Treatment` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `appointmentdetails`
--

INSERT INTO `appointmentdetails` (`AppointmentID`, `PatientName`, `PatientSurname`, `PatientID`, `DoctorName`, `DoctorSurname`, `DoctorID`, `Date`, `Time`, `RoomNumber`, `Status`, `Symptoms`, `Diagnosis`, `Treatment`) VALUES
(5, 'Devi', 'Naidoo', '1100110', 'Tom', 'Dev', '998877665', '2021-10-11', '14:00:00', '3', 'Seen', 'cough', 'Influenza', ''),
(6, 'Vennisha', 'Naidoo', '0105310', 'Gona', 'Naidoo', '112233445', '2021-10-08', '13:30:00', '10', 'Seen', 'sneezing', 'influenza virus', 'Antibiotics'),
(12, 'Krishna', 'Naidoo', '1010101', 'Tom', 'Dev', '998877665', '2021-11-18', '10:30:00', '16', 'Pending', 'Headache', 'Pending', 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `doctordetails`
--

CREATE TABLE `doctordetails` (
  `DoctorID` varchar(13) NOT NULL,
  `DoctorName` varchar(20) NOT NULL,
  `DoctorSurname` varchar(20) NOT NULL,
  `DoctorPassword` varchar(10) NOT NULL,
  `Specialisation` varchar(50) NOT NULL,
  `Doctor_EmailAddress` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `doctordetails`
--

INSERT INTO `doctordetails` (`DoctorID`, `DoctorName`, `DoctorSurname`, `DoctorPassword`, `Specialisation`, `Doctor_EmailAddress`) VALUES
('112233445', 'Gona', 'Naidoo', 'Gon321', 'General', 'Gonan@gmail.com'),
('998877665', 'Tom', 'Dev', '123dt', 'Neurologist', 'dev@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `nursedetails`
--

CREATE TABLE `nursedetails` (
  `NurseID` varchar(13) NOT NULL,
  `NurseName` varchar(20) NOT NULL,
  `NurseSurname` varchar(20) NOT NULL,
  `NursePassword` varchar(10) NOT NULL,
  `Gender` varchar(6) NOT NULL,
  `NursePhoneNumber` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `nursedetails`
--

INSERT INTO `nursedetails` (`NurseID`, `NurseName`, `NurseSurname`, `NursePassword`, `Gender`, `NursePhoneNumber`) VALUES
('321456987', 'Kimeshan', 'Pillay', 'kimmyp17', 'Male', '0794569898');

-- --------------------------------------------------------

--
-- Table structure for table `patientdetails`
--

CREATE TABLE `patientdetails` (
  `PatientID` varchar(13) NOT NULL,
  `PatientName` varchar(20) NOT NULL,
  `PatientSurname` varchar(20) NOT NULL,
  `PatientPassword` varchar(10) NOT NULL,
  `PatientGender` varchar(6) NOT NULL,
  `PatientPhoneNumber` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `patientdetails`
--

INSERT INTO `patientdetails` (`PatientID`, `PatientName`, `PatientSurname`, `PatientPassword`, `PatientGender`, `PatientPhoneNumber`) VALUES
('0105310', 'Vennisha', 'Naidoo', 'ven123', 'Female', '0791515551'),
('1001001', 'Sai', 'Bhag', 'saib31', 'Male', '0798889696'),
('1010101', 'Krishna', 'Naidoo', 'krishna10', 'Male', '0792169265'),
('1100110', 'Devi', 'Naidoo', 'devi100', 'Female', '0834561703'),
('3131313', 'Suvesh', 'Varden', 'suviVar17', 'Male', '0784561708');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointmentdetails`
--
ALTER TABLE `appointmentdetails`
  ADD PRIMARY KEY (`AppointmentID`);

--
-- Indexes for table `doctordetails`
--
ALTER TABLE `doctordetails`
  ADD PRIMARY KEY (`DoctorID`);

--
-- Indexes for table `nursedetails`
--
ALTER TABLE `nursedetails`
  ADD PRIMARY KEY (`NurseID`);

--
-- Indexes for table `patientdetails`
--
ALTER TABLE `patientdetails`
  ADD PRIMARY KEY (`PatientID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointmentdetails`
--
ALTER TABLE `appointmentdetails`
  MODIFY `AppointmentID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
