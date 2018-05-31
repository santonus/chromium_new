use chromium;
select * from 
(select t.comments, t.NoOfCommenters, t.approval, r.issue, r.created, r.modified, 
datediff((cast(r.modified as date)),(cast(r.created as date))) no_of_days
 from review r left join (
	select issue, count(issue) comments, count(distinct sender) NoOfCommenters, 
	count(case approval when 'true' then 1 else NULL end) approval
	from comment group by issue) t on t.issue=r.issue order by r.issue asc) r1
natural join
(select t.owners, r.issue from review r left join (
	select owner, count(owner)owners from review group by owner)t  on t.owner=r.owner order by r.issue asc) r2;


