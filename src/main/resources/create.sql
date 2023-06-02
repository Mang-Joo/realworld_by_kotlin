create table article_favorited
(
    article_id bigint not null,
    favorited  varchar(255)
) engine = InnoDB;
create table article_table
(
    article_id    bigint not null auto_increment,
    author_id     bigint,
    slug          varchar(255) unique not null,
    title         varchar(255),
    description   varchar(255),
    body          varchar(255),
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (article_id)
) engine = InnoDB;
create table article_tag
(
    article_id bigint not null,
    tag        varchar(255)
) engine = InnoDB;
create table user_follow
(
    follow_id     bigint not null auto_increment,
    from_user     bigint not null,
    created_date  datetime(6),
    modified_date datetime(6),
    to_user       bigint not null,
    primary key (follow_id)
) engine = InnoDB;
create table user_table
(
    user_id       bigint not null auto_increment,
    username      varchar(255),
    email         varchar(255),
    password      varchar(255),
    bio           varchar(255),
    image         varchar(255),
    created_date  datetime(6),
    modified_date datetime(6),
    role          enum ('ROLE_ADMIN','ROLE_USER'),
    is_enabled    bit    not null,
    primary key (user_id)
) engine = InnoDB;
alter table user_table
    add constraint UK_eamk4l51hm6yqb8xw37i23kb5 unique (email);
alter table user_table
    add constraint UK_en3wad7p8qfu8pcmh62gvef6v unique (username);