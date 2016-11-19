select
	e.id,
	e.description,
	c.description,
	l.description,
	hteam.description,
	ateam.description,
	e.starttime,
	e.status	
from
	/*=SCHEMA*/events e
	inner join /*=SCHEMA*/leagues l on e.leagueid = l.id
	inner join /*=SCHEMA*/countries c on e.countryid = c.id
	inner join /*=SCHEMA*/teams hteam on e.hometeamid = hteam.id 
	inner join /*=SCHEMA*/teams ateam on e.awayteamid = ateam.id
where
	e.id = :id