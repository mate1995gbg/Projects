-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 23, 2022 at 07:12 PM
-- Server version: 10.5.15-MariaDB-0+deb11u1
-- PHP Version: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mate0025`
--

-- --------------------------------------------------------

--
-- Table structure for table `Butikskop`
--

CREATE TABLE `Butikskop` (
  `KvittoNr` int(11) NOT NULL,
  `TotalPris` decimal(8,2) DEFAULT NULL,
  `Datum` date DEFAULT NULL,
  `Tid` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Butikskop`
--

INSERT INTO `Butikskop` (`KvittoNr`, `TotalPris`, `Datum`, `Tid`) VALUES
(1, '199.00', '2021-12-04', '08:24:12'),
(2, '1046.99', '2021-12-04', '08:59:12'),
(3, '3196.00', '2021-12-05', '10:30:33'),
(4, '1799.97', '2021-12-05', '11:27:35'),
(5, '1490.00', '2021-12-05', '12:34:04'),
(6, '644.25', '2021-12-05', '12:39:00'),
(7, '32.95', '2021-12-05', '13:08:12'),
(8, '2495.00', '2021-12-05', '14:21:06'),
(9, '898.99', '2021-12-06', '08:33:55'),
(10, '298.00', '2021-12-06', '09:10:05');

-- --------------------------------------------------------

--
-- Table structure for table `EnheterMatt`
--

CREATE TABLE `EnheterMatt` (
  `EnhetsNr` int(11) NOT NULL,
  `Matt` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `EnheterMatt`
--

INSERT INTO `EnheterMatt` (`EnhetsNr`, `Matt`) VALUES
(1, 'Centimeter'),
(2, 'Millimeter'),
(3, 'Decimeter'),
(4, 'Decameter'),
(5, 'Hektometer'),
(6, 'Meter'),
(7, 'Kilometer'),
(8, 'Megameter'),
(9, 'Gigameter'),
(10, 'Terameter');

-- --------------------------------------------------------

--
-- Table structure for table `EnheterVikt`
--

CREATE TABLE `EnheterVikt` (
  `EnhetsNr` int(11) NOT NULL,
  `Enhet` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `EnheterVikt`
--

INSERT INTO `EnheterVikt` (`EnhetsNr`, `Enhet`) VALUES
(1, 'Gram'),
(2, 'Kilogram'),
(3, 'Ton'),
(4, 'Milligram'),
(5, 'Mikrogram'),
(6, 'Nanogram'),
(7, 'Pikogram'),
(8, 'Megaton'),
(9, 'Gigaton'),
(10, 'Pound');

-- --------------------------------------------------------

--
-- Table structure for table `Inkopsfakturor`
--

CREATE TABLE `Inkopsfakturor` (
  `InkopsNr` int(11) NOT NULL,
  `TotalPris` decimal(8,2) NOT NULL,
  `Datum` date NOT NULL,
  `Betalstatus` tinyint(1) NOT NULL,
  `Leverantor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Inkopsfakturor`
--

INSERT INTO `Inkopsfakturor` (`InkopsNr`, `TotalPris`, `Datum`, `Betalstatus`, `Leverantor`) VALUES
(1, '6999.30', '2021-07-22', 0, 1),
(2, '4219.99', '2022-01-19', 0, 4),
(3, '2999.50', '2022-01-20', 0, 7),
(4, '7999.80', '2021-07-30', 1, 2),
(5, '7999.80', '2021-07-22', 0, 7),
(6, '3499.65', '2021-07-28', 0, 1),
(7, '9999.50', '2021-08-30', 0, 11),
(8, '99.95', '2021-07-22', 0, 9),
(9, '2999.50', '2021-10-22', 0, 12),
(10, '599.90', '2021-07-22', 0, 3);

-- --------------------------------------------------------

--
-- Table structure for table `InkoptaVaror`
--

CREATE TABLE `InkoptaVaror` (
  `InkopsNr` int(11) NOT NULL,
  `ProduktNr` int(11) NOT NULL,
  `Antal` int(11) NOT NULL,
  `StyckPris` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `InkoptaVaror`
--

INSERT INTO `InkoptaVaror` (`InkopsNr`, `ProduktNr`, `Antal`, `StyckPris`) VALUES
(1, 1, 50, '59.99'),
(1, 10, 20, '199.99'),
(2, 5, 10, '220.00'),
(2, 6, 10, '199.99'),
(3, 1, 50, '59.99'),
(3, 2, 50, '59.99'),
(4, 8, 20, '399.99'),
(5, 4, 500, '19.99'),
(6, 13, 10, '19.99'),
(6, 14, 25, '99.99'),
(7, 15, 25, '99.99'),
(7, 16, 25, '299.99'),
(8, 12, 5, '19.99'),
(9, 1, 50, '59.99'),
(10, 2, 10, '59.99');

-- --------------------------------------------------------

--
-- Table structure for table `Kategorier`
--

CREATE TABLE `Kategorier` (
  `KategoriNr` int(11) NOT NULL,
  `Namn` varchar(50) NOT NULL,
  `Beskrivning` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Kategorier`
--

INSERT INTO `Kategorier` (`KategoriNr`, `Namn`, `Beskrivning`) VALUES
(1, 'Isolering', 'Isoleringsmaterial för ex. fönster,väggar,tak och golv.'),
(2, 'Mura och Gjuta', 'Produkter som hör till cement och murbruk.'),
(3, 'Skivmaterial', 'Skivor av olika material så som MDF, gips m.m.'),
(4, 'Tak', 'Allt som hör till takbyggen.'),
(5, 'Fogmaterial & Tätning', 'Alla typer av produkter som hör till tätning finns här.'),
(7, 'Spik', 'Spikar av olika slag.'),
(8, 'Skruv', 'Skruvar av olika slag.'),
(9, 'Mutter', 'Muttrar av olika slag.'),
(10, 'Trä och Virke', 'Trä och virke för alla typer av arbeten.'),
(11, 'Handverktyg', 'Verktyg som inte är elektriska återfinns här.'),
(12, 'Elektriska Verktyg', 'Motorsågar, Elborr m.m.'),
(13, 'Kemiska Produkter', 'Miljö- eller hälsofarliga produkter såsom lim, bets m.m. återfinns här.'),
(14, 'Okategoriserade Produkter', 'Produkter som inte hör till någon specifik kategori.'),
(15, 'Färgprodukter', 'Färg för olika typer av applikationer.'),
(16, 'VVS', 'Rör & kontakter, m.m.');

-- --------------------------------------------------------

--
-- Table structure for table `Kundfakturor`
--

CREATE TABLE `Kundfakturor` (
  `FakturaNr` int(11) NOT NULL,
  `TotalSumma` decimal(8,2) NOT NULL,
  `OCR` int(11) NOT NULL,
  `BetalaSenast` date NOT NULL,
  `BetalStatus` tinyint(1) NOT NULL,
  `Month` varchar(50) NOT NULL,
  `Year` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Kundfakturor`
--

INSERT INTO `Kundfakturor` (`FakturaNr`, `TotalSumma`, `OCR`, `BetalaSenast`, `BetalStatus`, `Month`, `Year`) VALUES
(1, '9310.94', 123456789, '2022-04-09', 0, 'Mars', 2022),
(2, '898.99', 234234234, '2021-09-15', 1, 'Augusti', 2021),
(3, '2394.00', 345345345, '2022-02-19', 1, 'Februari', 2022),
(4, '1207.75', 345347897, '2022-01-29', 1, 'December', 2021),
(5, '2980.00', 78678567, '2022-04-25', 0, 'Februari', 2022),
(6, '42.95', 67436252, '2020-05-05', 1, 'April', 2020),
(7, '3999.50', 23421211, '2021-10-14', 1, 'September', 2021),
(8, '2394.00', 34587843, '2022-02-03', 1, 'Januari', 2022),
(9, '1490.00', 23456464, '2022-02-11', 1, 'Januari', 2022),
(10, '999.98', 34348833, '2022-04-08', 0, 'Mars', 2022);

-- --------------------------------------------------------

--
-- Table structure for table `Kundordrar`
--

CREATE TABLE `Kundordrar` (
  `OrderNr` int(11) NOT NULL,
  `FakturaNr` int(11) DEFAULT NULL,
  `KundNr` int(11) NOT NULL,
  `Orderdatum` date NOT NULL,
  `TotalPris` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Kundordrar`
--

INSERT INTO `Kundordrar` (`OrderNr`, `FakturaNr`, `KundNr`, `Orderdatum`, `TotalPris`) VALUES
(1, 1, 5, '2022-03-04', '3020.00'),
(2, 1, 5, '2022-03-07', '894.00'),
(3, 1, 5, '2022-03-15', '5396.94'),
(5, 2, 7, '2021-08-02', '898.99'),
(6, 3, 6, '2022-01-19', '2394.00'),
(7, 4, 4, '2021-12-29', '1043.00'),
(8, 4, 4, '2021-12-20', '163.75'),
(9, 5, 9, '2022-03-25', '2980.00'),
(10, 6, 1, '2020-04-05', '42.95'),
(11, 7, 1, '2020-09-14', '3999.50'),
(12, 8, 2, '2022-01-03', '2394.00'),
(13, 9, 3, '2022-01-11', '1490.00'),
(14, 10, 5, '2022-03-08', '999.98');

-- --------------------------------------------------------

--
-- Table structure for table `Leverantorer`
--

CREATE TABLE `Leverantorer` (
  `LeverantorNr` int(11) NOT NULL,
  `ForetagsNamn` varchar(80) DEFAULT NULL,
  `OrgNr` varchar(50) DEFAULT NULL,
  `BankGiro` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Leverantorer`
--

INSERT INTO `Leverantorer` (`LeverantorNr`, `ForetagsNamn`, `OrgNr`, `BankGiro`) VALUES
(1, 'Järngrossisten i Falköping AB', '5580-683000', '5580-1010'),
(2, 'Trämästaren AB', '5580-584498', '5565-9434'),
(3, 'Göteborgs Kem AB', '5581-992365', '5090-1012'),
(4, 'Norgips AB', '5580-984434', '6510-1230'),
(5, 'Nordsjö AB', '5580-039544', '5110-9501'),
(7, 'Verktygsmästarn AB ', '5582-585431', '0190-2020'),
(8, 'Trollhättans Kemigrossist AB', '5581-674001', '1002-9120'),
(9, 'Järnia AB', '5580-109493', '5101-2222'),
(10, 'Solid VVS AB', '5580-395443', '3020-1010'),
(11, 'Backmans Maskin AB', '5580-033433', '1290-5102'),
(12, 'Cementgrossisten AB', '5580-980043', '2109-1010');

-- --------------------------------------------------------

--
-- Table structure for table `LeverantorerKontaktuppgifter`
--

CREATE TABLE `LeverantorerKontaktuppgifter` (
  `LeverantorNr` int(11) NOT NULL,
  `Kontaktperson` varchar(50) DEFAULT NULL,
  `Telefonnummer` varchar(50) NOT NULL,
  `Mejladress` varchar(70) NOT NULL,
  `KontaktNr` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `LeverantorerKontaktuppgifter`
--

INSERT INTO `LeverantorerKontaktuppgifter` (`LeverantorNr`, `Kontaktperson`, `Telefonnummer`, `Mejladress`, `KontaktNr`) VALUES
(1, 'Annika Svensson', '070-304050', 'annika.svensson@jarngrossisten.se', 1),
(2, 'Emma Karlsson', '071-853003', 'logistik@tramastaren.se', 2),
(3, 'Markus Andersson', '071-940504', 'markus.saljare@gbgkem.se', 3),
(4, 'Andreas Martinsson', '070-343434', 'andreas@norgips.com', 4),
(7, 'Andrija Pezer', '071-903033', 'verktygsmastarn.andrija@outlook.se', 5),
(8, 'Peter Griffin', '072-903030', 'peter@trollhattankem.se', 6),
(9, 'Fredrik Svensson', '070-912843', 'fredrik.svensson@jarnia.se', 7),
(10, 'Senka Music', '071-343788', 'business@solidvvs.se', 8),
(11, 'Bert Karlsson', '071-558120', 'bert.karlsson@backmans.nu', 9),
(12, 'Telefonsluss ', '070-224488', 'leverans@cementgrossisten.se', 10),
(1, 'Peter Svensson', '070-304051', 'peter.svensson@jarngrossisten.se', 11);

-- --------------------------------------------------------

--
-- Table structure for table `Produkter`
--

CREATE TABLE `Produkter` (
  `ProduktNr` int(11) NOT NULL,
  `Namn` varchar(50) NOT NULL,
  `Leverantor` int(11) NOT NULL,
  `Tillverkare` int(11) NOT NULL,
  `Beskrivning` varchar(100) DEFAULT NULL,
  `SaljPris` decimal(8,2) NOT NULL,
  `Lagersaldo` int(11) NOT NULL,
  `Kategori` int(11) NOT NULL,
  `EAN` bigint(20) NOT NULL,
  `Utgatt` tinyint(1) NOT NULL,
  `ErsattMed` int(11) DEFAULT NULL,
  `LagerPlats` int(11) DEFAULT NULL,
  `Vikt` decimal(8,3) NOT NULL,
  `AntalPerForpackning` int(11) NOT NULL,
  `Langd` decimal(8,3) NOT NULL,
  `Bredd` decimal(8,3) NOT NULL,
  `Djup` decimal(8,3) NOT NULL,
  `EnhetVikt` int(11) NOT NULL,
  `EnhetMatt` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Produkter`
--

INSERT INTO `Produkter` (`ProduktNr`, `Namn`, `Leverantor`, `Tillverkare`, `Beskrivning`, `SaljPris`, `Lagersaldo`, `Kategori`, `EAN`, `Utgatt`, `ErsattMed`, `LagerPlats`, `Vikt`, `AntalPerForpackning`, `Langd`, `Bredd`, `Djup`, `EnhetVikt`, `EnhetMatt`) VALUES
(1, 'Spik ', 1, 4, '', '149.00', 59, 7, 978020137962, 0, NULL, 1, '0.010', 500, '5.000', '0.200', '0.200', 2, 1),
(2, 'Hammarspik', 1, 4, 'VFZ', '32.95', 13, 7, 6305056832815, 0, NULL, 18, '0.010', 100, '5.000', '0.200', '0.200', 2, 1),
(3, 'Spånskiveskruv', 1, 4, '', '149.00', 6, 8, 7821695302537, 1, NULL, 10, '0.010', 250, '3.000', '0.420', '0.420', 2, 1),
(4, 'Betongskruv', 1, 4, '', '42.95', 699, 8, 4181998615271, 0, NULL, 2, '0.010', 1, '11.000', '1.200', '1.200', 2, 1),
(5, 'Skruvdragare MT590', 7, 1, 'Batteri säljs separat, ProduktNr 6.', '499.99', 5, 12, 1064537237353, 0, NULL, 3, '2.100', 1, '19.000', '5.000', '18.000', 2, 1),
(6, 'MT-serien Batteri ', 7, 1, 'Batteri till Bosch MT-serie av verktyg.\r\n6000mAh', '399.00', 4, 12, 1064537237352, 0, NULL, 9, '2.500', 1, '6.000', '4.200', '4.200', 2, 1),
(8, 'MFT-skiva ', 4, 7, '', '799.00', 33, 3, 2309456775806, 0, NULL, 5, '10.200', 1, '50.000', '25.000', '1.200', 2, 1),
(9, 'Cementpulver ', 12, 9, 'Räcker till cirka 20 liter cement. ', '599.99', 0, 2, 5532188850177, 1, 10, 4, '15.000', 1, '30.000', '18.000', '12.000', 2, 1),
(10, 'Cementpulver ', 12, 8, 'Räcker till cirka 16 liter cement. ', '499.99', 20, 2, 6552849772937, 0, NULL, 6, '13.000', 1, '18.000', '17.000', '13.000', 2, 1),
(12, 'Superlim', 8, 10, '', '79.99', 40, 13, 93848444333, 0, NULL, 7, '0.050', 1, '10.000', '3.000', '3.000', 2, 1),
(13, 'Spackelspade ', 7, 3, 'liten storlek.', '49.99', 10, 11, 82342342190, 0, NULL, 8, '0.100', 1, '15.000', '7.000', '2.500', 2, 1),
(14, 'Hammare med spikklo', 7, 3, '', '199.99', 10, 11, 23423423421, 0, NULL, 11, '4.500', 1, '20.000', '3.000', '10.000', 2, 1),
(15, 'Träskiva', 2, 3, '', '399.95', 45, 3, 23423422212, 0, NULL, 12, '4.000', 1, '50.000', '25.000', '2.600', 2, 1),
(16, 'Träplank', 2, 3, '', '499.99', 45, 3, 23423422211, 0, NULL, 13, '3.000', 1, '20.000', '125.000', '3.000', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `SaldaVarorButik`
--

CREATE TABLE `SaldaVarorButik` (
  `ProduktNr` int(11) NOT NULL,
  `KvittoNr` int(11) NOT NULL,
  `Antal` int(11) NOT NULL,
  `StyckPris` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `SaldaVarorButik`
--

INSERT INTO `SaldaVarorButik` (`ProduktNr`, `KvittoNr`, `Antal`, `StyckPris`) VALUES
(1, 5, 10, '149.00'),
(1, 10, 2, '149.00'),
(2, 7, 1, '32.95'),
(3, 2, 1, '447.00'),
(4, 6, 15, '42.95'),
(5, 9, 1, '499.99'),
(6, 1, 1, '199.00'),
(6, 9, 1, '399.00'),
(8, 3, 5, '639.20'),
(9, 2, 1, '599.99'),
(9, 4, 3, '599.99'),
(10, 8, 5, '499.00');

-- --------------------------------------------------------

--
-- Table structure for table `SaldaVarorOrder`
--

CREATE TABLE `SaldaVarorOrder` (
  `OrderNr` int(11) NOT NULL,
  `ProduktNr` int(11) NOT NULL,
  `Antal` int(11) NOT NULL,
  `StyckPris` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `SaldaVarorOrder`
--

INSERT INTO `SaldaVarorOrder` (`OrderNr`, `ProduktNr`, `Antal`, `StyckPris`) VALUES
(1, 8, 4, '755.00'),
(2, 1, 3, '447.00'),
(2, 3, 3, '447.00'),
(3, 8, 3, '799.00'),
(3, 10, 6, '499.99'),
(5, 5, 1, '499.99'),
(5, 6, 1, '399.00'),
(6, 10, 6, '399.00'),
(7, 1, 7, '149.00'),
(8, 2, 5, '32.95'),
(9, 3, 20, '149.00'),
(10, 4, 1, '42.95'),
(11, 8, 5, '799.00'),
(12, 6, 6, '399.00'),
(13, 3, 10, '149.00'),
(14, 5, 2, '999.98');

-- --------------------------------------------------------

--
-- Table structure for table `Stamkunder`
--

CREATE TABLE `Stamkunder` (
  `KundNr` int(11) NOT NULL,
  `ForetagsNamn` varchar(50) NOT NULL,
  `Gata` varchar(100) DEFAULT NULL,
  `Stad` varchar(50) DEFAULT NULL,
  `PostNr` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Stamkunder`
--

INSERT INTO `Stamkunder` (`KundNr`, `ForetagsNamn`, `Gata`, `Stad`, `PostNr`) VALUES
(1, 'Trä & Trö AB', 'Musikvägen 5', 'Linköping', 12345),
(2, 'Finns Byggfirma', 'Byggvägen 6', 'Byggköping', 23456),
(3, 'Marco\'s Trähandel', 'Engelbrektsgatan 20', 'Borås', 34567),
(4, 'Fem Fingrar Bygg', 'Busaregatan 10', 'Mölndal', 45678),
(5, 'PEAB AB', 'Någontinggatan 55', 'Göteborg', 56789),
(6, 'Jeffs Metall AB', 'Cellofonvägen 66', 'Partille', 67890),
(7, 'Omars Rör & Rens', 'Pezervägen 5', 'Partille', 98762),
(8, 'Byggmästaren AB', 'Lindholmspiren 5', 'Göteborg', 98765),
(9, 'Byggex AB', 'Inspirationsgatan 13', 'Vänersborg', 87654),
(10, 'Metallhantverk AB', 'Falkgatan 56', 'Vänersborg', 76542);

-- --------------------------------------------------------

--
-- Table structure for table `StamkunderKontaktuppgifter`
--

CREATE TABLE `StamkunderKontaktuppgifter` (
  `KontaktNr` int(11) NOT NULL,
  `KundNr` int(11) NOT NULL,
  `Mejladress` varchar(50) NOT NULL,
  `Telefonnummer` varchar(50) NOT NULL,
  `Kontaktperson` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `StamkunderKontaktuppgifter`
--

INSERT INTO `StamkunderKontaktuppgifter` (`KontaktNr`, `KundNr`, `Mejladress`, `Telefonnummer`, `Kontaktperson`) VALUES
(1, 1, 'Wilf.Traochtro@gmail.com', '073-956655', 'Wilf Träman'),
(2, 2, 'inkop2@finnsbygg.com', '070-952233', 'Martin Karlsson'),
(3, 3, 'inkop.marco@trähandel.se', '073-123566', 'Marco Teran'),
(4, 4, 'sälj@femfingrar.nu', '070-915500', 'Markus Öhlander'),
(5, 5, 'inkopsarende@peab.se', '072-905566', 'Teo Vänt'),
(6, 6, 'raees.mcbride@jeffmetall.se', '071-003344', 'Raees Mcbride'),
(8, 7, 'Eleri@omarsrorrens.se', '073-005599', 'Eleri Golden'),
(9, 8, 'aaron.byggmastaren@hotmail.com', '072-152030', 'Aaron Marsh'),
(10, 9, 'aaron.byggex@hotmail.com', '072-126944', 'Aaron Taft'),
(11, 10, 'Lillianbauer@metallhantverk.se', '070-663049', 'Lillian Bauer'),
(12, 6, 'emile.mcbride@jeffmetall.se', '071-003344', 'Emile Mcbride');

-- --------------------------------------------------------

--
-- Table structure for table `Tillverkare`
--

CREATE TABLE `Tillverkare` (
  `TillverkarNr` int(11) NOT NULL,
  `TillverkarNamn` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Tillverkare`
--

INSERT INTO `Tillverkare` (`TillverkarNr`, `TillverkarNamn`) VALUES
(1, 'Bosch'),
(2, 'Makita'),
(3, 'Anderssons Spik & Trä AB'),
(4, 'Dyckert Pintos'),
(5, 'Senco'),
(6, 'Craftomat'),
(7, 'MFT'),
(8, 'Rapid'),
(9, 'Vida'),
(10, 'Moelven'),
(11, 'Powerworks'),
(12, 'Flügger Färg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Butikskop`
--
ALTER TABLE `Butikskop`
  ADD PRIMARY KEY (`KvittoNr`);

--
-- Indexes for table `EnheterMatt`
--
ALTER TABLE `EnheterMatt`
  ADD PRIMARY KEY (`EnhetsNr`);

--
-- Indexes for table `EnheterVikt`
--
ALTER TABLE `EnheterVikt`
  ADD PRIMARY KEY (`EnhetsNr`);

--
-- Indexes for table `Inkopsfakturor`
--
ALTER TABLE `Inkopsfakturor`
  ADD PRIMARY KEY (`InkopsNr`,`Leverantor`),
  ADD KEY `Leverantor` (`Leverantor`);

--
-- Indexes for table `InkoptaVaror`
--
ALTER TABLE `InkoptaVaror`
  ADD PRIMARY KEY (`InkopsNr`,`ProduktNr`),
  ADD KEY `ProduktNr` (`ProduktNr`);

--
-- Indexes for table `Kategorier`
--
ALTER TABLE `Kategorier`
  ADD PRIMARY KEY (`KategoriNr`);

--
-- Indexes for table `Kundfakturor`
--
ALTER TABLE `Kundfakturor`
  ADD PRIMARY KEY (`FakturaNr`);

--
-- Indexes for table `Kundordrar`
--
ALTER TABLE `Kundordrar`
  ADD PRIMARY KEY (`OrderNr`),
  ADD KEY `FakturaNr` (`FakturaNr`),
  ADD KEY `KundNr` (`KundNr`);

--
-- Indexes for table `Leverantorer`
--
ALTER TABLE `Leverantorer`
  ADD PRIMARY KEY (`LeverantorNr`);

--
-- Indexes for table `LeverantorerKontaktuppgifter`
--
ALTER TABLE `LeverantorerKontaktuppgifter`
  ADD PRIMARY KEY (`KontaktNr`),
  ADD KEY `LeverantorNr` (`LeverantorNr`);

--
-- Indexes for table `Produkter`
--
ALTER TABLE `Produkter`
  ADD PRIMARY KEY (`ProduktNr`),
  ADD UNIQUE KEY `EAN` (`EAN`),
  ADD UNIQUE KEY `LagerPlats` (`LagerPlats`),
  ADD KEY `Leverantor` (`Leverantor`),
  ADD KEY `Tillverkare` (`Tillverkare`),
  ADD KEY `Kategori` (`Kategori`),
  ADD KEY `ErsattMed` (`ErsattMed`),
  ADD KEY `EnhetVikt` (`EnhetVikt`),
  ADD KEY `EnhetMatt` (`EnhetMatt`);

--
-- Indexes for table `SaldaVarorButik`
--
ALTER TABLE `SaldaVarorButik`
  ADD PRIMARY KEY (`ProduktNr`,`KvittoNr`),
  ADD KEY `KvittoNr` (`KvittoNr`,`ProduktNr`) USING BTREE;

--
-- Indexes for table `SaldaVarorOrder`
--
ALTER TABLE `SaldaVarorOrder`
  ADD PRIMARY KEY (`OrderNr`,`ProduktNr`),
  ADD KEY `ProduktNr` (`ProduktNr`,`OrderNr`) USING BTREE;

--
-- Indexes for table `Stamkunder`
--
ALTER TABLE `Stamkunder`
  ADD PRIMARY KEY (`KundNr`);

--
-- Indexes for table `StamkunderKontaktuppgifter`
--
ALTER TABLE `StamkunderKontaktuppgifter`
  ADD PRIMARY KEY (`KontaktNr`),
  ADD KEY `KundNr` (`KundNr`);

--
-- Indexes for table `Tillverkare`
--
ALTER TABLE `Tillverkare`
  ADD PRIMARY KEY (`TillverkarNr`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Butikskop`
--
ALTER TABLE `Butikskop`
  MODIFY `KvittoNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `EnheterMatt`
--
ALTER TABLE `EnheterMatt`
  MODIFY `EnhetsNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `EnheterVikt`
--
ALTER TABLE `EnheterVikt`
  MODIFY `EnhetsNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Inkopsfakturor`
--
ALTER TABLE `Inkopsfakturor`
  MODIFY `InkopsNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Kategorier`
--
ALTER TABLE `Kategorier`
  MODIFY `KategoriNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `Kundfakturor`
--
ALTER TABLE `Kundfakturor`
  MODIFY `FakturaNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Kundordrar`
--
ALTER TABLE `Kundordrar`
  MODIFY `OrderNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `Leverantorer`
--
ALTER TABLE `Leverantorer`
  MODIFY `LeverantorNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `LeverantorerKontaktuppgifter`
--
ALTER TABLE `LeverantorerKontaktuppgifter`
  MODIFY `KontaktNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `Produkter`
--
ALTER TABLE `Produkter`
  MODIFY `ProduktNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `Stamkunder`
--
ALTER TABLE `Stamkunder`
  MODIFY `KundNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `StamkunderKontaktuppgifter`
--
ALTER TABLE `StamkunderKontaktuppgifter`
  MODIFY `KontaktNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `Tillverkare`
--
ALTER TABLE `Tillverkare`
  MODIFY `TillverkarNr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Inkopsfakturor`
--
ALTER TABLE `Inkopsfakturor`
  ADD CONSTRAINT `Inkopsfakturor_ibfk_1` FOREIGN KEY (`Leverantor`) REFERENCES `Leverantorer` (`LeverantorNr`);

--
-- Constraints for table `InkoptaVaror`
--
ALTER TABLE `InkoptaVaror`
  ADD CONSTRAINT `InkoptaVaror_ibfk_1` FOREIGN KEY (`InkopsNr`) REFERENCES `Inkopsfakturor` (`InkopsNr`),
  ADD CONSTRAINT `InkoptaVaror_ibfk_2` FOREIGN KEY (`ProduktNr`) REFERENCES `Produkter` (`ProduktNr`);

--
-- Constraints for table `Kundordrar`
--
ALTER TABLE `Kundordrar`
  ADD CONSTRAINT `Kundordrar_ibfk_1` FOREIGN KEY (`FakturaNr`) REFERENCES `Kundfakturor` (`FakturaNr`),
  ADD CONSTRAINT `Kundordrar_ibfk_2` FOREIGN KEY (`KundNr`) REFERENCES `Stamkunder` (`KundNr`);

--
-- Constraints for table `LeverantorerKontaktuppgifter`
--
ALTER TABLE `LeverantorerKontaktuppgifter`
  ADD CONSTRAINT `LeverantorerKontaktuppgifter_ibfk_1` FOREIGN KEY (`LeverantorNr`) REFERENCES `Leverantorer` (`LeverantorNr`),
  ADD CONSTRAINT `LeverantorerKontaktuppgifter_ibfk_2` FOREIGN KEY (`LeverantorNr`) REFERENCES `Leverantorer` (`LeverantorNr`);

--
-- Constraints for table `Produkter`
--
ALTER TABLE `Produkter`
  ADD CONSTRAINT `Produkter_ibfk_1` FOREIGN KEY (`Leverantor`) REFERENCES `Leverantorer` (`LeverantorNr`),
  ADD CONSTRAINT `Produkter_ibfk_2` FOREIGN KEY (`Tillverkare`) REFERENCES `Tillverkare` (`TillverkarNr`),
  ADD CONSTRAINT `Produkter_ibfk_3` FOREIGN KEY (`Kategori`) REFERENCES `Kategorier` (`KategoriNr`),
  ADD CONSTRAINT `Produkter_ibfk_4` FOREIGN KEY (`ErsattMed`) REFERENCES `Produkter` (`ProduktNr`),
  ADD CONSTRAINT `Produkter_ibfk_5` FOREIGN KEY (`EnhetVikt`) REFERENCES `EnheterVikt` (`EnhetsNr`),
  ADD CONSTRAINT `Produkter_ibfk_6` FOREIGN KEY (`EnhetMatt`) REFERENCES `EnheterMatt` (`EnhetsNr`);

--
-- Constraints for table `SaldaVarorButik`
--
ALTER TABLE `SaldaVarorButik`
  ADD CONSTRAINT `SaldaVarorButik_ibfk_1` FOREIGN KEY (`ProduktNr`) REFERENCES `Produkter` (`ProduktNr`),
  ADD CONSTRAINT `SaldaVarorButik_ibfk_2` FOREIGN KEY (`KvittoNr`) REFERENCES `Butikskop` (`KvittoNr`);

--
-- Constraints for table `SaldaVarorOrder`
--
ALTER TABLE `SaldaVarorOrder`
  ADD CONSTRAINT `SaldaVarorOrder_ibfk_1` FOREIGN KEY (`OrderNr`) REFERENCES `Kundordrar` (`OrderNr`),
  ADD CONSTRAINT `SaldaVarorOrder_ibfk_2` FOREIGN KEY (`ProduktNr`) REFERENCES `Produkter` (`ProduktNr`),
  ADD CONSTRAINT `SaldaVarorOrder_ibfk_3` FOREIGN KEY (`OrderNr`) REFERENCES `Kundordrar` (`OrderNr`);

--
-- Constraints for table `StamkunderKontaktuppgifter`
--
ALTER TABLE `StamkunderKontaktuppgifter`
  ADD CONSTRAINT `StamkunderKontaktuppgifter_ibfk_1` FOREIGN KEY (`KundNr`) REFERENCES `Stamkunder` (`KundNr`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
