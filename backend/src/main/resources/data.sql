INSERT INTO Status (code)
VALUES ('ACTIVE'),
       ('INACTIVE');


INSERT INTO Categories (type, status_code)
VALUES ('SPORTS', 'ACTIVE'),
       ('FINANCE', 'ACTIVE'),
       ('MOVIES', 'ACTIVE');


INSERT INTO Channels (type, status_code)
VALUES ('EMAIL', 'ACTIVE'),
       ('SMS', 'ACTIVE'),
       ('PUSH_NOTIFICATION', 'ACTIVE');


INSERT INTO Users (first_name, last_name, phone_number, email, status_code)
VALUES ('Alice', 'Johnson', '5551234567', 'alice.johnson@example.com', 'ACTIVE'),
       ('Bob', 'Smith', '5552345678', 'bob.smith@example.com', 'ACTIVE'),
       ('Charlie', 'Brown', '5553456789', 'charlie.brown@example.com', 'ACTIVE'),
       ('David', 'Williams', '5554567890', 'david.williams@example.com', 'ACTIVE'),
       ('Eva', 'Green', '5555678901', 'eva.green@example.com', 'ACTIVE'),
       ('Frank', 'White', '5556789012', 'frank.white@example.com', 'ACTIVE'),
       ('Grace', 'Adams', '5557890123', 'grace.adams@example.com', 'ACTIVE'),
       ('Hannah', 'Miller', '5558901234', 'hannah.miller@example.com', 'ACTIVE'),
       ('Ivy', 'Wilson', '5559012345', 'ivy.wilson@example.com', 'ACTIVE'),
       ('Jack', 'Moore', '5550123456', 'jack.moore@example.com', 'ACTIVE'),
       ('Karen', 'Taylor', '5551234567', 'karen.taylor@example.com', 'ACTIVE'),
       ('Leo', 'Anderson', '5552345678', 'leo.anderson@example.com', 'ACTIVE');


INSERT INTO user_category (user_id, category_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 3),
       (6, 1),
       (6, 2),
       (6, 3),
       (7, 2),
       (7, 3),
       (8, 1),
       (8, 3),
       (9, 1),
       (9, 2),
       (9, 3),
       (10, 2),
       (10, 3),
       (11, 1),
       (11, 2),
       (11, 3),
       (12, 1),
       (12, 2),
       (12, 3);



INSERT INTO user_channel (user_id, channel_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 2),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 2),
       (6, 1),
       (6, 2),
       (7, 1),
       (7, 2),
       (8, 1),
       (8, 2),
       (9, 1),
       (9, 2),
       (10, 1),
       (10, 2),
       (11, 1),
       (11, 2),
       (12, 1),
       (12, 2);



INSERT INTO notifications (category_id, channel_id, user_id, sent_at, message)
VALUES (1, 1, 3, '2023-07-10 08:00:00-04:00', 'Ophiophagus hannah'),
       (2, 2, 7, '2023-07-11 09:00:00-05:00', 'Crotalus adamanteus'),
       (3, 3, 5, '2023-07-12 10:00:00-04:00', 'Bungarus fasciatus'),
       (1, 1, 10, '2023-07-13 11:00:00-05:00', 'Boa constrictor'),
       (2, 2, 4, '2023-07-14 12:00:00-04:00', 'Dendroaspis polylepis'),
       (3, 3, 8, '2023-07-15 13:00:00-05:00', 'Malayopython reticulatus'),
       (1, 1, 12, '2023-07-16 14:00:00-04:00', 'Ophiophagus hannah'),
       (2, 2, 2, '2023-07-17 15:00:00-05:00', 'Crotalus adamanteus'),
       (3, 3, 6, '2023-07-18 16:00:00-04:00', 'Bungarus fasciatus'),
       (1, 1, 11, '2023-07-19 17:00:00-05:00', 'Boa constrictor'),
       (2, 2, 9, '2023-07-20 18:00:00-04:00', 'Dendroaspis polylepis'),
       (3, 3, 1, '2023-07-21 19:00:00-05:00', 'Malayopython reticulatus'),
       (1, 1, 4, '2023-07-22 20:00:00-04:00', 'Ophiophagus hannah'),
       (2, 2, 8, '2023-07-23 21:00:00-05:00', 'Crotalus adamanteus'),
       (3, 3, 3, '2023-07-24 22:00:00-04:00', 'Bungarus fasciatus'),
       (1, 1, 7, '2023-07-25 23:00:00-05:00', 'Boa constrictor'),
       (2, 2, 12, '2023-07-26 00:00:00-04:00', 'Dendroaspis polylepis'),
       (3, 3, 5, '2023-07-27 01:00:00-05:00', 'Malayopython reticulatus'),
       (1, 1, 10, '2023-07-28 02:00:00-04:00', 'Ophiophagus hannah'),
       (2, 2, 2, '2023-07-29 03:00:00-05:00', 'Crotalus adamanteus'),
       (3, 3, 6, '2023-07-30 04:00:00-04:00', 'Bungarus fasciatus'),
       (1, 1, 11, '2023-07-31 05:00:00-05:00', 'Boa constrictor'),
       (2, 2, 8, '2023-08-01 06:00:00-04:00', 'Dendroaspis polylepis'),
       (3, 3, 4, '2023-08-02 07:00:00-05:00', 'Malayopython reticulatus'),
       (1, 1, 12, '2023-08-03 08:00:00-04:00', 'Ophiophagus hannah'),
       (2, 2, 1, '2023-08-04 09:00:00-05:00', 'Crotalus adamanteus'),
       (3, 3, 7, '2023-08-05 10:00:00-04:00', 'Bungarus fasciatus'),
       (1, 1, 9, '2023-08-06 11:00:00-05:00', 'Boa constrictor'),
       (2, 2, 3, '2023-08-07 12:00:00-04:00', 'Dendroaspis polylepis'),
       (3, 3, 11, '2023-08-08 13:00:00-05:00', 'Malayopython reticulatus'),
       (1, 1, 6, '2023-08-09 14:00:00-04:00', 'Ophiophagus hannah'),
       (2, 2, 10, '2023-08-10 15:00:00-05:00', 'Crotalus adamanteus'),
       (3, 3, 2, '2023-08-11 16:00:00-04:00', 'Bungarus fasciatus'),
       (1, 1, 12, '2023-08-12 17:00:00-05:00', 'Boa constrictor'),
       (2, 2, 4, '2023-08-13 18:00:00-04:00', 'Dendroaspis polylepis'),
       (3, 3, 8, '2023-08-14 19:00:00-05:00', 'Malayopython reticulatus');


