/**
 * Creating source streams for each particular type of events in the input
 */

-- Stream of all the payments
CREATE OR REPLACE STREAM "Payment_Stream" ( 
	location				VARCHAR(64),
	COL_timestamp			TIMESTAMP,
	device					VARCHAR(64),
	items_count				INTEGER,
	collected				DECIMAL,
	discount				DECIMAL,
	tip						DECIMAL,
	tax						DECIMAL,
	fee						DECIMAL,
	tender_type				VARCHAR(32),
	tender_card_brand		VARCHAR(32),
	tender_card_suffix		INTEGER,
	tender_entry_method		VARCHAR(32)
);

CREATE OR REPLACE PUMP "Payment_Pump" AS INSERT INTO "Payment_Stream"
SELECT STREAM 
	location, COL_timestamp, device, 
	items_count, collected, discount, tip, tax, fee, 
	tender_type, tender_card_brand, tender_card_suffix, tender_entry_method 
FROM "SOURCE_SQL_STREAM_001"
WHERE 
	entry_type = 'PAYMENT' AND
	collected > 0;

-- Stream of all the items from payments
CREATE OR REPLACE STREAM "Item_Stream" ( 
	location				VARCHAR(64),
	COL_timestamp			TIMESTAMP,
	device					VARCHAR(64),
	name					VARCHAR(128),
	quantity				DECIMAL,
	category				VARCHAR(128),
	variation				VARCHAR(128),
	sku						VARCHAR(128),
	total					DECIMAL,
	single_quantity			DECIMAL,
	gross_sales				DECIMAL,
	net_sales				DECIMAL,
	modifier_type			VARCHAR(128),
	modifier_name			VARCHAR(128)
);

CREATE OR REPLACE PUMP "Item_Pump" AS INSERT INTO "Item_Stream"
SELECT STREAM 
	location, COL_timestamp, device, 
	name, quantity, category, variation, sku, 
	total, single_quantity, gross_sales, net_sales, 
	modifier_type, modifier_name 
FROM "SOURCE_SQL_STREAM_001"
WHERE 
	entry_type = 'ITEM';

-- Stream of all tracks in play history
CREATE OR REPLACE STREAM "Track_Stream" ( 
	location				VARCHAR(64),
	COL_timestamp			TIMESTAMP,
	name					VARCHAR(128),
	artist					VARCHAR(128),
	label					VARCHAR(128),
	album_name				VARCHAR(128),
	genres					VARCHAR(1024),
	album_popularity		INTEGER,
	release_date			VARCHAR(32),
	release_date_precision	VARCHAR(16),
	disc_number				INTEGER,
	track_number			INTEGER,
	track_popularity		INTEGER,
	duration				INTEGER,
	mode					INTEGER,
	tonality				INTEGER,
	time_signature			INTEGER,
	tempo					REAL,
	acousticness			REAL,
	danceability			REAL,
	energy					REAL,
	instrumentalness		REAL,
	liveness				REAL,
	loudness				REAL,
	speechiness				REAL,
	valence					REAL
);

CREATE OR REPLACE PUMP "Track_Pump" AS INSERT INTO "Track_Stream"
SELECT STREAM 
	location, COL_timestamp, 
	name, artist, label, album_name, 
	genres, album_popularity, release_date, release_date_precision, disc_number, track_number, 
	track_popularity, duration, mode, tonality, time_signature, tempo, 
	acousticness, danceability, energy, instrumentalness, liveness, loudness, speechiness, valence 
FROM "SOURCE_SQL_STREAM_001"
WHERE 
	entry_type = 'TRACK';

/**
 * Performing the basic analytics.
 */

-- Stream for payments analytics with tumbling windows 1 hour size
CREATE OR REPLACE STREAM "Payment_Analytics_Stream" ( 
	ingest_time				VARCHAR(32), 
	theHour					VARCHAR(32),
	location				VARCHAR(64),
	total_payments_count	INTEGER,
	total_items_count		INTEGER,
	total_discount_percent	REAL,
	collected_amount		DECIMAL
);

CREATE OR REPLACE PUMP "Payment_Analytics_Pump" AS INSERT INTO "Payment_Analytics_Stream"
SELECT STREAM 
	TIMESTAMP_TO_CHAR('yyyy-MM-dd HH:mm:ss', STEP("Payment_Stream".ROWTIME BY INTERVAL '1' HOUR))		AS ingest_time,
	TIMESTAMP_TO_CHAR('yyyy-MM-dd HH:mm:ss', MONOTONIC(FLOOR("Payment_Stream".COL_timestamp TO HOUR)))	AS theHour, 
	location, 
	COUNT(*) AS total_payments_count,
	SUM(items_count) AS total_items_count,
	SUM(discount / (discount + collected)) AS total_discount_percent,
	SUM(collected) AS collected_amount
FROM "Payment_Stream"
GROUP BY 
	location,
	STEP("Payment_Stream".ROWTIME BY INTERVAL '1' HOUR), 
	MONOTONIC(FLOOR("Payment_Stream".COL_timestamp TO HOUR));


-- Stream for play history analytics with tumbling windows 1 hour size
CREATE OR REPLACE STREAM "Track_Analytics_Stream" (
	ingest_time					VARCHAR(32), 
	theHour						VARCHAR(32),
	location					VARCHAR(64),
	tracks_count				INTEGER,
	sum_acousticness			REAL,
	sum_danceability			REAL,
	sum_energy					REAL,
	sum_instrumentalness		REAL,
	sum_liveness				REAL,
	sum_loudness				REAL,
	sum_speechiness				REAL,
	sum_valence					REAL,
	sum_duration				REAL,
	sum_tempo					REAL,
	min_acousticness			REAL,
	min_danceability			REAL,
	min_energy					REAL,
	min_instrumentalness		REAL,
	min_liveness				REAL,
	min_loudness				REAL,
	min_speechiness				REAL,
	min_valence					REAL,
	min_duration				REAL,
	min_tempo					REAL,
	max_acousticness			REAL,
	max_danceability			REAL,
	max_energy					REAL,
	max_instrumentalness		REAL,
	max_liveness				REAL,
	max_loudness				REAL,
	max_speechiness				REAL,
	max_valence					REAL,
	max_duration				REAL,
	max_tempo					REAL,
	std_acousticness			REAL,
	std_danceability			REAL,
	std_energy					REAL,
	std_instrumentalness		REAL,
	std_liveness				REAL,
	std_loudness				REAL,
	std_speechiness				REAL,
	std_valence					REAL,
	std_duration				REAL,
	std_tempo					REAL
);

CREATE OR REPLACE PUMP "Track_Analytics_Pump" AS INSERT INTO "Track_Analytics_Stream"
SELECT STREAM
	TIMESTAMP_TO_CHAR('yyyy-MM-dd HH:mm:ss', STEP("Track_Stream".ROWTIME BY INTERVAL '1' HOUR))		AS ingest_time,
	TIMESTAMP_TO_CHAR('yyyy-MM-dd HH:mm:ss', MONOTONIC(FLOOR("Track_Stream".COL_timestamp TO HOUR)))	AS theHour, 
	location,
	COUNT(*) AS tracks_count,
	SUM(acousticness) AS sum_acousticness,
	SUM(danceability) AS sum_danceability,
	SUM(energy) AS sum_energy,
	SUM(instrumentalness) AS sum_instrumentalness,
	SUM(liveness) AS sum_liveness,
	SUM(loudness) AS sum_loudness,
	SUM(speechiness) AS sum_speechiness,
	SUM(valence) AS sum_valence,
	SUM(duration) AS sum_duration,
	SUM(tempo) AS sum_tempo,
	MIN(acousticness) AS min_acousticness,
	MIN(danceability) AS min_danceability,
	MIN(energy) AS min_energy,
	MIN(instrumentalness) AS min_instrumentalness,
	MIN(liveness) AS min_liveness,
	MIN(loudness) AS min_loudness,
	MIN(speechiness) AS min_speechiness,
	MIN(valence) AS min_valence,
	MIN(duration) AS min_duration,
	MIN(tempo) AS min_tempo,
	MAX(acousticness) AS max_acousticness,
	MAX(danceability) AS max_danceability,
	MAX(energy) AS max_energy,
	MAX(instrumentalness) AS max_instrumentalness,
	MAX(liveness) AS max_liveness,
	MAX(loudness) AS max_loudness,
	MAX(speechiness) AS max_speechiness,
	MAX(valence) AS max_valence,
	MAX(duration) AS max_duration,
	MAX(tempo) AS max_tempo,
	STDDEV_SAMP(acousticness) AS std_acousticness,
	STDDEV_SAMP(danceability) AS std_danceability,
	STDDEV_SAMP(energy) AS std_energy,
	STDDEV_SAMP(instrumentalness) AS std_instrumentalness,
	STDDEV_SAMP(liveness) AS std_liveness,
	STDDEV_SAMP(loudness) AS std_loudness,
	STDDEV_SAMP(speechiness) AS std_speechiness,
	STDDEV_SAMP(valence) AS std_valence,
	STDDEV_SAMP(duration) AS std_duration,
	STDDEV_SAMP(tempo) AS std_tempo
FROM "Track_Stream"
GROUP BY
	location,
	STEP("Track_Stream".ROWTIME BY INTERVAL '1' HOUR), 
	MONOTONIC(FLOOR("Track_Stream".COL_timestamp TO HOUR));
