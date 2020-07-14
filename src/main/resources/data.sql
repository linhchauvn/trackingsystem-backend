-- user table 
INSERT INTO USERS (id, username) VALUES
	(1, 'Alice'),
  	(2, 'Bob'),
  	(3, 'Chris');

-- fileupload table
INSERT INTO FILEUPLOAD (id, filename, uploadtime, metadata_id, user_id) VALUES
 (1, 'Alice_offroad', '2020-07-14T09:41:33Z', null, 1),
 (2, 'Alice_touring', '2020-07-14T11:41:33Z', null, 1),
 (3, 'Bob_trekking', '2020-07-14T13:41:33Z', null, 2),
 (4, 'Chris_running', '2020-07-14T17:41:33Z', null, 1);
  
-- metadata table
INSERT INTO METADATA (id, author, gpxdescription, gpxname, linkhref, linktitle, time, fileupload_id) VALUES
	(1, 'Alice', 'Lets go to the jungle', 'Amazon offroad', 'http://www.oruxmaps.com', 'OruxMaps', '2020-07-12T09:41:33Z', 1),
	(2, 'Ecila','Rio de Janero goes around', 'Rio touring', 'http://www.oruxmaps.com', 'OruxMaps', '2020-07-13T09:41:33Z', 2),
	(3, 'BoB', 'Enter sandman', 'Sahara trekking', 'https://www.strava.com/', 'Strava', '2020-07-10T09:41:33Z', 3),
	(4, 'Chrx', 'Melbourne botanic garden', 'Morning run', 'https://www.strava.com/', 'Strava', '2020-07-09T09:41:33Z', 4);
-- set metadata_id to fileupload table
UPDATE FILEUPLOAD SET metadata_id = 1 WHERE id = 1;
UPDATE FILEUPLOAD SET metadata_id = 2 WHERE id = 2;
UPDATE FILEUPLOAD SET metadata_id = 3 WHERE id = 3;
UPDATE FILEUPLOAD SET metadata_id = 4 WHERE id = 4;

-- waypoint table
INSERT INTO WAYPOINT (id, latitude, longitude, name, symbol, fileupload_id) VALUES
	(1, 13.1313, 14.1414, 'A1', '/cdn/A1', 1),
	(2, 25.2525, 26.2626, 'A2', '/cdn/A2', 1),
	(3, 33.3333, 34.3434, 'A3', '/cdn/A3', 1),
	(4, 44.4444, 45.5555, 'E4', '/cdn/E4', 2),
	(5, 54.4444, 55.5555, 'E5', '/cdn/E5', 2),
	(6, 64.4444, 75.5555, 'B6', '/cdn/B6', 3),
	(7, 74.4444, 85.5555, 'C7', '/cdn/C7', 4);
	
-- tracksegment table
INSERT INTO TRACKSEGMENT (id, fileupload_id) VALUES
	(1, 1),
	(2, 1),
	(3, 1),
	(4, 2),
	(5, 2),
	(6, 3),
	(7, 4);
	
-- trackpoint table
INSERT INTO TRACKPOINT (id, elevation, latitude, longitude, time, tracksegment_id) VALUES
	(1, 11.111111, 12.121212, 13.131313, '2020-07-12T09:41:33Z', 1),
	(2, 14.141414, 15.151515, 16.161616, '2020-07-12T10:41:33Z', 1),
	(3, 17.141414, 18.151515, 19.161616, '2020-07-12T11:41:33Z', 1),
	(4, 21.141414, 22.151515, 23.161616, '2020-07-12T12:41:33Z', 2),
	(5, 31.141414, 32.151515, 33.161616, '2020-07-12T13:41:33Z', 3),
	(6, 41.141414, 42.151515, 43.161616, '2020-07-13T09:41:33Z', 4),
	(7, 51.141414, 52.151515, 53.161616, '2020-07-13T10:41:33Z', 5),
	(8, 61.141414, 62.151515, 63.161616, '2020-07-10T09:41:33Z', 6),
	(9, 71.141414, 72.151515, 73.161616, '2020-07-09T09:41:33Z', 7);