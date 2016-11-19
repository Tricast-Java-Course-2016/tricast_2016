select
	m.id,
	p.id,
	pt.description,
	p.eventid,
	m.markettypeid,
	mt.description		
from
	/*=SCHEMA*/periodtypes pt
	inner join /*=SCHEMA*/periods p on pt.id = p.periodtypeid
	inner join /*=SCHEMA*/markets m on m.periodid = p.id
	inner join /*=SCHEMA*/markettypes mt on mt.id = m.markettypeid
where
	m.eventid = :id