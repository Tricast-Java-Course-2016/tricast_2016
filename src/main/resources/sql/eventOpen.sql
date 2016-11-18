select
	e.id,
	l.description,
	c.description,
	hteam.description,
	ateam.description,
	e.description,
	e.starttime
from
	/*=SCHEMA*/events e
	inner join /*=SCHEMA*/leagues l on e.leagueid = l.id
	inner join /*=SCHEMA*/countries c on e.countryid = c.id
	inner join /*=SCHEMA*/teams hteam on e.hometeamid = hteam.id 
	inner join /*=SCHEMA*/teams ateam on e.awayteamid = ateam.id
where
	e.status like '%OPEN%'
order by
	e.starttime,
	hteam.description