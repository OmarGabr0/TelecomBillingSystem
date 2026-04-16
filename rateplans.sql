INSERT INTO rateplan (rateplan_id,name,ROR,description,plan_price) VALUES (
1,'My_Life_150',0.19,'first_rate_plan',150);


INSERT INTO rateplan (rateplan_id,name,ROR,description,plan_price) VALUES (
2,'My_Life_250',0.19,'second_rate_plan',250);

INSERT INTO rateplan (rateplan_id,name,ROR,description,plan_price) VALUES (
3,'My_Life_350',0.19,'third_rate_plan',350);

----- zones for non local calls
INSERT INTO tariff_zone VALUES (0,016,'local','local calls for TelecoSmart','TelecoSmart');
INSERT INTO tariff_zone VALUES (1,012,'non local','non local calls for orange','orange');

------------ service packages 
--- for first rate plan My life 150
---BEGIN;
INSERT INTO service_package (service_type,description,rating_price,free_units,zone_id)
VALUES (1,'Voice service package',0.05,1000,1);

INSERT INTO service_rateplan (service_id,rateplan_id)  VALUES(1,1);
--COMMIT;

---BEGIN;
INSERT INTO service_package (service_id,service_type,description,rating_price,free_units,zone_id) VALUES (2,2,'SMS service package',0.01,300,1);
INSERT INTO service_rateplan (service_id,rateplan_id) VALUES(2,1);

---COMMIT;

---BEGIN;
INSERT INTO service_package (service_id,service_type,description,rating_price,free_units,zone_id) VALUES (3,3,'DATA service package',0.30,20000,0);
INSERT INTO service_rateplan (service_id,rateplan_id) VALUES(3,1);

---COMMIT;
------------------------------------------------
--- for second rate plan My life 250 


INSERT INTO service_package (service_id,service_type,description,rating_price,free_units,zone_id)
VALUES (4,1,'Voice service package',0.05,2000,0);

INSERT INTO service_rateplan (service_id,rateplan_id)  VALUES(4,2);
--COMMIT;

---BEGIN;
INSERT INTO service_package (service_id,service_type,description,rating_price,free_units,zone_id) VALUES (5,2,'SMS service package',0.01,500,0);
INSERT INTO service_rateplan (service_id,rateplan_id) VALUES(5,2);

---COMMIT;

---BEGIN;
INSERT INTO service_package (service_id,service_type,description,rating_price,free_units,zone_id) VALUES (6,3,'DATA service package',0.30,30000,0);
INSERT INTO service_rateplan (service_id,rateplan_id) VALUES(6,2);

---COMMIT;



------------------------------------------------
--- for third rate plan My life 350 


INSERT INTO service_package (service_id,service_type,description,rating_price,free_units,zone_id)
VALUES (7,1,'Voice service package',0.05,3000,0);

INSERT INTO service_rateplan (service_id,rateplan_id)  VALUES(7,3);
--COMMIT;

---BEGIN;
INSERT INTO service_package (service_id,service_type,description,rating_price,free_units,zone_id) VALUES (8,2,'SMS service package',0.01,1000,0);
INSERT INTO service_rateplan (service_id,rateplan_id) VALUES(8,3);

---COMMIT;

---BEGIN;
INSERT INTO service_package (service_id,service_type,description,rating_price,free_units,zone_id) VALUES (9,3,'DATA service package',0.30,5000,0);
INSERT INTO service_rateplan (service_id,rateplan_id) VALUES(9,3);

---COMMIT;