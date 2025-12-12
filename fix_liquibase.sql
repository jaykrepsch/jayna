-- Fix Liquibase checksum issue
DELETE FROM DATABASECHANGELOG 
WHERE ID = '20240617170000-1' 
AND AUTHOR = 'jayna' 
AND FILENAME = 'config/liquibase/changelog/20240617170000_update_electricity_formname.xml';
