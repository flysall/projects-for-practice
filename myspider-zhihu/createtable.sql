create table user(
	id int(11) NOT NULL,
	name varchar(100) NOT NULL,
	gender varchar(100) NOT NULL,
	usertype varchar(100) NOT NULL,
	follower_count int(11) NOT NULL,
	answer_count int(11) NOT NULL,
	article_count int(11) NOT NULL,
	is_followed varchar(100) NOT NULL,
	is_following varchar(100) NOT NULL,
	homepage varchar(100) NOT NULL
);