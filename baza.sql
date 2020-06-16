CREATE TABLE procesory (
procesory_id INT PRIMARY KEY IDENTITY(1,1),
nazwa VARCHAR(25),
cena FLOAT,
stan INT
);

CREATE TABLE koszyk (
koszyk_id INT PRIMARY KEY IDENTITY(1,1),
nazwa VARCHAR(25),
cena FLOAT,
);

CREATE TABLE wiadomosci (
wiadmosci_id INT PRIMARY KEY IDENTITY(1,1),
email VARCHAR(50),
wiadmosc VARCHAR(250)
);

INSERT INTO procesory (nazwa,cena,stan) 
VALUES ('yntol r7',499.99,90),
('yntol g9',599.99,70),
('amb 5',350.00,55),
('myszka 1',29.99,100),
('myszka 2',39.99,120),
('myszka 3',19.99,99),
('monitor 1',899.99,30),
('monitor 2',629.99,25),
('monitor 3',999.99,35);


