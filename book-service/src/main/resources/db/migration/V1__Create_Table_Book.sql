CREATE TABLE `book` (
                          `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
                          `author` longtext,
                          `launch_date` datetime(6),
                          `price` decimal(65,2),
                          `title` longtext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
