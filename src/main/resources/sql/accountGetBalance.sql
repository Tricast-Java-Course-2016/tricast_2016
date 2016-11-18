select
	SUM(t.amount)
from
	/*=SCHEMA*/transactions t
where
	t.accountid = :id