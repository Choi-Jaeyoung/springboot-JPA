INSERT INTO USER
	(
			ID
		,	AGE
		,	NAME
		,	PASSWORD
	)
VALUES
	(
			'user001'
		,	12
		,	'최재영'
		,	'$2a$10$d9EJpo5HUmwY5P.xjwhQ7eMam8Z6AFv9zhL2iXR0.q8F/hdNei7om'
	);

INSERT INTO USER_DETAIL
	(
			ID
		,	MARKETINGYN
	)
VALUES
	(
			'user001'
		,	'N'
	);
	
INSERT INTO USER_DELIVERY
	(
			ID
		,	SEQ
		,	ADDRESS
		,	INSERTDT
	)
VALUES
	(
			'user001'
		,	1
		,	'가나다11111'
		,	'2021-06-01'
	)
,	(
			'user001'
		,	2
		,	'가나다22222'
		,	'2021-06-01'
	);